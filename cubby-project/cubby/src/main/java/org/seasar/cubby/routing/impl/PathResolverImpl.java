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

import static org.seasar.cubby.CubbyConstants.INTERNAL_FORWARD_DIRECTORY;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
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
import org.seasar.cubby.controller.ClassDetector;
import org.seasar.cubby.controller.DetectClassProcessor;
import org.seasar.cubby.exception.ActionRuntimeException;
import org.seasar.cubby.exception.DuplicateRoutingRuntimeException;
import org.seasar.cubby.routing.InternalForwardInfo;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.QueryStringBuilder;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;
import org.seasar.framework.util.StringUtil;

/**
 * クラスパスから {@link Action} を検索し、クラス名やメソッド名、そのクラスやメソッドに指定された
 * {@link org.seasar.cubby.action.Path}
 * の情報からアクションのパスを抽出し、リクエストされたパスをどのメソッドに振り分けるかを決定します。
 * 
 * @author baba
 * @since 1.0.0
 */
public class PathResolverImpl implements PathResolver, DetectClassProcessor,
		Disposable {

	/** ロガー */
	private static final Logger logger = Logger
			.getLogger(PathResolverImpl.class);

	/** デフォルトの URI エンコーディング */
	private static final String DEFAULT_URI_ENCODING = "UTF-8";

	/** アクションのパスからパラメータを抽出するための正規表現パターン */
	private static Pattern URI_PARAMETER_MATCHING_PATTERN = Pattern
			.compile("([{]([^}]+)[}])([^{]*)");

	/** デフォルトの URI パラメータ正規表現 */
	private static final String DEFAULT_URI_PARAMETER_REGEX = "[a-zA-Z0-9]+";

	/** インスタンスが初期化済みであることを示します。 */
	private boolean initialized;

	/** 命名規約。 */
	private NamingConvention namingConvention;

	/** ルーティングのコンパレータ。 */
	private final Comparator<Routing> routingComparator = new RoutingComparator();

	/** 登録されたルーティングのマップ。 */
	private final Map<Routing, Routing> routings = new TreeMap<Routing, Routing>(
			routingComparator);

	/** クラスパスを走査してクラスを検出するクラス。 */
	private ClassDetector classDetector;

	/** URI のエンコーディング。 */
	private String uriEncoding = DEFAULT_URI_ENCODING;

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
	public List<Routing> getRoutings() {
		initialize();
		return Collections.unmodifiableList(new ArrayList<Routing>(routings
				.values()));
	}

	/**
	 * クラスパスを走査してクラスを検出するクラスを設定します。
	 * 
	 * @param classDetector
	 *            クラスパスを走査してクラスを設定します。
	 */
	public void setClassDetector(final ClassDetector classDetector) {
		this.classDetector = classDetector;
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
		if (initialized) {
			return;
		}
		classDetector.detect();
		DisposableUtil.add(this);
		initialized = true;
	}

	/**
	 * {@inheritDoc}
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
	 *            アクションのパス
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            アクションメソッド名
	 */
	public void add(final String actionPath,
			final Class<? extends Action> actionClass, final String methodName) {
		this.add(actionPath, actionClass, methodName, new RequestMethod[0]);
	}

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

		final Method method = ClassUtil.getMethod(actionClass, methodName,
				new Class<?>[0]);
		if (requestMethods == null || requestMethods.length == 0) {
			for (final RequestMethod requestMethod : CubbyUtils.DEFAULT_ACCEPT_ANNOTATION
					.value()) {
				this.add(actionPath, actionClass, method, requestMethod, false);
			}
		} else {
			for (final RequestMethod requestMethod : requestMethods) {
				this.add(actionPath, actionClass, method, requestMethod, false);
			}
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

		final Matcher matcher = URI_PARAMETER_MATCHING_PATTERN
				.matcher(actionPath);
		String uriRegex = actionPath;
		final List<String> uriParameterNames = new ArrayList<String>();
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
			uriRegex = StringUtil.replace(uriRegex, matcher.group(1),
					regexGroup(uriParameterRegex));
		}
		uriRegex = "^" + uriRegex + "$";
		final Pattern pattern = Pattern.compile(uriRegex);

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
				throw new DuplicateRoutingRuntimeException("ECUB0001",
						new Object[] { routing, duplication });
			}
		} else {
			routings.put(routing, routing);
			if (logger.isDebugEnabled()) {
				logger.log("DCUB0007", new Object[] { routing });
			}
		}
	}

	/**
	 * {@inheritDoc}
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
		final Iterator<Routing> iterator = routings.values().iterator();
		while (iterator.hasNext()) {
			final Routing routing = iterator.next();
			final Matcher matcher = routing.getPattern().matcher(path);
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
					final String inernalFowardPath = buildInternalForwardPath(uriParameters);

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
			final Map<String, String[]> parameters) {
		final StringBuilder builder = new StringBuilder(100);
		builder.append(INTERNAL_FORWARD_DIRECTORY);
		if (parameters != null && !parameters.isEmpty()) {
			builder.append("?");
			final QueryStringBuilder query = new QueryStringBuilder();
			if (!StringUtil.isEmpty(uriEncoding)) {
				query.setEncode(uriEncoding);
			}
			for (final Entry<String, String[]> entry : parameters.entrySet()) {
				for (final String parameter : entry.getValue()) {
					query.addParam(entry.getKey(), parameter);
				}
			}
			builder.append(query.toString());
		}
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
			final String methodName, final Map<String, String[]> parameters) {
		final Routing routing = findRouting(actionClass, methodName);
		final String actionPath = routing.getActionPath();

		final Matcher matcher = URI_PARAMETER_MATCHING_PATTERN
				.matcher(actionPath);
		final Map<String, String[]> copyOfParameters = new HashMap<String, String[]>(
				parameters);
		String redirectPath = actionPath;
		while (matcher.find()) {
			final String holder = matcher.group(2);
			final String[] tokens = CubbyUtils.split2(holder, ',');
			final String uriParameterName = tokens[0];
			if (!copyOfParameters.containsKey(uriParameterName)) {
				throw new ActionRuntimeException("ECUB0104", new Object[] {
						actionPath, uriParameterName });
			}
			final String value = copyOfParameters.remove(uriParameterName)[0];
			final String uriParameterRegex;
			if (tokens.length == 1) {
				uriParameterRegex = DEFAULT_URI_PARAMETER_REGEX;
			} else {
				uriParameterRegex = tokens[1];
			}
			if (!value.matches(uriParameterRegex)) {
				throw new ActionRuntimeException("ECUB0105",
						new Object[] { actionPath, uriParameterName, value,
								uriParameterRegex });
			}
			try {
				final String encodedValue = URLEncoder.encode(value,
						uriEncoding);
				redirectPath = StringUtil.replace(redirectPath, matcher
						.group(1), encodedValue);
			} catch (final UnsupportedEncodingException e) {
				throw new IORuntimeException(e);
			}
		}
		if (!copyOfParameters.isEmpty()) {
			final QueryStringBuilder builder = new QueryStringBuilder();
			builder.setEncode(uriEncoding);
			for (final Entry<String, String[]> entry : copyOfParameters
					.entrySet()) {
				for (final String value : entry.getValue()) {
					builder.addParam(entry.getKey(), value);
				}
			}
			redirectPath += "?" + builder.toString();
		}

		return redirectPath;
	}

	/**
	 * 指定されたクラス、メソッドに対応するルーティング情報を検索します。
	 * 
	 * @param actionClass
	 *            クラス
	 * @param methodName
	 *            メソッド
	 * @return ルーティング情報
	 * @throws ActionRuntimeException
	 *             ルーティング情報が見つからなかった場合
	 */
	private Routing findRouting(final Class<? extends Action> actionClass,
			final String methodName) {
		for (final Routing routing : routings.values()) {
			if (actionClass.getCanonicalName().equals(
					routing.getActionClass().getCanonicalName())) {
				if (methodName.equals(routing.getMethod().getName())) {
					return routing;
				}
			}
		}
		throw new ActionRuntimeException("ECUB0103", new Object[] {
				actionClass, methodName });
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 指定されたパッケージ名、クラス名から導出されるクラスがアクションクラスだった場合はルーティングを登録します。
	 * </p>
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
		final Class<?> clazz = ClassUtil.forName(className);
		if (namingConvention.isSkipClass(clazz)) {
			return;
		}
		if (!CubbyUtils.isActionClass(clazz)) {
			return;
		}
		final Class<? extends Action> actionClass = cast(clazz);

		for (final Method method : clazz.getMethods()) {
			if (CubbyUtils.isActionMethod(method)) {
				final String actionPath = CubbyUtils.getActionPath(actionClass,
						method);
				final RequestMethod[] acceptableRequestMethods = CubbyUtils
						.getAcceptableRequestMethods(clazz, method);
				for (final RequestMethod requestMethod : acceptableRequestMethods) {
					add(actionPath, actionClass, method, requestMethod, true);
				}
			}
		}
	}

	/**
	 * 指定されたクラスを <code>Class&lt;? extends Action&gt;</code> にキャストします。
	 * 
	 * @param clazz
	 *            クラス
	 * @return キャストされたクラス
	 */
	@SuppressWarnings("unchecked")
	private static Class<? extends Action> cast(final Class<?> clazz) {
		return (Class<? extends Action>) clazz;
	}

}
