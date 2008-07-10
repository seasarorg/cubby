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
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.QueryStringBuilder;
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
 * 
 * @author baba
 * @since 1.0.0
 */
public class Forward extends AbstractActionResult {

	private static final Logger logger = Logger.getLogger(Forward.class);

	/** 空のパラメータ。 */
	private static final Map<String, String[]> EMPTY_PARAMETERS = Collections
			.emptyMap();

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
	 * @param parameters
	 *            パラメータ
	 * @since 1.0.5
	 */
	public Forward(final Class<? extends Action> actionClass,
			String methodName, final Map<String, String[]> parameters) {
		final StringBuilder builder = new StringBuilder();
		builder.append("/");
		builder.append(CubbyConstants.INTERNAL_FORWARD_DIRECTORY);
		builder.append("/");
		builder.append(actionClass.getCanonicalName());
		builder.append("/");
		builder.append(methodName);
		if (parameters != null && !parameters.isEmpty()) {
			builder.append("?");
			final QueryStringBuilder queryString = new QueryStringBuilder();
			for (final Entry<String, String[]> parameter : parameters
					.entrySet()) {
				final String name = parameter.getKey();
				for (final String value : parameter.getValue()) {
					queryString.addParam(name, value);
				}
			}
			builder.append(queryString.toString());
		}
		this.path = builder.toString();
	}

	/**
	 * インスタンスを生成します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            アクションメソッド名
	 * @since 1.0.5
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
	public void execute(final ActionContext context,
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final Action action = context.getAction();
		final String actionDirectory = CubbyUtils.getActionDirectory(context
				.getComponentDef().getComponentClass());

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
	public void prerender(final ActionContext context) {
		final Action action = context.getAction();
		action.prerender();
	}

}
