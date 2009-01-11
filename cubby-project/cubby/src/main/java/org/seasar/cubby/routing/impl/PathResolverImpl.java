/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.routing.impl;

import static org.seasar.cubby.internal.util.LogMessages.format;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.internal.routing.PathTemplateParser;
import org.seasar.cubby.internal.routing.impl.PathTemplateParserImpl;
import org.seasar.cubby.internal.util.MetaUtils;
import org.seasar.cubby.internal.util.QueryStringBuilder;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.routing.RoutingException;
import org.seasar.cubby.util.ActionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * クラスパスから {@link Action} を検索し、クラス名やメソッド名、そのクラスやメソッドに指定された
 * {@link org.seasar.cubby.action.Path}
 * の情報からアクションのパスを抽出し、リクエストされたパスをどのメソッドに振り分けるかを決定します。
 * 
 * @author baba
 * @since 1.0.0
 */
public class PathResolverImpl implements PathResolver {

	/** ロガー */
	private static final Logger logger = LoggerFactory
			.getLogger(PathResolverImpl.class);

	/** 登録されたルーティングのマップ。 */
	private final Map<RoutingKey, Routing> routings = new TreeMap<RoutingKey, Routing>();

	/** パステンプレートのパーサー。 */
	private final PathTemplateParser pathTemplateParser = new PathTemplateParserImpl();

	/**
	 * インスタンス化します。
	 */
	public PathResolverImpl() {
	}

