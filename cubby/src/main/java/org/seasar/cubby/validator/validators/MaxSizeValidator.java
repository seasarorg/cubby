package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 配列の最大サイズを検証します。
 * 
 * @author agata
 * @author baba
 */
public class MaxSizeValidator extends BaseValidator {

	/**
	 * 配列の最大サイズ
	 */
	private final int max;

	/**
	 * コンストラクタ
	 * 
	 * @param max
	 *            配列の最大サイズ
	 */
	public MaxSizeValidator(final int max) {
		this(max, "valid.maxSize");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param max
	 *            配列の最大サイズ
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public MaxSizeValidator(final int max, final String messageKey) {
		this.max = max;
		this.setMessageKey(messageKey);
	}

	public void validate(ValidationContext context) {
		final Object[] values = context.getValues();
		if (values == null) {
			return;
		}
		if (values.length > max) {
			context.addMessage(getMessage(
					getPropertyMessage(context.getName()), max));
		}
	}
}