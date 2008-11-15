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
package org.seasar.cubby.internal.routing.impl;

import static org.seasar.cubby.CubbyConstants.INTERNAL_FORWARD_DIRECTORY;
import static org.seasar.cubby.internal.util.LogMessages.format;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
import org.seasar.cubby.internal.routing.InternalForwardInfo;
import org.seasar.cubby.internal.routing.PathResolver;
import org.seasar.cubby.internal.routing.PathTemplateParser;
import org.seasar.cubby.internal.routing.Routing;
import org.seasar.cubby.internal.routing.RoutingException;
import org.seasar.cubby.internal.util.CubbyUtils;
import org.seasar.cubby.internal.util.QueryStringBuilder;
import org.seasar.cubby.internal.util.StringUtils;
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
public class PathResolverImpl implements PathResolver {// ,
	// DetectClassProcessor,
	// Disposable {

	/** ロガー */
	private static final Logger logger = LoggerFactory
			.getLogger(PathResolverImpl.class);

	// /** インスタンスが初期化済みであることを示します。 */
	// private boolean initialized;
	//
	// /** 命名規約。 */
	// private NamingConvention namingConvention;

	/** ルーティングのコンパレータ。 */
	private final Comparator<Routing> routingComparator = new RoutingComparator();

	/** 登録されたルーティングのマップ。 */
	private final Map<Routing, Routing> routings = new TreeMap<Routing, Routing>(
			routingComparator);

	// /** クラスパスを走査してクラスを検出するクラス。 */
	// private ClassDetector classDetector;

	/** パステンプレートのパーサー。 */
	private PathTemplateParser pathTemplateParser = new PathTemplateParserImpl();

	/** 手動登録用のプライオリティカウンタ。 */
	private int priorityCounter = 0;

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
	public Map<Routing, Routing> getRoutings() {
		// TODO
		// initialize();
		// return Collections.unmodifiableList(new ArrayList<Routing>(routings
		// .values()));
		return routings;
	}

	// /**
	// * クラスパスを走査してクラスを検出するクラスを設定します。
	// *
	// * @param classDetector
	// * クラスパスを走査してクラスを検出するクラス
	// */
	// public void setClassDetector(final ClassDetector classDetector) {
	// this.classDetector = classDetector;
	// }
	//
	// /**
	// * 初期化します。
	// */
	// public void initialize() {
	// if (initialized) {
	// return;
	// }
	// classDetector.detect();
	// DisposableUtil.add(this);
	// initialized = true;
	// }
	//
	// /**
	// * {@inheritDoc}
	// */
	// public void dispose() {
	// final List<Routing> removes = new ArrayList<Routing>();
	// for (final Routing routing : routings.keySet()) {
	// if (routing.isAuto()) {
	// removes.add(routing);
	// }
	// }
	// for (final Routing routing : removes) {
	// routings.remove(routing);
	// }
	// initialized = false;
	// }

	public void addAllActionClasses(
			Collection<Class<? extends Action>> actionClasses) {
		for (final Class<? extends Action> actionClass : actionClasses) {
			for (final Method method : actionClass.getMethods()) {
				if (CubbyUtils.isActionMethod(method)) {
					final String actionPath = CubbyUtils.getActionPath(
							actionClass, method);
					final RequestMethod[] acceptableRequestMethods = CubbyUtils
							.getAcceptableRequestMethods(actionClass, method);
					for (final RequestMethod requestMethod : acceptableRequestMethods) {
						this.add(actionPath, actionClass, method,
								requestMethod, true);
					}
				}
			}
		}
	}

	public void clearAllActionClasses() {
		routings.clear();
	}

	// // TODO
	// public void clearAutoRegisteredRouting() {
	// final List<Routing> removes = new ArrayList<Routing>();
	// for (final Routing routing : routings.keySet()) {
	// if (routing.isAuto()) {
	// removes.add(routing);
	// }
	// }
	// for (final Routing routing : removes) {
	// routings.remove(routing);
	// }
	// }

