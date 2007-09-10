package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

public class NumberValidator extends BaseValidator {
	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtil.isEmpty(str)) {
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
