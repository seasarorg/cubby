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

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.log.Logger;
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

	/** フォワード先のパス。 */
	private final String path;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param path
	 *            フォワード先のパス
	 */
	public Forward(final String path) {
		this.path = path;
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
		this(CubbyUtils.getInternalForwardPath(actionClass, methodName));
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
		final String actionDirectory = CubbyUtils
				.getActionDirectory(actionClass);

		final String absolutePath;
		if (this.path.startsWith("/")) {
			absolutePath = this.path;
		} else if (StringUtil.isEmpty(actionDirectory)) {
			absolutePath = "/" + this.path;
		} else {
			absolutePath = "/" + actionDirectory + "/" + this.path;
		}
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0001", new String[] { absolutePath });
		}
		final RequestDispatcher dispatcher = request
				.getRequestDispatcher(absolutePath);
		dispatcher.forward(request, response);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0002", new String[] { absolutePath });
		}
		action.postrender();

		action.getFlash().clear();
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

}
