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
package org.seasar.cubby.action;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.QueryStringBuilder;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

/**
 * 指定されたパスにリダイレクトする {@link ActionResult} です。
 * <p>
 * アクションメソッドの戻り値としてこのインスタンスを指定することで、指定されたパスにリダイレクトします。
 * </p>
 * <p>
 * 使用例1 : リダイレクト先を相対パスで指定
 * 
 * <pre>
 * return new Redirect(&quot;list&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例2 : リダイレクト先を絶対パスで指定
 * 
 * <pre>
 * return new Redirect(&quot;/todo/list&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例3 : リダイレクト先をクラスとメソッド名で指定
 * 
 * <pre>
 * return new Redirect(TodoListAction.class, &quot;show&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例4 : リダイレクト先をクラスとメソッド名で指定(paramメソッドによるパラメータつき)
 * 
 * <pre>
 * return new Redirect(TodoListAction.class, &quot;show&quot;).param(&quot;value1&quot;, &quot;12345&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例5 : リダイレクト先をクラスとメソッド名で指定(Mapによるパラメータつき)
 * 
 * <pre>
 * Map&lt;String, String[]&gt; parameters = new HashMap();
 * parameters.put(&quot;value1&quot;, new String[] { &quot;12345&quot; });
 * return new Redirect(TodoListAction.class, &quot;show&quot;, parameters);
 * </pre>
 * 
 * </p>
 * <p>
 * 通常は {@link HttpServletResponse#encodeRedirectURL(String)} によってエンコードされた URL
 * にリダイレクトするため、URL にセッション ID が埋め込まれます。 URL にセッション ID を埋め込みたくない場合は、noEncodeURL()
 * を使用してください。
 * 
 * <pre>
 * return new Redirect(&quot;/todo/list&quot;).noEnocdeURL();
 * </pre>
 * 
 * </p>
 * 
 * @author baba
 * @since 1.0.0
 */
public class Redirect implements ActionResult {

	/** ロガー。 */
	private static final Logger logger = Logger.getLogger(Redirect.class);

	/** 空のパラメータ。 */
	private static final Map<String, String[]> EMPTY_PARAMETERS = Collections
			.emptyMap();

	/** リダイレクト先のパス。 */
	private String path;

	/** リダイレクト先のプロトコル。 */
	private final String protocol;

	/** リダイレクト先のポート。 */
	private final int port;

	/** リダイレクト先のアクションクラス */
	private Class<? extends Action> actionClass;

	/** リダイレクト先のアクションクラスのメソッド名 */
	private String methodName;

	/** リダイレクト時のパラメータ */
	private Map<String, String[]> parameters;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param path
	 *            リダイレクト先のパス
	 */
	public Redirect(final String path) {
		this(path, null);
	}

	/**
	 * インスタンスを生成します。
	 * 
	 * @param path
	 *            リダイレクト先のパス
	 * @param protocol
	 *            リダイレクト先のプロトコル
	 * @since 1.1.0
	 */
	public Redirect(final String path, final String protocol) {
		this(path, protocol, -1);
	}

	/**
	 * インスタンスを生成します。
	 * 
	 * @param path
	 *            リダイレクト先のパス
	 * @param protocol
	 *            リダイレクト先のプロトコル
	 * @param port
	 *            リダイレクト先のポート
	 * @since 1.1.0
	 */
	public Redirect(final String path, final String protocol, final int port) {
		this.path = path;
		this.protocol = protocol;
		this.port = port;
	}

	/**
	 * 指定されたアクションメソッドへリダイレクトするインスタンスを生成します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            メソッド名
	 * @throws org.seasar.cubby.exception.ActionRuntimeException
	 *             リダイレクト先パスの構築に失敗した場合
	 * @since 1.1.0
	 */
	public Redirect(final Class<? extends Action> actionClass,
			final String methodName) {
		this(actionClass, methodName, EMPTY_PARAMETERS);
	}

