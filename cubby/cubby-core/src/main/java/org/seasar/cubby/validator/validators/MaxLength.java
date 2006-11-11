package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidContext;

public class MaxLength extends BaseValidator {
	private int max;

	public MaxLength(int max) {
		this.max = max;
	}

	public String validate(ValidContext context, Object value) {
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtils.isEmpty((String)value)) {
				return null;
			}
			if (str.length() <= max) {
				return null;
			} else {
				return getMessage("valid.maxLength", getPropertyMessage(context
						.getName()), (Integer) max);
			}
		} else {
			return getMessage("valid.maxLength", getPropertyMessage(context
					.getName()), (Integer) max);
		}
	}
}