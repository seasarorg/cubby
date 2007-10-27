package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 指定した文字列と等しいかどうかを検証します。
 * @author agata
 *
 */
public class EqualsValidator extends BaseValidator {

	/**
	 * 対象文字列
	 */
	private final String value;

	/**
	 * コンストラクタ
	 * @param value 対象文字列
	 */
	public EqualsValidator(final String value) {
		this(value, "valid.equals");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * @param value
	 * @param messageKey
	 */
	public EqualsValidator(final String value, final String messageKey) {
		this.value = value;
		this.setMessageKey(messageKey);
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (this.value.equals(value)) {
			return null;
		} else {
			return getMessage(getPropertyMessage(ctx.getName()), this.value);
		}
	}

}
