package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 配列の最大サイズを検証します。
 * @author agata
 *
 */
public class MaxSizeValidator extends BaseValidator {

	/**
	 * 配列の最大サイズ
	 */
	private final int max;

	/**
	 * コンストラクタ
	 * @param max 配列の最大サイズ
	 */
	public MaxSizeValidator(final int max) {
		this(max, "valid.maxSize");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * @param max 配列の最大サイズ
	 * @param messageKey エラーメッセージキー
	 */
	public MaxSizeValidator(final int max, final String messageKey) {
		this.max = max;
		this.setMessageKey(messageKey);
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value == null) {
			return null; 
		} 
		int size = CubbyUtils.getObjectSize(value);
		if (size <= max) {
			return null;
		} else {
			return getMessage(getPropertyMessage(ctx.getName()), max);
		}
	}
}