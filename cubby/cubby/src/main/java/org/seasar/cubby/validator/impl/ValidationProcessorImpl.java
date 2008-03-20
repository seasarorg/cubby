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
package org.seasar.cubby.validator.impl;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationPhase;
import org.seasar.cubby.validator.ValidationProcessor;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

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
			ActionContext context, ActionErrors errors) {
		final Validation validation = this.getValidation(context);
		if (validation != null) {
			final Map<String, Object[]> params = CubbyUtils.getAttribute(
					request, ATTR_PARAMS);
			final ValidationRules validationRules = this
					.getValidationRules(context);
			final Object form = context.getFormBean();
			validate(validationRules, params, form, errors);
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
	public void validate(final ValidationRules validationRules,
			final Map<String, Object[]> params, final Object form,
			final ActionErrors errors) {
		for (ValidationPhase validationPhase : validationRules
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
	public void validate(final ValidationRules validationRules,
			final ValidationPhase validationPhase,
			final Map<String, Object[]> params, final Object form,
			final ActionErrors errors) {
		final Collection<ValidationRule> phaseValidationRules = validationRules
				.getPhaseValidationRules(validationPhase);
		if (validationRules != null) {
			for (final ValidationRule validationRule : phaseValidationRules) {
				validationRule.apply(params, form, errors);
			}
			if (!errors.isEmpty()) {
				throw new ValidationException();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResult handleValidationException(final ValidationException e,
			final HttpServletRequest request, final ActionContext context,
			final ActionErrors errors) {
		if (e.hasMessage()) {
			errors.add(e.getMessage(), e.getFieldNames());
		}
		request.setAttribute(ATTR_VALIDATION_FAIL, Boolean.TRUE);

		final String errorPage;
		final Validation validation = this.getValidation(context);
		if (validation == null) {
			errorPage = null;
		} else {
			errorPage = validation.errorPage();
		}

		final ValidationRules validationRules = this
				.getValidationRules(context);
		return validationRules.fail(errorPage);
	}

	/**
	 * コンテキストに設定されたアクションの入力検証の定義を取得します。
	 * 
	 * @param context
	 *            アクションメソッド実行時のコンテキスト
	 * @return アクションの入力検証の定義
	 */
	private Validation getValidation(final ActionContext context) {
		return context.getMethod().getAnnotation(Validation.class);
	}

	/**
	 * コンテキストに設定されたアクションの入力検証ルールの集合を取得します。
	 * 
	 * @param context
	 *            アクションメソッド実行時のコンテキスト
	 * @return アクションメソッドの入力検証ルールの集合
	 */
	private ValidationRules getValidationRules(final ActionContext context) {
		final Validation validation = getValidation(context);
		final Action action = context.getAction();
		final BeanDesc beanDesc = BeanDescFactory
				.getBeanDesc(action.getClass());
		final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(validation
				.rules());
		final ValidationRules rules = (ValidationRules) propertyDesc
				.getValue(action);
		return rules;
	}

}
