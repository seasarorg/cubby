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
package org.seasar.cubby.handler.impl;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;
import static org.seasar.cubby.validator.ValidationUtils.getValidation;
import static org.seasar.cubby.validator.ValidationUtils.getValidationRules;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.internal.util.RequestUtils;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationFailBehaviour;
import org.seasar.cubby.validator.ValidationRules;

/**
 * 入力検証を行います。
 * 
 * @author agata
 * @author baba
 * @since 2.0.0
 */
public class ValidationActionHandler implements ActionHandler {

	/**
	 * {@inheritDoc}
	 * <p>
	 * 入力検証を実行します。
	 * </p>
	 */
	public ActionResult handle(final HttpServletRequest request,
			final HttpServletResponse response,
			final ActionContext actionContext,
			final ActionHandlerChain actionHandlerChain) throws Exception {
		try {
			validate(request, actionContext);
			return actionHandlerChain.chain(request, response, actionContext);
		} catch (final ValidationException e) {
			return handleValidationException(e, request, actionContext);
		}
	}

	/**
	 * 入力検証を行います。
	 * <p>
	 * 入力検証はフェーズごとに実行され、そのフェーズの入力検証でエラーがあった({@link ActionErrors}
	 * にメッセージが登録された)場合には {@link ValidationException} をスローします。
	 * </p>
	 * 
	 * @param request
	 *            リクエスト
	 * @param actionContext
	 *            アクションコンテキスト
	 * @throws ValidationException
	 *             入力検証にエラーがあった場合
	 */
	protected void validate(final HttpServletRequest request,
			final ActionContext actionContext) {
		final Validation validation = getValidation(actionContext
				.getActionMethod());
		if (validation != null) {
			final Map<String, Object[]> params = RequestUtils.getAttribute(
					request, ATTR_PARAMS);
			final ValidationRules validationRules = getValidationRules(
					actionContext.getAction(), validation.rules());
			final Object formBean = actionContext.getFormBean();
			validationRules.validate(params, formBean, actionContext
					.getActionErrors());
		}
	}

	/**
	 * {@link #validate(HttpServletRequest, ActionContext)} とアクションの実行中に発生した
	 * {@link ValidationException} を処理します。
	 * <p>
	 * <ul>
	 * <li>{@link ValidationException} にメッセージが指定されていた場合はそれを {@link ActionErrors}
	 * に設定</li>
	 * <li>リクエストの属性 {@link CubbyConstants#ATTR_VALIDATION_FAIL} に
	 * <code>true</code> を設定</li>
	 * <li>{@link ValidationRules#fail(String)} の呼び出し</li>
	 * </ul>
	 * </p>
	 * 
	 * @param e
	 *            処理対象の例外
	 * @param request
	 *            リクエスト
	 * @param actionContext
	 *            アクションコンテキスト
	 * @return {@link ValidationRules#fail(String)} が返す値
	 */
	protected ActionResult handleValidationException(
			final ValidationException e, final HttpServletRequest request,
			final ActionContext actionContext) {
		request.setAttribute(ATTR_VALIDATION_FAIL, Boolean.TRUE);
		final ValidationFailBehaviour behaviour = e.getBehaviour();
		return behaviour.getValidationErrorActionResult(actionContext);
	}

}
