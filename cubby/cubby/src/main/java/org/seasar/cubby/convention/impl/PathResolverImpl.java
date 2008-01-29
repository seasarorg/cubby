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
package org.seasar.cubby.convention.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.convention.InternalForwardInfo;
import org.seasar.cubby.convention.PathResolver;
import org.seasar.cubby.exception.DuplicateRoutingRuntimeException;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.QueryStringBuilder;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;
import org.seasar.framework.util.StringUtil;

/**
 * クラスパスから {@link Action} を検索し、そのメソッドに指定された {@link org.seasar.cubby.action.Path}
 * の情報によって、リクエストされたパスをどのメソッドに振り分けるかを決定します。
 * 
 * @author baba
 */
public class PathResolverImpl implements PathResolver, Disposable {

	/** ロガー */
	private static final Logger logger = Logger
			.getLogger(PathResolverImpl.class);

	/** デフォルトの URI エンコーディング */
	private static final String DEFAULT_URI_ENCODING = "UTF-8";

	/** URI パラメータを抽出するための正規表現パターン */
	private static Pattern URI_PARAMETER_MATCHING_PATTERN = Pattern
			.compile("([{]([^}]+)[}])([^{]*)");

	/** デフォルトの URI パラメータ正規表現 */
	private static final String DEFAULT_URI_PARAMETER_REGEX = "[a-zA-Z0-9]+";

	/** 初期化フラグ */
	private boolean initialized;

	/** 命名規約 */
	private NamingConvention namingConvention;

	/** ルーティングのコンパレータ */
	private final Comparator<Routing> routingComparator = new RoutingComparator();

	/** 登録されたルーティングのマップ */
	private final Map<Routing, Routing> routings = new TreeMap<Routing, Routing>(
			routingComparator);

	/** URI のエンコーディング */
	private String uriEncoding = DEFAULT_URI_ENCODING;

	/**
	 * インスタンス化します。
	 */
	public PathResolverImpl() {
	}

	/**
	 * URI エンコーディングを設定します。
	 * 
	 * @param uriEncoding
	 *            URI エンコーディング
	 */
	public void setUriEncoding(final String uriEncoding) {
		this.uriEncoding = uriEncoding;
	}

	/**
	 * 初期化します。
	 */
	public void initialize() {
		if (!initialized) {
			final ClassCollector classCollector = new ActionClassCollector();
			classCollector.collect();

			DisposableUtil.add(this);
			initialized = true;
		}
	}

	/**
	 * 破棄します。
	 * 
	 * @see Disposable#dispose()
	 * @see DisposableUtil
	 */
	public void dispose() {
		final List<Routing> removes = new ArrayList<Routing>();
		for (final Routing routing : routings.keySet()) {
			if (routing.isAuto()) {
				removes.add(routing);
			}
		}
		for (final Routing routing : removes) {
			routings.remove(routing);
		}
		initialized = false;
	}

	/**
	 * ルーティング情報を登録します。
	 * <p>
	 * クラスパスを検索して自動登録されるルーティング情報以外にも、このメソッドによって手動でルーティング情報を登録できます。
	 * </p>
	 * 
	 * @param actionPath
	 *            アクションパス
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

		final Method method = ClassUtil.getMethod(actionClass, methodName,
				new Class<?>[0]);
		this.add(actionPath, actionClass, method, requestMethods, false);
	}

	/**
	 * ルーティング情報を登録します。
	 * 
	 * @param actionPath
	 *            アクションパス
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
			final RequestMethod[] requestMethods, final boolean auto) {

		String actionPathRegex = actionPath;
		final List<String> uriParameterNames = new ArrayList<String>();
		final Matcher matcher = URI_PARAMETER_MATCHING_PATTERN
				.matcher(actionPathRegex);
		while (matcher.find()) {
			final String holder = matcher.group(2);
			final String[] tokens = CubbyUtils.split2(holder, ',');
			uriParameterNames.add(tokens[0]);
			final String uriParameterRegex;
			if (tokens.length == 1) {
				uriParameterRegex = DEFAULT_URI_PARAMETER_REGEX;
			} else {
				uriParameterRegex = tokens[1];
			}
			actionPathRegex = StringUtil.replace(actionPathRegex, matcher
					.group(1), regexGroup(uriParameterRegex));
		}
		actionPathRegex = "^" + actionPathRegex + "$";
		final Pattern pattern = Pattern.compile(actionPathRegex);

		final Routing routing = new Routing(actionClass, method,
				uriParameterNames, pattern, requestMethods, auto);

		if (routings.containsKey(routing)) {
			throw new DuplicateRoutingRuntimeException("ECUB0001",
					new Object[] { routing, routings.get(routing) });
		}
		routings.put(routing, routing);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0007", new Object[] { routing });
		}
	}

	/**
	 * 指定されたパス、メソッドに対応する内部フォワード情報を取得します。
	 * 
	 * @param path
	 *            リクエストのパス
	 * @param requestMethod
	 *            リクエストのメソッド
	 * @return 内部フォワード情報、対応する内部フォワード情報が登録されていない場合は <code>null</code>
	 */
	public InternalForwardInfo getInternalForwardInfo(final String path,
			final String requestMethod) {
		initialize();

		final String decodedPath;
		try {
			decodedPath = URLDecoder.decode(path, uriEncoding);
		} catch (final IOException e) {
			throw new IORuntimeException(e);
		}

		final InternalForwardInfo internalForwardInfo = findInternalForwardInfo(
				decodedPath, requestMethod);
		return internalForwardInfo;
	}

