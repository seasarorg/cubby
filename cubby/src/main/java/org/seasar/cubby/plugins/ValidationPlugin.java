/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.cubby.plugins;

import static org.seasar.cubby.CubbyConstants.ATTR_CONVERSION_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;
import static org.seasar.cubby.validator.ValidationUtils.getValidation;
import static org.seasar.cubby.validator.ValidationUtils.getValidationRules;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.internal.action.impl.ActionErrorsImpl;
import org.seasar.cubby.internal.controller.RequestParameterBinder;
import org.seasar.cubby.internal.controller.impl.RequestParameterBinderImpl;
import org.seasar.cubby.internal.util.RequestUtils;
import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.plugin.ActionInvocation;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationFailBehaviour;
import org.seasar.cubby.validator.ValidationRules;

/**
 * 要求パラメータからフォームオブジェクトへの値のバインドと、それに伴う型変換、要求パラメータの検証を行うプラグインです。
 * 
 * @author baba
 */
public class ValidationPlugin extends AbstractPlugin {

	/** リクエストパラメータをオブジェクトへバインドするクラス。 */
	private final RequestParameterBinder requestParameterBinder = new RequestParameterBinderImpl();

	@Override
	public ActionResult invokeAction(final ActionInvocation invocation)
			throws Exception {
		final HttpServletRequest request = invocation.getRequest();
		final Map<String, Object[]> parameterMap = RequestUtils.getAttribute(
				request, ATTR_PARAMS);

		final ActionContext actionContext = invocation.getActionContext();
		final Object formBean = actionContext.getFormBean();

		if (formBean != null) {
			final ActionErrors conversionErros = new ActionErrorsImpl();
			requestParameterBinder.bind(parameterMap, formBean, actionContext,
					conversionErros);
			request.setAttribute(ATTR_CONVERSION_ERRORS, conversionErros);
		}

		try {
			final Validation validation = getValidation(actionContext
					.getActionMethod());
			if (validation != null) {
				final ValidationRules validationRules = getValidationRules(
						actionContext.getAction(), validation.rules());
				validationRules.validate(parameterMap, formBean, actionContext
						.getActionErrors());
			}
			return invocation.proceed();
		} catch (final ValidationException e) {
			request.setAttribute(ATTR_VALIDATION_FAIL, Boolean.TRUE);
			final ValidationFailBehaviour behaviour = e.getBehaviour();
			return behaviour.getValidationErrorActionResult(actionContext);
		}
	}

}
