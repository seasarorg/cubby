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
package org.seasar.cubby.validator;

import java.util.Collection;
import java.util.Map;

import org.seasar.cubby.action.ActionErrors;

/**
 * 入力検証ルールの抽象Kクラスです。
 * 
 * @author baba
 */
public abstract class AbstractValidationRules implements ValidationRules {

	/**
	 * すべてのフェーズに対する入力検証を実行します。
	 * 
	 * @param params
	 *            リクエストパラメータ
	 * @param form
	 *            フォームオブジェクト
	 * @param errors
	 *            アクションのエラー
	 */
	public void validate(final Map<String, Object[]> params, final Object form,
			final ActionErrors errors) {
		for (final ValidationPhase validationPhase : this.getValidationPhases()) {
			validate(validationPhase, params, form, errors);
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
	protected void validate(final ValidationPhase validationPhase,
			final Map<String, Object[]> params, final Object form,
			final ActionErrors errors) {
		final Collection<ValidationRule> phaseValidationRules = this
				.getPhaseValidationRules(validationPhase);
		for (final ValidationRule validationRule : phaseValidationRules) {
			validationRule.apply(params, form, errors);
		}
		if (!errors.isEmpty()) {
			throw new ValidationException();
		}
	}

}