	/**
	 * 指定されたパス、メソッドに対応する内部フォワード情報を検索します。
	 * 
	 * @param path
	 *            リクエストのパス
	 * @param requestMethod
	 *            リクエストのメソッド
	 * @return 内部フォワード情報、対応する内部フォワード情報が登録されていない場合は <code>null</code>
	 */
	private InternalForwardInfo findInternalForwardInfo(final String path,
			final String requestMethod) {
		final Map<String, String> uriParameters = new HashMap<String, String>();
		for (final Routing routing : routings.values()) {
			final Matcher matcher = routing.getPattern().matcher(path);
			if (matcher.find()) {
				if (routing.isAcceptable(requestMethod)) {
					for (int i = 0; i < matcher.groupCount(); i++) {
						final String name = routing.getUriParameterNames().get(
								i);
						final String value = matcher.group(i + 1);
						uriParameters.put(name, value);
					}
					final String inernalFowardPath = buildInternalForwardPathWithQueryString(
							routing, uriParameters);
					final InternalForwardInfoImpl internalForwardInfo = new InternalForwardInfoImpl(
							inernalFowardPath, routing, uriParameters);
					return internalForwardInfo;
				}
			}
		}
		return null;
	}

	/**
	 * 内部フォワードパスを構築します。
	 * 
	 * @param routing
	 *            ルーティング
	 * @param uriParameters
	 *            URI パラメータ
	 * @return 内部フォワードパス
	 */
	private String buildInternalForwardPathWithQueryString(
			final Routing routing, final Map<String, String> uriParameters) {
		final StringBuilder builder = new StringBuilder(100);
		builder.append(fromActionClassToInnerForwardPath(routing
				.getActionClass(), routing.getMethod()));
		if (!uriParameters.isEmpty()) {
			builder.append("?");
			final QueryStringBuilder query = new QueryStringBuilder();
			final String encoding = PathResolverImpl.this.uriEncoding;
			if (!StringUtil.isEmpty(encoding)) {
				query.setEncode(encoding);
			}
			for (final Entry<String, String> entry : uriParameters.entrySet()) {
				query.addParam(entry.getKey(), entry.getValue());
			}
			builder.append(query.toString());
		}
		return builder.toString();
	}

	/**
	 * アクションクラスとメソッドから内部フォワードのパスへ変換します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param method
	 *            アクションメソッド
	 * @return 内部フォワードパス
	 */
	private String fromActionClassToInnerForwardPath(
			final Class<? extends Action> actionClass, final Method method) {
		final String componentName = namingConvention
				.fromClassNameToComponentName(actionClass.getCanonicalName());
		final StringBuilder builder = new StringBuilder(200);
		builder.append('/');
		builder.append(componentName.substring(
				0,
				componentName.length()
						- namingConvention.getActionSuffix().length())
				.replaceAll("_", "/"));
		builder.append('/');
		builder.append(method.getName());
		return builder.toString();
	}

