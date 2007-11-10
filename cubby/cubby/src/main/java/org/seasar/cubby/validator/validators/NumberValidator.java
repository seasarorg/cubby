package org.seasar.cubby.validator.validators;

import java.math.BigDecimal;

import org.seasar.cubby.validator.BaseScalarValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 数値かどうかを検証します。
 * <p>
 * 数値かどうかの検証は {@link BigDecimal#BigDecimal(String)} で行っています。
 * <p>
 * デフォルトエラーメッセージキー:valid.number
 * </p>
 * @author agata
 * @author baba
 * @see BigDecimal#BigDecimal(String)
 */
public class NumberValidator extends BaseScalarValidator {

	/**
	 * コンストラクタ
	 */
	public NumberValidator() {
		this("valid.number");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public NumberValidator(final String messageKey) {
		this.setMessageKey(messageKey);
	}

	@Override
	protected void validate(final Object value, final ValidationContext context) {
		if (value instanceof String) {
			final String str = (String) value;
			if (StringUtil.isEmpty(str)) {
				return;
			}
			try {
				new BigDecimal(str);
				return;
			} catch (final NumberFormatException e) {
			}
		} else if (value == null) {
			return;
		}
		context.addMessage(getMessage(getPropertyMessage(context.getName())));
	}

}