	// /**
	// * ルーティング情報を登録します。
	// * <p>
	// * クラスパスを検索して自動登録されるルーティング情報以外にも、このメソッドによって手動でルーティング情報を登録できます。
	// * </p>
	// *
	// * @param actionPath
	// * アクションのパス
	// * @param actionClass
	// * アクションクラス
	// * @param methodName
	// * アクションメソッド名
	// */
	// public void add(final String actionPath,
	// final Class<? extends Action> actionClass, final String methodName) {
	// this.add(actionPath, actionClass, methodName, new RequestMethod[0]);
	// }

	/**
	 * ルーティング情報を登録します。
	 * <p>
	 * クラスパスを検索して自動登録されるルーティング情報以外にも、このメソッドによって手動でルーティング情報を登録できます。
	 * </p>
	 * 
	 * @param actionPath
	 *            アクションのパス
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            アクションメソッド名
	 * @param requestMethods
	 *            リクエストメソッド
	 */
	public void add(final String actionPath,
			final Class<? extends Action> actionClass, final String methodName,
			final RequestMethod... requestMethods) {
		try {
			final Method method = actionClass.getMethod(methodName);
			if (requestMethods == null || requestMethods.length == 0) {
				for (final RequestMethod requestMethod : CubbyUtils.DEFAULT_ACCEPT_ANNOTATION
						.value()) {
					this.add(actionPath, actionClass, method, requestMethod,
							false);
				}
			} else {
				for (final RequestMethod requestMethod : requestMethods) {
					this.add(actionPath, actionClass, method, requestMethod,
							false);
				}
			}
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
	 * @param auto
	 *            自動登録かどうか
	 */
	private void add(final String actionPath,
			final Class<? extends Action> actionClass, final Method method,
			final RequestMethod requestMethod, final boolean auto) {

		if (!CubbyUtils.isActionClass(actionClass)) {
			throw new RoutingException(format("ECUB0002", actionClass));
		} else if (!CubbyUtils.isActionMethod(method)) {
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

		final String onSubmit = CubbyUtils.getOnSubmit(method);

		final int priority = auto ? CubbyUtils.getPriority(method)
				: priorityCounter++;

		final Routing routing = new RoutingImpl(actionClass, method,
				actionPath, uriParameterNames, pattern, requestMethod,
				onSubmit, priority, auto);

		if (routings.containsKey(routing)) {
			final Routing duplication = routings.get(routing);
			if (!routing.getActionClass().equals(duplication.getActionClass())
					|| !routing.getMethod().equals(duplication.getMethod())) {
				throw new RoutingException(format("ECUB0001", routing,
						duplication));
			}
		} else {
			routings.put(routing, routing);
			if (logger.isDebugEnabled()) {
				logger.debug(format("DCUB0007", routing));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public InternalForwardInfo getInternalForwardInfo(final String path,
			final String requestMethod, final String characterEncoding) {
		// TODO
		// initialize();
		//
		// final InternalForwardInfo internalForwardInfo =
		// findInternalForwardInfo(
		// decode(path, characterEncoding), requestMethod,
		// characterEncoding);
		// return internalForwardInfo;
		// }
		//
		// /**
		// * 指定されたパス、メソッドに対応する内部フォワード情報を検索します。
		// *
		// * @param path
		// * リクエストのパス
		// * @param requestMethod
		// * リクエストのメソッド
		// * @return 内部フォワード情報、対応する内部フォワード情報が登録されていない場合は <code>null</code>
		// */
		// public InternalForwardInfo findInternalForwardInfo(final String path,
		// final String requestMethod, final String characterEncoding) {
		final String decodedPath = decode(path, characterEncoding);
		final Iterator<Routing> iterator = getRoutings().values().iterator();
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
					final String inernalFowardPath = buildInternalForwardPath(
							uriParameters, characterEncoding);

					final InternalForwardInfo internalForwardInfo = new InternalForwardInfoImpl(
							inernalFowardPath, onSubmitRoutings);

					return internalForwardInfo;
				}
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String buildInternalForwardPath(
			final Map<String, String[]> parameters,
			final String characterEncoding) {
		final StringBuilder path = new StringBuilder(100);
		path.append(INTERNAL_FORWARD_DIRECTORY);
		if (parameters != null && !parameters.isEmpty()) {
			path.append("?");
			final QueryStringBuilder query = new QueryStringBuilder();
			if (!StringUtils.isEmpty(characterEncoding)) {
				query.setEncode(characterEncoding);
			}
			for (final Entry<String, String[]> entry : parameters.entrySet()) {
				for (final String parameter : entry.getValue()) {
					query.addParam(entry.getKey(), parameter);
				}
			}
			path.append(query.toString());
		}
		return path.toString();
	}

	// /**
	// * 命名規約を設定します。
	// *
	// * @param namingConvention
	// * 命名規約
	// */
	// public void setNamingConvention(final NamingConvention namingConvention)
	// {
	// this.namingConvention = namingConvention;
	// }

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
	 * ルーティングのコンパレータ。
	 * 
	 * @author baba
	 */
	static class RoutingComparator implements Comparator<Routing> {

		/**
		 * routing1 と routing2 を比較します。
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
		 * @param routing1
		 *            比較対象1
		 * @param routing2
		 *            比較対象2
		 * @return 比較結果
		 */
		public int compare(final Routing routing1, final Routing routing2) {
			int compare = routing1.getPriority() - routing2.getPriority();
			if (compare != 0) {
				return compare;
			}
			compare = routing1.getUriParameterNames().size()
					- routing2.getUriParameterNames().size();
			if (compare != 0) {
				return compare;
			}
			compare = routing1.getPattern().pattern().compareTo(
					routing2.getPattern().pattern());
			if (compare != 0) {
				return compare;
			}
			compare = routing1.getRequestMethod().compareTo(
					routing2.getRequestMethod());
			if (compare != 0) {
				return compare;
			}
			if (routing1.getOnSubmit() == routing2.getOnSubmit()) {
				compare = 0;
			} else if (routing1.getOnSubmit() == null) {
				compare = -1;
			} else if (routing2.getOnSubmit() == null) {
				compare = 1;
			} else {
				compare = routing1.getOnSubmit().compareTo(
						routing2.getOnSubmit());
			}
			return compare;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String reverseLookup(final Class<? extends Action> actionClass,
			final String methodName, final Map<String, String[]> parameters,
			final String characterEncoding) {
		final Collection<Routing> routings = getRoutings().values();
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
	private static Routing findRouting(Collection<Routing> routings,
			final Class<? extends Action> actionClass, final String methodName) {
		for (final Routing routing : routings) {
			if (actionClass.getCanonicalName().equals(
					routing.getActionClass().getCanonicalName())) {
				if (methodName.equals(routing.getMethod().getName())) {
					return routing;
				}
			}
		}
		throw new RoutingException(format("ECUB0103", actionClass, methodName));
	}

	// /**
	// * {@inheritDoc}
	// * <p>
	// * 指定されたパッケージ名、クラス名から導出されるクラスがアクションクラスだった場合はルーティングを登録します。
	// * </p>
	// */
	// public void processClass(final String packageName,
	// final String shortClassName) {
	// if (shortClassName.indexOf('$') != -1) {
	// return;
	// }
	// final String className = ClassUtils.concatName(packageName,
	// shortClassName);
	// if (!namingConvention.isTargetClassName(className)) {
	// return;
	// }
	// if (!className.endsWith(namingConvention.getActionSuffix())) {
	// return;
	// }
	// final Class<?> clazz = ClassUtils.forName(className);
	// if (namingConvention.isSkipClass(clazz)) {
	// return;
	// }
	// if (!CubbyUtils.isActionClass(clazz)) {
	// return;
	// }
	// final Class<? extends Action> actionClass = cast(clazz);
	//
	// for (final Method method : clazz.getMethods()) {
	// if (CubbyUtils.isActionMethod(method)) {
	// final String actionPath = CubbyUtils.getActionPath(actionClass,
	// method);
	// final RequestMethod[] acceptableRequestMethods = CubbyUtils
	// .getAcceptableRequestMethods(clazz, method);
	// for (final RequestMethod requestMethod : acceptableRequestMethods) {
	// add(actionPath, actionClass, method, requestMethod, true);
	// }
	// }
	// }
	// }
	//
	// /**
	// * 指定されたクラスを <code>Class&lt;? extends Action&gt;</code> にキャストします。
	// *
	// * @param clazz
	// * クラス
	// * @return キャストされたクラス
	// */
	// @SuppressWarnings("unchecked")
	// private static Class<? extends Action> cast(final Class<?> clazz) {
	// return Class.class.cast(clazz);
	// }

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

}