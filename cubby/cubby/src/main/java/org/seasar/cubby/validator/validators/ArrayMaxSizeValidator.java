package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.ArrayFieldValidator;
import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 配列の最大サイズを検証します。
 * <p>
 * デフォルトエラーメッセージキー:valid.arrayMaxSize
 * </p>
 * 
 * @author agata
 * @author baba
 */
public class ArrayMaxSizeValidator implements ArrayFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

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
	public ArrayMaxSizeValidator(final int max) {
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
	public ArrayMaxSizeValidator(final int max, final String messageKey) {
		this.max = max;
		this.messageHelper = new MessageHelper(messageKey);
	}

	public void validate(final ValidationContext context, final Object[] values) {
		if (values == null) {
			return;
		}
		if (values.length > max) {
			context.addMessageInfo(this.messageHelper.createMessageInfo(max));
		}
	}
}