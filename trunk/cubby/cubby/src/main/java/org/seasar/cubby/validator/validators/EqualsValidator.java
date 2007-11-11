package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseScalarValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 指定した文字列と等しいかどうかを検証します。
 * <p>
 * デフォルトエラーメッセージキー:valid.equals
 * </p>
 * @author agata
 * @author baba
 */
public class EqualsValidator extends BaseScalarValidator {

	/**
	 * 対象文字列
	 */
	private final String value;

	/**
	 * コンストラクタ
	 * 
	 * @param value
	 *            対象文字列
	 */
	public EqualsValidator(final String value) {
		this(value, "valid.equals");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param value
	 * @param messageKey
	 */
	public EqualsValidator(final String value, final String messageKey) {
		this.value = value;
		this.setMessageKey(messageKey);
	}

	@Override
	protected void validate(final Object value, final ValidationContext ctx) {
		if (!this.value.equals(value)) {
			ctx.addMessage(getMessage(getPropertyMessage(ctx.getName()),
					this.value));
		}
	}

}
