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

import static org.seasar.cubby.CubbyConstants.ATTR_ROUTING;
import static org.seasar.cubby.internal.util.LogMessages.format;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.internal.routing.PathResolver;
import org.seasar.cubby.internal.routing.Routing;
import org.seasar.cubby.internal.spi.PathResolverProvider;
import org.seasar.cubby.internal.spi.ProviderFactory;
import org.seasar.cubby.internal.util.CubbyUtils;
import org.seasar.cubby.internal.util.QueryStringBuilder;
import org.seasar.cubby.internal.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 指定されたパスにフォワードする {@link ActionResult} です。
 * <p>
 * アクションメソッドの戻り値としてこのインスタンスを指定することで、指定されたパスにフォワードします。
 * </p>
 * <p>
 * 使用例1 : フォワード先を相対パスで指定
 * 
 * <pre>
 * return new Forward(&quot;list.jsp&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例2 : フォワード先を絶対パスで指定
 * 
 * <pre>
 * return new Forward(&quot;/todo/list.jsp&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例2 : フォワード先をクラスとメソッド名で指定
 * 
 * <pre>
 * return new Forward(TodoListAction.class, &quot;show&quot;);
 * </pre>
 * 
 * <p>
 * 使用例3 : フォワード先をクラスとメソッド名で指定(paramメソッドによるパラメータつき)
 * 
 * <pre>
 * return new Forward(TodoListAction.class, &quot;show&quot;).param(&quot;value1&quot;, &quot;12345&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例3 : フォワード先をクラスとメソッド名で指定(Mapによるパラメータつき)
 * 
 * <pre>
 * Map&lt;String, String[]&gt; parameters = new HashMap();
 * parameters.put(&quot;value1&quot;, new String[] { &quot;12345&quot; });
 * return new Forward(TodoListAction.class, &quot;show&quot;, parameters);
 * </pre>
 * 
 * </p>
 * <p>
 * フォワード前には {@link Action#invokePreRenderMethod(Method)} を実行します。 フォワード後には
 * {@link Action#invokePostRenderMethod(Method)} を実行し、フラッシュメッセージをクリアします。
 * </p>
 * 
 * @author baba
 * @since 1.0.0
 */
public class Forward implements ActionResult {

	/** ロガー。 */
	private static final Logger logger = LoggerFactory.getLogger(Forward.class);

	/** 空のパラメータ。 */
	private static final Map<String, String[]> EMPTY_PARAMETERS = Collections
			.emptyMap();

	/** フォワード先のパス。 */
	private String path;

	/** ルーティング。 */
	private final Routing routing;

	/** フォワード先のアクションクラス */
	private Class<?> actionClass;

	/** フォワード先のアクションクラスのメソッド名 */
	private String methodName;

	/** フォワード時のパラメータ */
	private Map<String, String[]> parameters;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param path
	 *            フォワード先のパス
	 */
	public Forward(final String path) {
		this.path = path;
		this.routing = null;
	}

	/**
	 * インスタンスを生成します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            アクションメソッド名
	 * @param parameters
	 *            パラメータ
	 * @since 1.1.0
	 */
	public Forward(final Class<?> actionClass, final String methodName,
			final Map<String, String[]> parameters) {
		this.actionClass = actionClass;
		this.methodName = methodName;
		this.parameters = parameters;
		try {
			final Method method = actionClass.getMethod(methodName);
			this.routing = new ForwardRouting(actionClass, method);
		} catch (final NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 指定されたアクションクラスのindexメソッドへフォワードするインスタンスを生成します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @since 1.1.0
	 */
	public Forward(final Class<?> actionClass) {
		this(actionClass, "index");
	}

	/**
	 * 指定されたアクションメソッドへフォワードするインスタンスを生成します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            アクションメソッド名
	 * @since 1.1.0
	 */
	public Forward(final Class<?> actionClass, final String methodName) {
		this(actionClass, methodName, EMPTY_PARAMETERS);
	}

	/**
	 * パスを取得します。
	 * 
	 * @param characterEncoding
	 *            URI のエンコーディング
	 * @return パス
	 */
	public String getPath(final String characterEncoding) {
		if (isReverseLookupRedirect()) {
			final PathResolverProvider pathResolverProvider = ProviderFactory
					.get(PathResolverProvider.class);
			final PathResolver pathResolver = pathResolverProvider
					.getPathResolver();
			final String forwardPath = pathResolver.reverseLookup(actionClass,
					methodName, parameters, characterEncoding);
			this.path = forwardPath;
		}
		return this.path;
	}

	/**
	 * アクションクラスを指定したフォワードかどうかを判定します。
	 * 
	 * @return アクションクラスを指定したフォワードならtrue
	 */
	private boolean isReverseLookupRedirect() {
		return this.actionClass != null && this.methodName != null
				&& this.parameters != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute(final ActionContext actionContext,
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		actionContext.invokePreRenderMethod();

		final String forwardPath = calculateForwardPath(getPath(request
				.getCharacterEncoding()), actionContext.getActionClass(),
				request.getCharacterEncoding());
		if (this.routing != null) {
			request.setAttribute(ATTR_ROUTING, this.routing);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0001", forwardPath, routing));
		}
		final RequestDispatcher dispatcher = request
				.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0002", forwardPath));
		}

		actionContext.invokePostRenderMethod();
		actionContext.clearFlash();
	}

	/**
	 * フォワードするパスを計算します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param characterEncoding
	 *            URI のエンコーディング
	 * @return フォワードするパス
	 */
	protected String calculateForwardPath(final String path,
			final Class<?> actionClass, final String characterEncoding) {
		final String absolutePath;
		if (getPath(characterEncoding).startsWith("/")) {
			absolutePath = path;
		} else {
			final String actionDirectory = CubbyUtils
					.getActionDirectory(actionClass);
			if (StringUtils.isEmpty(actionDirectory)) {
				absolutePath = "/" + path;
			} else {
				final StringBuilder builder = new StringBuilder();
				if (!actionDirectory.startsWith("/")) {
					builder.append("/");
				}
				builder.append(actionDirectory);
				if (!actionDirectory.endsWith("/")) {
					builder.append("/");
				}
				builder.append(path);
				absolutePath = builder.toString();
			}
		}
		return absolutePath;
	}

	/**
	 * パラメータを追加します。
	 * 
	 * @param paramName
	 *            パラメータ名
	 * @param paramValue
	 *            パラメータの値。{@code Object#toString()}の結果が値として使用されます。
	 * @return フォワードする URL
	 */
	public Forward param(final String paramName, final Object paramValue) {
		return param(paramName, new String[] { paramValue.toString() });
	}

	/**
	 * パラメータを追加します。
	 * 
	 * @param paramName
	 *            パラメータ名
	 * @param paramValues
	 *            パラメータの値の配列。配列の要素の{@code Object#toString()}の結果がそれぞれの値として使用されます。
	 * @return フォワードする URL
	 */
	public Forward param(final String paramName, final Object[] paramValues) {
		return param(paramName, toStringArray(paramValues));
	}

	/**
	 * パラメータを追加します。
	 * 
	 * @param paramName
	 *            パラメータ名
	 * @param paramValues
	 *            パラメータの値
	 * @return フォワードする URL
	 */
	public Forward param(final String paramName, final String[] paramValues) {
		if (isReverseLookupRedirect()) {
			if (this.parameters == EMPTY_PARAMETERS) {
				this.parameters = new HashMap<String, String[]>();
			}
			this.parameters.put(paramName, paramValues);
		} else {
			final QueryStringBuilder builder = new QueryStringBuilder(this.path);
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
	 * 
	 * @param paramValues
	 *            {@code Object#toString()}型の配列
	 * @return {@code Object#toString()}型の配列。
	 */
	private String[] toStringArray(final Object[] paramValues) {
		final String[] values = new String[paramValues.length];
		for (int i = 0; i < paramValues.length; i++) {
			values[i] = paramValues[i].toString();
		}
		return values;
	}

	/**
	 * アクションメソッドへフォワードするためのルーティング。
	 * 
	 * @author baba
	 * @since 1.1.0
	 */
	private static class ForwardRouting implements Routing {

		/** アクションクラス。 */
		private final Class<?> actionClass;

		/** アクションメソッド。 */
		private final Method method;

		/**
		 * {@inheritDoc}
		 */
		private ForwardRouting(final Class<?> actionClass, final Method method) {
			this.actionClass = actionClass;
			this.method = method;
		}

		/**
		 * {@inheritDoc}
		 */
		public Class<?> getActionClass() {
			return actionClass;
		}

		/**
		 * {@inheritDoc}
		 */
		public Method getMethod() {
			return method;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getActionPath() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public List<String> getUriParameterNames() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public Pattern getPattern() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public RequestMethod getRequestMethod() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getOnSubmit() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public int getPriority() {
			return 0;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isAuto() {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isAcceptable(final String requestMethod) {
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return new StringBuilder().append("[").append(method).append("]")
					.toString();
		}

	}

}
