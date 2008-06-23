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
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.container.SingletonS2Container;
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
 * 使用例4 : リダイレクト先をクラスとメソッド名で指定(パラメータつき)
 * 
 * <pre>
 * Map&lt;String, String[]&gt; parameters = new HashMap();
 * parameters.put(&quot;value1&quot;, new String[] { &quot;12345&quot; });
 * return new Redirect(TodoListAction.class, &quot;show&quot;, parameters);
 * </pre>
 * 
 * </p>
 * <p>
 * 通常は {@link HttpServletResponse#encodeRedirectURL(String)} によってエンコードされた URL にリダイレクトするため、URL にセッション ID が埋め込まれます。
 * URL にセッション ID を埋め込みたくない場合は、noEncodeURL() を使用してください。
 * 
 * <pre>
 * return new Redirect(&quot;/todo/list&quot;).noEnocdeURL();
 * </pre>
 * </p>
 * 
 * @author baba
 * @since 1.0.0
 */
public class Redirect extends AbstractActionResult {

	/** ロガー。 */
	private static final Logger logger = Logger.getLogger(Redirect.class);

	/** 空のパラメータ。 */
	private static final Map<String, String[]> EMPTY_PARAMETERS = Collections
			.emptyMap();

	/** リダイレクト先のパス。 */
	private final String path;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param path
	 *            リダイレクト先のパス
	 */
	public Redirect(final String path) {
		this.path = path;
	}

	/**
	 * インスタンスを生成します。
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
		final PathResolver pathResolver = SingletonS2Container
				.getComponent(PathResolver.class);
		final String redirectPath = pathResolver.reverseLookup(actionClass,
				methodName, parameters);
		this.path = redirectPath;
	}

	/**
	 * インスタンスを生成します。
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
	 * パスを取得します。
	 * 
	 * @return パス
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute(final Action action,
			final Class<? extends Action> actionClass, final Method method,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {

		final String url = calculateRedirectURL(this.path, actionClass, request);
		final String encoded = encodeURL(url, response);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0003", new String[] { encoded });
		}
		response.sendRedirect(encoded);
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
		final String redirectURL;
		final String contextPath;
		if ("/".equals(request.getContextPath())) {
			contextPath = "";
		} else {
			contextPath = request.getContextPath();
		}
		if (path.startsWith("/")) {
			redirectURL = contextPath + path;
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
				redirectURL = builder.toString();
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
				redirectURL = builder.toString();
			}
		}
		return redirectURL;
	}

	/**
	 * URL をエンコードします。
	 * 
	 * @param url
	 *            URL
	 * @param response
	 *            レスポンス
	 * @return エンコードされた URL
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
		return new Redirect(path) {

			@Override
			protected String encodeURL(final String url,
					final HttpServletResponse response) {
				return url;
			}

		};
	}

}
