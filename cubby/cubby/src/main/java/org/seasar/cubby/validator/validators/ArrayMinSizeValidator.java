package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.ArrayFieldValidator;
import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 配列の最小サイズを検証します。
 * <p>
 * デフォルトエラーメッセージキー:valid.arrayMinSize
 * </p>
 * 
 * @author agata
 * @author baba
 */
public class ArrayMinSizeValidator implements ArrayFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

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
		this(min, "valid.minSize");
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
		this.messageHelper = new MessageHelper(messageKey);
	}

	public void validate(final ValidationContext context, final Object[] values) {
		if (values == null) {
			return;
		}
		if (values.length < min) {
			context.addMessageInfo(this.messageHelper.createMessageInfo(min));
		}
	}
}