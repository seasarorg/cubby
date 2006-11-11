package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidContext;

public class RangeValidator extends BaseValidator {
	private long min;
	private long max;

	public RangeValidator(long min, long max) {
		this.min = min;
		this.max = max;
	}

	public String validate(ValidContext context, Object value) {
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtils.isEmpty(str)) {
				return null;
			}
			try {
				long longValue = Long.parseLong(str);
				if (longValue >= min && longValue <= max) {
					return null;
				}
			} catch (NumberFormatException e) {}
		}
		return getMessage("valid.range", getPropertyMessage(context
				.getName()), (Long) min, (Long) max);
	}
}