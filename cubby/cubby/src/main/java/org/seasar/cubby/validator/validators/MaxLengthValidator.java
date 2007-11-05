package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseScalarValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 最大文字数を検証します。
 * <p>
 * String#length()メソッドで文字列の長さを求めます。文字列のバイト数でないこと、半角全角も1文字としてカウントされることに注意してください。
 * </p>
 * 
 * @author agata
 * @author baba
 * @see String#length()
 */
public class MaxLengthValidator extends BaseScalarValidator {

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
		this.setMessageKey(messageKey);
	}

	@Override
	protected void validate(final Object value, final ValidationContext context) {
		if (value instanceof String) {
			String str = (String) value;
			if (StringUtil.isEmpty((String) value)) {
				return;
			}
			if (str.length() <= max) {
				return;
			}
		} else if (value == null) {
			return;
		}
		context.addMessage(getMessage(getPropertyMessage(context.getName()),
				max));
	}
}