package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

public class NumberValidator extends BaseValidator {
	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtils.isEmpty(str)) {
				return null;
			}
			try {
				Long.parseLong(str);
				return null;
			} catch (NumberFormatException e) {}
		}else if(value == null){
			return null;
		}
		return getMessage("valid.number", getPropertyMessage(ctx
				.getName()));
	}
}