	/**
	 * 指定されたアクションメソッドへリダイレクトするインスタンスを生成します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            メソッド名
	 * @param parameters
	 *            パラメータ
	 * @throws org.seasar.cubby.exception.ActionRuntimeException
	 *             リダイレクト先パスの構築に失敗した場合
	 * @since 1.1.0
	 */
	public Redirect(final Class<? extends Action> actionClass,
			final String methodName, final Map<String, String[]> parameters) {
		this(actionClass, methodName, parameters, null);
	}

	/**
	 * 指定されたアクションメソッドへリダイレクトするインスタンスを生成します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            メソッド名
	 * @param parameters
	 *            パラメータ
	 * @param protocol
	 *            リダイレクト先のプロトコル
	 * @throws org.seasar.cubby.exception.ActionRuntimeException
	 *             リダイレクト先パスの構築に失敗した場合
	 * @since 1.1.0
	 */
	public Redirect(final Class<? extends Action> actionClass,
			final String methodName, final Map<String, String[]> parameters,
			final String protocol) {
		this(actionClass, methodName, parameters, protocol, -1);
	}

	/**
	 * 指定されたアクションメソッドへリダイレクトするインスタンスを生成します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            メソッド名
	 * @param parameters
	 *            パラメータ
	 * @param protocol
	 *            リダイレクト先のプロトコル
	 * @param port
	 *            リダイレクト先のポート
	 * @throws org.seasar.cubby.exception.ActionRuntimeException
	 *             リダイレクト先パスの構築に失敗した場合
	 * @since 1.1.0
	 */
	public Redirect(final Class<? extends Action> actionClass,
			final String methodName, final Map<String, String[]> parameters,
			final String protocol, final int port) {
		this.actionClass = actionClass;
		this.methodName = methodName;
		this.parameters = parameters;
		this.protocol = protocol;
		this.port = port;
	}

	/**
	 * パスを取得します。
	 * 
	 * @return パス
	 */
	public String getPath() {
		if (isReverseLookupRedirect()) {
			final S2Container container = SingletonS2ContainerFactory
			.getContainer();
			final PathResolver pathResolver = (PathResolver) container
			.getComponent(PathResolver.class);
			final String redirectPath = pathResolver.reverseLookup(actionClass,
			methodName, parameters);
			this.path = redirectPath;
		}
		return this.path;
	}
	
	/**
	 * アクションクラスを指定したリダイレクトかどうかを判定します。
	 * @return アクションクラスを指定したリダイレクトならtrue
	 */
	private boolean isReverseLookupRedirect() {
		return this.actionClass != null && this.methodName != null && this.parameters != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute(final Action action,
			final Class<? extends Action> actionClass, final Method method,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		final String redirectURL = calculateRedirectURL(getPath(), actionClass,
				request);
		final String encodedRedirectURL = encodeURL(redirectURL, response);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0003", new String[] { encodedRedirectURL });
		}
		response.sendRedirect(encodedRedirectURL);
	}

	/**
	 * リダイレクトする URL を計算します。
	 * 
	 * @param path
	 *            パス
	 * @param actionClass
	 *            アクションクラス
	 * @param request
	 *            リクエスト
	 * @return URL
	 */
	protected String calculateRedirectURL(final String path,
			final Class<? extends Action> actionClass,
			final HttpServletRequest request) {
		try {
			final String redirectURL = new URL(path).toExternalForm();
			return redirectURL;
		} catch (MalformedURLException e) {
			final String redirectURL = calculateInternalRedirectURL(path,
					actionClass, request);
			return redirectURL;
		}
	}

