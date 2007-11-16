package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 指定した文字列と等しいかどうかを検証します。
 * <p>
 * デフォルトエラーメッセージキー:valid.equals
 * </p>
 * 
 * @author agata
 * @author baba
 */
public class EqualsValidator implements ScalarFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

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
		this.messageHelper = new MessageHelper(messageKey);
	}

	public void validate(final ValidationContext context, final Object value) {
		if (!this.value.equals(value)) {
			context.addMessageInfo(this.messageHelper.createMessageInfo());
		}
	}

}
