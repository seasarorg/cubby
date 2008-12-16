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
package org.seasar.cubby.handler.impl;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.internal.controller.RequestParameterBinder;
import org.seasar.cubby.internal.controller.impl.RequestParameterBinderImpl;
import org.seasar.cubby.internal.util.RequestUtils;

/**
 * 要求パラメータをフォームオブジェクトにバインドするアクションハンドラーです。
 * 
 * @author baba
 * @since 2.0.0
 */
public class ParameterBindingActionHandler implements ActionHandler {

	/** リクエストパラメータをオブジェクトへバインドするクラス。 */
	private final RequestParameterBinder requestParameterBinder = new RequestParameterBinderImpl();

	/**
	 * {@inheritDoc}
	 * <p>
	 * アクションメソッドにフォームオブジェクトが定義されている場合に、{@link RequestParameterBinder}
	 * によって要求パラメータをフォームオブジェクトにバインドします。
	 * </p>
	 */
	public ActionResult handle(final HttpServletRequest request,
			final HttpServletResponse response,
			final ActionContext actionContext,
			final ActionHandlerChain actionHandlerChain) throws Exception {

		final Object formBean = actionContext.getFormBean();
		if (formBean != null) {
			final Map<String, Object[]> parameterMap = RequestUtils.getAttribute(
					request, ATTR_PARAMS);
			requestParameterBinder.bind(parameterMap, formBean, actionContext);
		}

		final ActionResult result = actionHandlerChain.chain(request, response,
				actionContext);
		return result;
	}

}