	/**
	 * リダイレクトする URL を計算します。
	 * 
	 * @param path
	 *            パス
	 * @param actionClass
	 *            アクションクラス
	 * @param request
	 *            リクエスト
	 * @return URL
	 */
	private String calculateInternalRedirectURL(final String path,
			final Class<? extends Action> actionClass,
			final HttpServletRequest request) {
		final String redirectPath;
		final String contextPath;
		if ("/".equals(request.getContextPath())) {
			contextPath = "";
		} else {
			contextPath = request.getContextPath();
		}
		if (path.startsWith("/")) {
			redirectPath = contextPath + path;
		} else {
			final String actionDirectory = CubbyUtils
					.getActionDirectory(actionClass);
			if (StringUtil.isEmpty(actionDirectory)) {
				final StringBuilder builder = new StringBuilder();
				builder.append(contextPath);
				if (!contextPath.endsWith("/")) {
					builder.append("/");
				}
				builder.append(path);
				redirectPath = builder.toString();
			} else {
				final StringBuilder builder = new StringBuilder();
				builder.append(contextPath);
				if (!contextPath.endsWith("/")
						&& !actionDirectory.startsWith("/")) {
					builder.append("/");
				}
				builder.append(actionDirectory);
				if (!actionDirectory.endsWith("/")) {
					builder.append("/");
				}
				builder.append(path);
				redirectPath = builder.toString();
			}
		}

		if (protocol == null) {
			return redirectPath;
		} else {
			try {
				final URL currentURL = new URL(request.getRequestURL()
						.toString());
				final String redirectProtocol = this.protocol == null ? currentURL
						.getProtocol()
						: this.protocol;
				final int redirectPort = this.port < 0 ? currentURL.getPort()
						: this.port;
				final URL redirectURL = new URL(redirectProtocol, currentURL
						.getHost(), redirectPort, redirectPath);
				return redirectURL.toExternalForm();
			} catch (MalformedURLException e) {
				throw new IORuntimeException(e);
			}
		}
	}

	/**
	 * URL をエンコードします。
	 * 
	 * @param url
	 *            URL
	 * @param response
	 *            レスポンス
	 * @return エンコードされた URL
	 * @see HttpServletResponse#encodeRedirectURL(String)
	 */
	protected String encodeURL(final String url,
			final HttpServletResponse response) {
		return response.encodeRedirectURL(url);
	}

	/**
	 * {@link HttpServletResponse#encodeRedirectURL(String)}
	 * によってエンコードせずにリダイレクトします。
	 * <p>
	 * URL 埋め込みのセッション ID を出力したくない場合に使用してください。
	 * </p>
	 * 
	 * @return リダイレクトする URL
	 * @since 1.1.0
	 */
	public ActionResult noEncodeURL() {
		return new Redirect(getPath()) {

			@Override
			protected String encodeURL(final String url,
					final HttpServletResponse response) {
				return url;
			}

		};
	}
	
	/**
	 * パラメータを追加します。
	 * @param paramName パラメータ名
	 * @param paramValue パラメータの値。{@code Object#toString()}の結果が値として使用されます。
	 * @return リダイレクトする URL
	 */
	public Redirect param(String paramName, Object paramValue) {
		return param(paramName, new String[] { paramValue.toString() });
	}
	
	/**
	 * パラメータを追加します。
	 * @param paramName パラメータ名
	 * @param paramValues パラメータの値の配列。配列の要素の{@code Object#toString()}の結果がそれぞれの値として使用されます。
	 * @return リダイレクトする URL
	 */
	public Redirect param(final String paramName, final Object[] paramValues) {
		return param(paramName, toStringArray(paramValues));
	}

	/**
	 * パラメータを追加します。
	 * @param paramName パラメータ名
	 * @param paramValues パラメータの値
	 * @return リダイレクトする URL
	 */
	public Redirect param(final String paramName, final String[] paramValues) {
		if (isReverseLookupRedirect()) {
			if (this.parameters == EMPTY_PARAMETERS) {
				this.parameters = new HashMap<String, String[]>();
			}
			this.parameters.put(paramName, paramValues);
		} else {
			QueryStringBuilder builder = new QueryStringBuilder(this.path);
			builder.addParam(paramName, paramValues);
			this.path = builder.toString();
		}
		return this;
	}
	
	/**
	 * {@code Object#toString()}型の配列を{@code Object#toString()}型の配列に変換します。
	 * <p>
	 * 配列のそれぞれの要素に対して{@code Object#toString()}を使用して変換します。
	 * </p>
	 * @param paramValues {@code Object#toString()}型の配列
	 * @return {@code Object#toString()}型の配列。
	 */
	private String[] toStringArray(final Object[] paramValues) {
		String[] values = new String[paramValues.length];
		for (int i = 0; i < paramValues.length; i++) {
			values[i] = paramValues[i].toString();
		}
		return values;
	}
}
