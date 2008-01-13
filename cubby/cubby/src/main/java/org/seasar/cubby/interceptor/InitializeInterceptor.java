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
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.ThreadContext;
import org.seasar.cubby.dxo.FormDxo;

/**
 * コントローラの初期化や実行結果のrequest/sessionへの反映などを行うインターセプタです。
 * {@link Action#initialize()}、{@link Action#prerender()} の実行を行います。
 * 
 * @author agata
 * @author baba
 * @since 1.0
 */
public class InitializeInterceptor implements MethodInterceptor {

	private HttpServletRequest request;

	private ActionContext context;

	public void setRequest(final HttpServletRequest request) {
		this.request = request;
	}

	public void setActionContext(final ActionContext context) {
		this.context = context;
	}

	public Object invoke(final MethodInvocation invocation) throws Throwable {
		setupParams(context, request);
		setupForm(context, request);

		final Action action = context.getAction();
		action.initialize();

		final ActionResult result = (ActionResult) invocation.proceed();
		if (result != null) {
			result.prerender(context);
		}

		return result;
	}

	void setupParams(final ActionContext context,
			final HttpServletRequest request) {
		final CubbyConfiguration configuration = ThreadContext
				.getConfiguration();
		final RequestParser requestParser = configuration.getRequestParser();
		final Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(request);
		request.setAttribute(ATTR_PARAMS, parameterMap);
	}

	private void setupForm(final ActionContext context,
			final HttpServletRequest request) {
		final Object formBean = context.getFormBean();
		if (formBean != null) {
			final FormDxo formDxo = context.getFormDxo();
			final Map<String, Object[]> params = getParams(request);
			formDxo.convert(params, formBean);
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object[]> getParams(
			final HttpServletRequest request) {
		return (Map<String, Object[]>) request.getAttribute(ATTR_PARAMS);
	}

}
