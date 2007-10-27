package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 文字列の長さの範囲を指定して検証します。<p>
 * String#length()メソッドで文字列の長さを求めます。文字列のバイト数でないこと、半角全角も1文字としてカウントされることに注意してください。
 * @author agata
 *
 */
public class RangeLengthValidator extends BaseValidator {

	/**
	 * 最小文字数
	 */
	private final int min;

	/**
	 * 最大文字数
	 */
	private final int max;

	/**
	 * コンストラクタ
	 * @param min 最小文字数
	 * @param max 最大文字数
	 */
	public RangeLengthValidator(final int min, final int max) {
		this(min, max, "valid.rangeLength");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * @param min 最小文字数
	 * @param max 最大文字数
	 * @param messageKey エラーメッセージキー
	 */
	public RangeLengthValidator(final int min, final int max, final String messageKey) {
		this.min = min;
		this.max = max;
		this.setMessageKey(messageKey);
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtil.isEmpty(str)) {
				return null;
			}

			int length = str.length();
			if (length >= min && length <= max) {
				return null;
			}
		}else if(value == null){
			return null;
		}
		return getMessage(getPropertyMessage(ctx.getName()), min, max);
	}

}