	/**
	 * 命名規約を設定します。
	 * 
	 * @param namingConvention
	 *            命名規約
	 */
	public void setNamingConvention(final NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
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
		 * <li>URI 埋め込みパラメータが少ない順</li>
		 * <li>正規表現の順(@link {@link String#compareTo(String)})</li>
		 * </ul>
		 * </p>
		 */
		public int compare(final Routing routing1, final Routing routing2) {
			int compare = routing1.getUriParameterNames().size()
					- routing2.getUriParameterNames().size();
			if (compare != 0) {
				return compare;
			}
			compare = routing1.getPattern().pattern().compareTo(
					routing2.getPattern().pattern());
			if (compare != 0) {
				return compare;
			}
			final RequestMethod[] requestMethods1 = routing1
					.getRequestMethods();
			final RequestMethod[] requestMethods2 = routing2
					.getRequestMethods();
			for (final RequestMethod requestMethod : requestMethods1) {
				if (ArrayUtil.contains(requestMethods2, requestMethod)) {
					return 0;
				}
			}
			return 1;
		}
	}

	/**
	 * ルーティング。
	 * 
	 * @author baba
	 */
	static class Routing {

		/** アクションクラス。 */
		private final Class<? extends Action> actionClass;

		/** アクソンメソッド。 */
		private final Method method;

		/** URI パラメータ名。 */
		private final List<String> uriParameterNames;

		/** 正規表現パターン。 */
		private final Pattern pattern;

		/** リクエストメソッド。 */
		private final RequestMethod[] requestMethods;

		/** 自動登録されたかどうか */
		private final boolean auto;

		/**
		 * インスタンス化します。
		 * 
		 * @param actionClass
		 *            アクションクラス
		 * @param method
		 *            アクションメソッド
		 * @param uriParameterNames
		 *            URI パラメータ名
		 * @param pattern
		 *            正規表現パターン
		 * @param requestMethods
		 *            リクエストメソッド
		 * @param auto
		 *            自動登録されたかどうか
		 */
		public Routing(final Class<? extends Action> actionClass,
				final Method method, final List<String> uriParameterNames,
				final Pattern pattern, final RequestMethod[] requestMethods,
				final boolean auto) {
			this.actionClass = actionClass;
			this.method = method;
			this.uriParameterNames = uriParameterNames;
			this.pattern = pattern;
			this.requestMethods = requestMethods;
			this.auto = auto;
		}

		/**
		 * アクションクラスを取得します。
		 * 
		 * @return アクションクラス
		 */
		public Class<? extends Action> getActionClass() {
			return actionClass;
		}

		/**
		 * メソッドを取得します。
		 * 
		 * @return メソッド
		 */
		public Method getMethod() {
			return method;
		}

		/**
		 * URI パラメータ名を取得します。
		 * 
		 * @return URI パラメータ名
		 */
		public List<String> getUriParameterNames() {
			return uriParameterNames;
		}

		/**
		 * 正規表現パターンを取得します。
		 * 
		 * @return 正規表現パターン
		 */
		public Pattern getPattern() {
			return pattern;
		}

		/**
		 * リクエストメソッドを取得します。
		 * 
		 * @return リクエストメソッド
		 */
		public RequestMethod[] getRequestMethods() {
			return requestMethods;
		}

		/**
		 * 自動登録されたルーティングかを示します。
		 * 
		 * @return 自動登録されたルーティングの場合は <code>true</code>、そうでない場合は
		 *         <code>false</code>
		 */
		public boolean isAuto() {
			return auto;
		}

		/**
		 * 指定されたリクエストメソッドがこのルーティングの対象かどうかを示します。
		 * 
		 * @param requestMethod
		 *            リクエストメソッド
		 * @return 対象の場合は <code>true</code>、そうでない場合は <code>false</code>
		 */
		public boolean isAcceptable(final String requestMethod) {
			for (final RequestMethod acceptableRequestMethod : requestMethods) {
				if (StringUtil.equalsIgnoreCase(acceptableRequestMethod.name(),
						requestMethod)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * このオブジェクトの文字列表現を返します。
		 * 
		 * @return このオブジェクトの正規表現
		 */
		@Override
		public String toString() {
			return new StringBuilder().append("[regex=").append(this.pattern)
					.append(",method=").append(this.method).append(
							",uriParameterNames=").append(uriParameterNames)
					.append(",requestMethods=").append(
							Arrays.deepToString(requestMethods)).append("]")
					.toString();
		}
	}

	/**
	 * クラスを収集します。
	 * 
	 * @author baba
	 */
	class ActionClassCollector extends ClassCollector {

		/**
		 * デフォルトコンストラクタ。
		 */
		public ActionClassCollector() {
			super(namingConvention);
		}

		/**
		 * 指定されたパッケージとクラス名からクラスを検索します。
		 * 
		 * @param packageName
		 *            パッケージ名
		 * @param shortClassName
		 *            クラス名
		 */
		public void processClass(final String packageName,
				final String shortClassName) {
			if (shortClassName.indexOf('$') != -1) {
				return;
			}
			final String className = ClassUtil.concatName(packageName,
					shortClassName);
			if (!namingConvention.isTargetClassName(className)) {
				return;
			}
			if (!className.endsWith(namingConvention.getActionSuffix())) {
				return;
			}
			final Class<? extends Action> clazz = classForName(className);
			if (namingConvention.isSkipClass(clazz)) {
				return;
			}

			for (final Method method : clazz.getMethods()) {
				if (CubbyUtils.isActionMethod(method)) {
					final String actionPath = CubbyUtils.getActionPath(clazz,
							method);
					final RequestMethod[] acceptableRequestMethods = CubbyUtils
							.getAcceptableRequestMethods(clazz, method);
					add(actionPath, clazz, method, acceptableRequestMethods,
							true);
				}
			}
		}

	}

	/**
	 * クラスを取得します。
	 * 
	 * @param <T>
	 *            型
	 * @param className
	 *            クラス名
	 * @return クラス
	 */
	@SuppressWarnings("unchecked")
	private static <T> Class<T> classForName(final String className) {
		return ClassUtil.forName(className);
	}

}
