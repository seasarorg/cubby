package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidContext;

public class RangeLengthValidator extends BaseValidator {
	private int min;
	private int max;

	public RangeLengthValidator(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public String validate(ValidContext context, Object value) {
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtils.isEmpty(str)) {
				return null;
			}

			int length = str.length();
			if (length >= min && length <= max) {
				return null;
			}
		}
		return getMessage("valid.rangeLength", getPropertyMessage(context.getName()), min, max);
	}
}