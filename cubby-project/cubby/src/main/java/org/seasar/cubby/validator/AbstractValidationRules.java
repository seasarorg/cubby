package org.seasar.cubby.validator;

import java.util.Collection;
import java.util.Map;

import org.seasar.cubby.action.ActionErrors;

public abstract class AbstractValidationRules implements ValidationRules {

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
