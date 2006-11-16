package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidContext;

public class Required extends BaseValidator {
	public String validate(ValidContext context, Object value) {
		if (value instanceof String) {
			String str = (String)value;
			if (!StringUtils.isEmpty(str)) {
				return null;
			}
		} else if (value != null) {
			return null;
		}
		return getMessage("valid.required", getPropertyMessage(context
				.getName()));
	}
}
