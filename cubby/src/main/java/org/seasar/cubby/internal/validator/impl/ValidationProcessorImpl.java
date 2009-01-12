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
package org.seasar.cubby.internal.validator.impl;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;
import static org.seasar.cubby.validator.ValidationUtils.getValidation;
import static org.seasar.cubby.validator.ValidationUtils.getValidationRules;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.internal.util.RequestUtils;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationFailBehaviour;
import org.seasar.cubby.validator.ValidationPhase;
import org.seasar.cubby.validator.ValidationProcessor;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;

/**
 * 入力検証処理の実装です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class ValidationProcessorImpl implements ValidationProcessor {

	/**
	 * {@inheritDoc}
	 */
	public void process(final HttpServletRequest request,
			final ActionContext actionContext) {
		final Validation validation = getValidation(actionContext
				.getActionMethod());
		if (validation != null) {
			final Map<String, Object[]> params = RequestUtils.getAttribute(
					request, ATTR_PARAMS);
			final ValidationRules validationRules = getValidationRules(
					actionContext.getAction(), validation.rules());
			final Object formBean = actionContext.getFormBean();
			validate(validationRules, params, formBean, actionContext
					.getActionErrors());
		}
	}

	/**
	 * すべてのフェーズに対する入力検証を実行します。
	 * 
	 * @param validationRules
	 *            入力検証ルールの集合
	 * @param params
	 *            リクエストパラメータ
	 * @param form
	 *            フォームオブジェクト
	 * @param errors
	 *            アクションのエラー
	 */
	private void validate(final ValidationRules validationRules,
			final Map<String, Object[]> params, final Object form,
			final ActionErrors errors) {
		for (final ValidationPhase validationPhase : validationRules
				.getValidationPhases()) {
			validate(validationRules, validationPhase, params, form, errors);
		}
	}

	/**
	 * 指定されたフェーズに対する入力検証を実行します。
	 * 
	 * @param validationRules
	 *            入力検証ルールの集合
	 * @param validationPhase
	 *            入力検証のフェーズ
	 * @param errors
	 *            アクションのエラー
	 * @param params
	 *            リクエストパラメータ
	 * @param form
	 *            フォームオブジェクト
	 */
	private void validate(final ValidationRules validationRules,
			final ValidationPhase validationPhase,
			final Map<String, Object[]> params, final Object form,
			final ActionErrors errors) {
		final Collection<ValidationRule> phaseValidationRules = validationRules
				.getPhaseValidationRules(validationPhase);
		for (final ValidationRule validationRule : phaseValidationRules) {
			validationRule.apply(params, form, errors);
		}
		if (!errors.isEmpty()) {
			throw new ValidationException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResult handleValidationException(final ValidationException e,
			final HttpServletRequest request, final ActionContext actionContext) {
		request.setAttribute(ATTR_VALIDATION_FAIL, Boolean.TRUE);
		final ValidationFailBehaviour behaviour = e.getBehaviour();
		return behaviour.getValidationErrorActionResult(actionContext);
	}

}
