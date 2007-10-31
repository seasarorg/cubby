package org.seasar.cubby.validator.validators;

import java.math.BigDecimal;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 数値かどうかを検証します。<p>
 * 数値かどうかの検証は「new BigDecimal(str);」で行っています。
 * @author agata
 * @see BigDecimal
 */
public class NumberValidator extends BaseValidator {

	/**
	 * コンストラクタ
	 */
	public NumberValidator() {
		this("valid.number");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * @param messageKey エラーメッセージキー
	 */
	public NumberValidator(final String messageKey) {
		this.setMessageKey(messageKey);
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtil.isEmpty(str)) {
				return null;
			}
			try {
				new BigDecimal(str);
				return null;
			} catch (NumberFormatException e) {}
		}else if(value == null){
			return null;
		}
		return getMessage(getPropertyMessage(ctx.getName()));
	}
}
