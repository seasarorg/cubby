package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 最大文字数を検証します。
 * <p>
 * String#length()メソッドで文字列の長さを求めます。文字列のバイト数でないこと、半角全角も1文字としてカウントされることに注意してください。
 * </p>
 * <p>
 * デフォルトエラーメッセージキー:valid.maxLength
 * </p>
 * 
 * @author agata
 * @author baba
 * @see String#length()
 */
public class MaxLengthValidator implements ScalarFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * 最大文字数
	 */
	private final int max;

	/**
	 * コンストラクタ
	 * 
	 * @param max
	 *            最大文字数
	 */
	public MaxLengthValidator(final int max) {
		this(max, "valid.maxLength");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param max
	 *            最大文字数
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public MaxLengthValidator(final int max, final String messageKey) {
		this.max = max;
		this.messageHelper = new MessageHelper(messageKey);
	}

	public void validate(final ValidationContext context, final Object value) {
		if (value instanceof String) {
			final String str = (String) value;
			if (StringUtil.isEmpty((String) value)) {
				return;
			}
			if (str.length() <= max) {
				return;
			}
		} else if (value == null) {
			return;
		}
		context.addMessageInfo(this.messageHelper.createMessageInfo(max));
	}
}