package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidContext;

public class Number extends BaseValidator {
	public String validate(ValidContext context, Object value) {
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtils.isEmpty(str)) {
				return null;
			}
			try {
				Long.parseLong(str);
				return null;
			} catch (NumberFormatException e) {}
		}
		return getMessage("valid.number", getPropertyMessage(context
				.getName()));
	}
}
