package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 必須検証します。<p>
 * 文字列の長さが0の場合、検証エラーとなります。
 * @author agata
 *
 */
public class RequiredValidator extends BaseValidator {

	/**
	 * コンストラクタ
	 */
	public RequiredValidator() {
		this("valid.required");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * @param messageKey エラーメッセージキー
	 */
	public RequiredValidator(final String messageKey) {
		this.setMessageKey(messageKey);
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String)value;
			if (!StringUtil.isEmpty(str)) {
				return null;
			}
		} else if (value != null) {
			return null;
		}
		return getMessage(getPropertyMessage(ctx
				.getName()));
	}

}
