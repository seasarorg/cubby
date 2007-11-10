package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 配列の最小サイズを検証します。
 * <p>
 * デフォルトエラーメッセージキー:valid.arrayMinSize
 * </p>
 * @author agata
 * @author baba
 */
public class ArrayMinSizeValidator extends BaseValidator {

	/**
	 * 配列の最小サイズ
	 */
	private final int min;

	/**
	 * コンストラクタ
	 * 
	 * @param min
	 *            配列の最小サイズ
	 */
	public ArrayMinSizeValidator(final int min) {
		this(min, "valid.arrayMinSize");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param min
	 *            配列の最小サイズ
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public ArrayMinSizeValidator(final int min, final String messageKey) {
		this.min = min;
		this.setMessageKey(messageKey);
	}

	public void validate(ValidationContext context) {
		final Object[] values = context.getValues();
		if (values == null) {
			return;
		}
		if (values.length < min) {
			context.addMessage(getMessage(
					getPropertyMessage(context.getName()), min));
		}
	}
}