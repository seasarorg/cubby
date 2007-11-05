package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 必須検証します。
 * <p>
 * 文字列の長さが0の場合、検証エラーとなります。
 * 
 * @author agata
 * @author baba
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
	 * 
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public RequiredValidator(final String messageKey) {
		this.setMessageKey(messageKey);
	}

	public void validate(final ValidationContext context) {
		final Object[] values = context.getValues();
		if (values == null || values.length == 0) {
			context
					.addMessage(getMessage(getPropertyMessage(context.getName())));
		} else {
			for (final Object value : values) {
				validate(value, context);
			}
		}
	}

	private void validate(final Object value, final ValidationContext context) {
		if (value instanceof String) {
			final String str = (String) value;
			if (!StringUtil.isEmpty(str)) {
				return;
			}
		} else if (value != null) {
			return;
		}
		context.addMessage(getMessage(getPropertyMessage(context.getName())));
	}

}
