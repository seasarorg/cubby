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

	/** リクエスト。 */
	private HttpServletRequest request;

	/** アクションメソッドの実行時コンテキスト。 */
	private ActionContext context;

	/** アクションで発生したエラー。 */
	private ActionErrors errors;

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
	 * アクションメソッド実行時のコンテキストを設定します。
	 * 
	 * @param context
	 *            アクションメソッド実行時のコンテキスト
	 */
	public void setActionContext(final ActionContext context) {
		this.context = context;
	}

	/**
	 * アクションで発生したエラーを設定します。
	 * 
	 * @param errors
	 *            アクションで発生したエラー。
	 */
	public void setActionErrors(final ActionErrors errors) {
		this.errors = errors;
	}

	/**
	 * {@inheritDoc}
	 */
	public void process() {
		final Validation validation = this.getValidation();
		if (validation != null) {
			final Map<String, Object[]> params = CubbyUtils.getAttribute(
					request, ATTR_PARAMS);
			final ValidationRules validationRules = this.getValidationRules();
			final Object form = context.getFormBean();
			validate(validationRules, params, form);
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
	 */
	public void validate(final ValidationRules validationRules,
			final Map<String, Object[]> params, final Object form) {
		for (ValidationPhase validationPhase : validationRules
				.getValidationPhases()) {
			validate(validationRules, validationPhase, params, form);
		}
	}

	/**
	 * 指定されたフェーズに対する入力検証を実行します。
	 * 
	 * @param validationRules
	 *            入力検証ルールの集合
	 * @param validationPhase
	 *            入力検証のフェーズ
	 * @param params
	 *            リクエストパラメータ
	 * @param form
	 *            フォームオブジェクト
	 */
	public void validate(final ValidationRules validationRules,
			ValidationPhase validationPhase,
			final Map<String, Object[]> params, final Object form) {
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
	public ActionResult handleValidationException(final ValidationException e) {
		if (e.hasMessage()) {
			errors.add(e.getMessage(), e.getFieldNames());
		}
		request.setAttribute(ATTR_VALIDATION_FAIL, Boolean.TRUE);

		final String errorPage;
		final Validation validation = this.getValidation();
		if (validation == null) {
			errorPage = null;
		} else {
			errorPage = validation.errorPage();
		}

		final ValidationRules validationRules = this.getValidationRules();
		return validationRules.fail(errorPage);
	}

	/**
	 * コンテキストに設定されたアクションの入力検証の定義を取得します。
	 * 
	 * @param context
	 *            アクションメソッド実行時のコンテキスト
	 * @return アクションの入力検証の定義
	 */
	private Validation getValidation() {
		return context.getMethod().getAnnotation(Validation.class);
	}

	/**
	 * コンテキストに設定されたアクションの入力検証ルールの集合を取得します。
	 * 
	 * @param context
	 *            アクションメソッド実行時のコンテキスト
	 * @return アクションメソッドの入力検証ルールの集合
	 */
	private ValidationRules getValidationRules() {
		final Validation validation = getValidation();
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