	/**
	 * ルーティング情報を取得します。
	 * 
	 * @return ルーティング情報
	 */
	public Collection<Routing> getRoutings() {
		return routings.values();
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(final Class<?> actionClass) {
		for (final Method method : actionClass.getMethods()) {
			if (ActionUtils.isActionMethod(method)) {
				final String actionPath = MetaUtils.getActionPath(actionClass,
						method);
				final RequestMethod[] acceptableRequestMethods = MetaUtils
						.getAcceptableRequestMethods(actionClass, method);
				for (final RequestMethod requestMethod : acceptableRequestMethods) {
					final String onSubmit = MetaUtils.getOnSubmit(method);
					final int priority = MetaUtils.getPriority(method);
					this.add(actionPath, actionClass, method, requestMethod,
							onSubmit, priority);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void addAll(final Collection<Class<?>> actionClasses) {
		for (final Class<?> actionClass : actionClasses) {
			add(actionClass);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		routings.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(final String actionPath, final Class<?> actionClass,
			final String methodName, final RequestMethod requestMethod,
			final String onSubmit, final int priority) {
		try {
			final Method method = actionClass.getMethod(methodName);
			this.add(actionPath, actionClass, method, requestMethod, onSubmit,
					priority);
		} catch (final NoSuchMethodException e) {
			throw new RoutingException(e);
		}
	}

	/**
	 * ルーティング情報を登録します。
	 * 
	 * @param actionPath
	 *            アクションのパス
	 * @param actionClass
	 *            アクションクラス
	 * @param method
	 *            アクションメソッド
	 * @param requestMethods
	 *            リクエストメソッド
	 * @param onSubmit
	 *            アクションメソッドへ振り分けるための要求パラメータ名
	 * @param priority
	 *            プライオリティ
	 */
	private void add(final String actionPath, final Class<?> actionClass,
			final Method method, final RequestMethod requestMethod,
			final String onSubmit, final int priority) {
		if (!ActionUtils.isActionMethod(method)) {
			throw new RoutingException(format("ECUB0003", method));
		}

		final List<String> uriParameterNames = new ArrayList<String>();
		final String uriRegex = pathTemplateParser.parse(actionPath,
				new PathTemplateParser.Handler() {

					public String handle(final String name, final String regex) {
						uriParameterNames.add(name);
						return regexGroup(regex);
					}

				});
		final Pattern pattern = Pattern.compile("^" + uriRegex + "$");

		final Routing routing = new RoutingImpl(actionClass, method,
				actionPath, uriParameterNames, pattern, requestMethod,
				onSubmit, priority);
		final RoutingKey key = new RoutingKey(routing);

		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0007", routing));
		}
		if (routings.containsKey(key)) {
			final Routing duplication = routings.get(key);
			throw new RoutingException(format("ECUB0001", routing, duplication));
		}
		routings.put(key, routing);
	}

	/**
	 * {@inheritDoc}
	 */
	public PathInfo getPathInfo(final String path, final String requestMethod,
			final String characterEncoding) {
		final String decodedPath = decode(path, characterEncoding);
		final Iterator<Routing> iterator = getRoutings().iterator();
		while (iterator.hasNext()) {
			final Routing routing = iterator.next();
			final Matcher matcher = routing.getPattern().matcher(decodedPath);
			if (matcher.find()) {
				if (routing.isAcceptable(requestMethod)) {
					final Map<String, Routing> onSubmitRoutings = new HashMap<String, Routing>();
					onSubmitRoutings.put(routing.getOnSubmit(), routing);
					while (iterator.hasNext()) {
						final Routing anotherRouting = iterator.next();
						if (routing.getPattern().pattern().equals(
								anotherRouting.getPattern().pattern())
								&& routing.getRequestMethod().equals(
										anotherRouting.getRequestMethod())) {
							onSubmitRoutings.put(anotherRouting.getOnSubmit(),
									anotherRouting);
						}
					}

					final Map<String, String[]> uriParameters = new HashMap<String, String[]>();
					for (int i = 0; i < matcher.groupCount(); i++) {
						final String name = routing.getUriParameterNames().get(
								i);
						final String value = matcher.group(i + 1);
						uriParameters.put(name, new String[] { value });
					}

					final PathInfo pathInfo = new PathInfoImpl(uriParameters,
							onSubmitRoutings);

					return pathInfo;
				}
			}
		}

		return null;
	}

	/**
	 * 指定された正規表現を括弧「()」で囲んで正規表現のグループにします。
	 * 
	 * @param regex
	 *            正規表現
	 * @return 正規表現のグループ
	 */
	private static String regexGroup(final String regex) {
		return "(" + regex + ")";
	}

	/**
	 * {@inheritDoc}
	 */
	public String reverseLookup(final Class<?> actionClass,
			final String methodName, final Map<String, String[]> parameters,
			final String characterEncoding) {
		final Collection<Routing> routings = getRoutings();
		final Routing routing = findRouting(routings, actionClass, methodName);
		final String actionPath = routing.getActionPath();
		final Map<String, String[]> copyOfParameters = new HashMap<String, String[]>(
				parameters);
		final StringBuilder path = new StringBuilder(100);
		path.append(pathTemplateParser.parse(actionPath,
				new PathTemplateParser.Handler() {

					public String handle(final String name, final String regex) {
						if (!copyOfParameters.containsKey(name)) {
							throw new RoutingException(format("ECUB0104",
									actionPath, name));
						}
						final String value = copyOfParameters.remove(name)[0];
						if (!value.matches(regex)) {
							throw new RoutingException(format("ECUB0105",
									actionPath, name, value, regex));
						}
						return encode(value, characterEncoding);
					}

				}));

		if (!copyOfParameters.isEmpty()) {
			final QueryStringBuilder builder = new QueryStringBuilder();
			if (characterEncoding != null) {
				builder.setEncode(characterEncoding);
			}
			for (final Entry<String, String[]> entry : copyOfParameters
					.entrySet()) {
				for (final String value : entry.getValue()) {
					builder.addParam(entry.getKey(), value);
				}
			}
			path.append('?');
			path.append(builder.toString());
		}

		return path.toString();
	}

	/**
	 * 指定されたクラス、メソッドに対応するルーティング情報を検索します。
	 * 
	 * @param routings
	 *            ルーティング情報
	 * @param actionClass
	 *            クラス
	 * @param methodName
	 *            メソッド
	 * @return ルーティング情報
	 * @throws RoutingException
	 *             ルーティング情報が見つからなかった場合
	 */
	private static Routing findRouting(final Collection<Routing> routings,
			final Class<?> actionClass, final String methodName) {
		for (final Routing routing : routings) {
			if (actionClass.getCanonicalName().equals(
					routing.getActionClass().getCanonicalName())) {
				if (methodName.equals(routing.getActionMethod().getName())) {
					return routing;
				}
			}
		}
		throw new RoutingException(format("ECUB0103", actionClass, methodName));
	}

	/**
	 * 指定された文字列を URL エンコードします。
	 * 
	 * @param str
	 *            文字列
	 * @param characterEncoding
	 *            エンコーディング
	 * @return エンコードされた文字列
	 */
	private static String encode(final String str,
			final String characterEncoding) {
		if (characterEncoding == null) {
			return str;
		}
		try {
			return URLEncoder.encode(str, characterEncoding);
		} catch (final UnsupportedEncodingException e) {
			throw new RoutingException(e);
		}
	}

	/**
	 * 指定された URL 文字列をデコードします。
	 * 
	 * @param str
	 *            文字列
	 * @param characterEncoding
	 *            エンコーディング
	 * @return デコードされた文字列
	 */
	private static String decode(final String str,
			final String characterEncoding) {
		if (characterEncoding == null) {
			return str;
		}
		try {
			return URLDecoder.decode(str, characterEncoding);
		} catch (final IOException e) {
			throw new RoutingException(e);
		}
	}

	/**
	 * ルーティングのキーです。
	 * 
	 * @author baba
	 * @since 2.0.0
	 */
	static class RoutingKey implements Comparable<RoutingKey> {

		private final int priority;

		private final List<String> uriParameterNames;

		private final Pattern pattern;

		private final RequestMethod requestMethod;

		private final String onSubmit;

		public RoutingKey(final Routing routing) {
			this.priority = routing.getPriority();
			this.uriParameterNames = routing.getUriParameterNames();
			this.pattern = routing.getPattern();
			this.requestMethod = routing.getRequestMethod();
			this.onSubmit = routing.getOnSubmit();
		}

		/**
		 * このキーと指定されたキーを比較します。
		 * <p>
		 * 正規表現パターンと HTTP メソッドが同じ場合は同値とみなします。
		 * </p>
		 * <p>
		 * また、大小関係は以下のようになります。
		 * <ul>
		 * <li>優先度(@link {@link Path#priority()})が小さい順</li>
		 * <li>URI 埋め込みパラメータが少ない順</li>
		 * <li>正規表現の順(@link {@link String#compareTo(String)})</li>
		 * </ul>
		 * </p>
		 * 
		 * @param another
		 *            比較対象のキー
		 * @return 比較結果
		 */
		public int compareTo(final RoutingKey another) {
			int compare = this.priority - another.priority;
			if (compare != 0) {
				return compare;
			}
			compare = this.uriParameterNames.size()
					- another.uriParameterNames.size();
			if (compare != 0) {
				return compare;
			}
			compare = this.pattern.pattern().compareTo(
					another.pattern.pattern());
			if (compare != 0) {
				return compare;
			}
			compare = this.requestMethod.compareTo(another.requestMethod);
			if (compare != 0) {
				return compare;
			}
			if (this.onSubmit == another.onSubmit) {
				compare = 0;
			} else if (this.onSubmit == null) {
				compare = -1;
			} else if (another.onSubmit == null) {
				compare = 1;
			} else {
				compare = this.onSubmit.compareTo(another.onSubmit);
			}
			return compare;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((onSubmit == null) ? 0 : onSubmit.hashCode());
			result = prime
					* result
					+ ((pattern.pattern() == null) ? 0 : pattern.pattern()
							.hashCode());
			result = prime * result + priority;
			result = prime * result
					+ ((requestMethod == null) ? 0 : requestMethod.hashCode());
			result = prime
					* result
					+ ((uriParameterNames == null) ? 0 : uriParameterNames
							.hashCode());
			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final RoutingKey other = (RoutingKey) obj;
			if (onSubmit == null) {
				if (other.onSubmit != null) {
					return false;
				}
			} else if (!onSubmit.equals(other.onSubmit)) {
				return false;
			}
			if (pattern == null) {
				if (other.pattern != null) {
					return false;
				}
			} else if (!pattern.pattern().equals(other.pattern.pattern())) {
				return false;
			}
			if (priority != other.priority) {
				return false;
			}
			if (requestMethod == null) {
				if (other.requestMethod != null) {
					return false;
				}
			} else if (!requestMethod.equals(other.requestMethod)) {
				return false;
			}
			if (uriParameterNames == null) {
				if (other.uriParameterNames != null) {
					return false;
				}
			} else if (!uriParameterNames.equals(other.uriParameterNames)) {
				return false;
			}
			return true;
		}

	}

	/**
	 * パスから取得した情報の実装です。
	 * 
	 * @author baba
	 * @since 2.0.0
	 */
	static class PathInfoImpl implements PathInfo {

		/** URI から抽出したパラメータ。 */
		private final Map<String, String[]> uriParameters;

		/** リクエストパラメータ名と対応するルーティングのマッピング。 */
		private final Map<String, Routing> routings;

		/**
		 * インスタンス化します。
		 * 
		 * @param uriParameters
		 *            URI から抽出したパラメータ
		 * @param routings
		 *            リクエストパラメータ名とルーティングのマッピング
		 */
		public PathInfoImpl(final Map<String, String[]> uriParameters,
				final Map<String, Routing> routings) {
			this.uriParameters = uriParameters;
			this.routings = routings;
		}

		/**
		 * {@inheritDoc}
		 */
		public Map<String, String[]> getURIParameters() {
			return uriParameters;
		}

		/**
		 * {@inheritDoc}
		 */
		public Routing dispatch(final Map<String, Object[]> parameterMap) {
			for (final Entry<String, Routing> entry : routings.entrySet()) {
				if (parameterMap.containsKey(entry.getKey())) {
					return entry.getValue();
				}
			}
			return routings.get(null);
		}

	}

}
