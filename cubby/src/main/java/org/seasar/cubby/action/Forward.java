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

import static org.seasar.cubby.CubbyConstants.ATTR_ROUTINGS;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;

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
 * </p>
 * 
 * @author baba
 * @since 1.0.0
 */
public class Forward extends AbstractActionResult {

	/** ロガー。 */
	private static final Logger logger = Logger.getLogger(Forward.class);

	/** 空のパラメータ。 */
	private static final Map<String, String[]> EMPTY_PARAMETERS = Collections
			.emptyMap();

	/** フォワード先のパス。 */
	private final String path;

	/** ルーティング。 */
	private final Map<String, Routing> routings;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param path
	 *            フォワード先のパス
	 */
	public Forward(final String path) {
		this.path = path;
		this.routings = null;
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
	public Forward(final Class<? extends Action> actionClass,
			final String methodName, final Map<String, String[]> parameters) {
		final PathResolver pathResolver = SingletonS2Container
				.getComponent(PathResolver.class);
		this.path = pathResolver.buildInternalForwardPath(parameters);
		final Method method = ClassUtil.getMethod(actionClass, methodName,
				new Class[0]);
		final Routing routing = new ForwardRouting(actionClass, method);
		this.routings = Collections.singletonMap(null, routing);
	}

	/**
	 * インスタンスを生成します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            アクションメソッド名
	 * @since 1.1.0
	 */
	public Forward(final Class<? extends Action> actionClass,
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
			throws ServletException, IOException {

		final String forwardPath = calculateForwardPath(this.path, actionClass);
		if (this.routings != null) {
			request.setAttribute(ATTR_ROUTINGS, this.routings);
		}
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0001", new Object[] { forwardPath, routings });
		}
		final RequestDispatcher dispatcher = request
				.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0002", new Object[] { forwardPath });
		}
		action.postrender();

		action.getFlash().clear();
	}

	/**
	 * フォワードするパスを計算します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @return フォワードするパス
	 */
	protected String calculateForwardPath(final String path,
			final Class<? extends Action> actionClass) {
		final String absolutePath;
		if (path.startsWith("/")) {
			absolutePath = path;
		} else {
			final String actionDirectory = CubbyUtils
					.getActionDirectory(actionClass);
			if (StringUtil.isEmpty(actionDirectory)) {
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
	 * フォワード前の処理を行います。
	 * <p>
	 * {@link Action#prerender()} を実行します。
	 * </p>
	 */
	@Override
	public void prerender(final Action action) {
		action.prerender();
	}

	/**
	 * アクションメソッドへフォワードするためのルーティング。
	 * 
	 * @author baba
	 * @since 1.1.0
	 */
	private static class ForwardRouting implements Routing {

		/** アクションクラス。 */
		private Class<? extends Action> actionClass;

		/** アクションメソッド。 */
		private Method method;

		/**
		 * {@inheritDoc}
		 */
		private ForwardRouting(final Class<? extends Action> actionClass,
				final Method method) {
			this.actionClass = actionClass;
			this.method = method;
		}

		/**
		 * {@inheritDoc}
		 */
		public Class<? extends Action> getActionClass() {
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
