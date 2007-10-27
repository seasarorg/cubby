package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 最大文字数を検証します。<p>
 * String#length()メソッドで文字列の長さを求めます。文字列のバイト数でないこと、半角全角も1文字としてカウントされることに注意してください。
 * @author agata
 *
 */
public class MaxLengthValidator extends BaseValidator {

	/**
	 * 最大文字数
	 */
	private final int max;

	/**
	 * コンストラクタ
	 * @param max 最大文字数
	 */
	public MaxLengthValidator(final int max) {
		this(max, "valid.maxLength");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * @param max 最大文字数
	 * @param messageKey エラーメッセージキー
	 */
	public MaxLengthValidator(final int max, final String messageKey) {
		this.max = max;
		this.setMessageKey(messageKey);
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String) value;
			if (StringUtil.isEmpty((String) value)) {
				return null;
			}
			if (str.length() <= max) {
				return null;
			}
		} else if (value == null) {
			return null;
		}
		return getMessage(getPropertyMessage(ctx.getName()), max);
	}
}