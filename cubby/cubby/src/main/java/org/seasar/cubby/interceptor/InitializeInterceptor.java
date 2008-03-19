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
package org.seasar.cubby.interceptor;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.ThreadContext;
import org.seasar.cubby.dxo.FormDxo;

/**
 * コントローラの初期化や実行結果のrequest/sessionへの反映などを行うインターセプタです。
 * {@link Action#initialize()}、{@link ActionResult#prerender(ActionContext)}
 * の実行を行います。
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class InitializeInterceptor implements MethodInterceptor {

	/** リクエスト。 */
	private HttpServletRequest request;

	/** アクションのコンテキスト。 */
	private ActionContext context;

	/**
	 * リクエストを設定します。
	 * 
	 * @param request
	 *            リクエスト
	 */
	public void setRequest(final HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * アクションのコンテキストを設定します。
	 * 
	 * @param context
	 *            アクションのコンテキスト
	 */
	public void setContext(final ActionContext context) {
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 以下のようなフローでアクションメソッドを実行します。
	 * <ul>
	 * <li>{@link Action#parseRequest(HttpServletRequest)}を呼び出してリクエストをパースしてリクエストパラメータをパースします。パース後、リクエストの属性
	 * {@link CubbyConstants#ATTR_PARAMS} に設定。</li>
	 * <li>{@link Action#initialize()}を呼び出してアクションを初期化します。</li>
	 * <li>対象のメソッドを呼び出します。</li>
	 * <li>{@link ActionResult#prerender(ActionContext)}を呼び出します。</li>
	 * <li>メソッドの実行結果を返します。</li>
	 * </ul>
	 * </p>
	 */
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		final Action action = context.getAction();
		final Map<String, Object[]> parameterMap = parseRequest(request);
		request.setAttribute(ATTR_PARAMS, parameterMap);
		action.initialize();

		final Object formBean = context.getFormBean();
		if (formBean != null) {
			final FormDxo formDxo = context.getFormDxo();
			formDxo.convert(parameterMap, formBean);
		}

		final ActionResult result = (ActionResult) invocation.proceed();

		if (result != null) {
			result.prerender(context);
		}

		return result;
	}

	/**
	 * リクエストをパースしてパラメータを取り出し、{@link Map}に変換して返します。
	 * 
	 * @param request
	 *            リクエスト
	 * @return リクエストパラメータの{@link Map}
	 */
	private Map<String, Object[]> parseRequest(final HttpServletRequest request) {
		final CubbyConfiguration configuration = ThreadContext
				.getConfiguration();
		final RequestParser requestParser = configuration.getRequestParser();
		final Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(request);
		return parameterMap;
	}

}
