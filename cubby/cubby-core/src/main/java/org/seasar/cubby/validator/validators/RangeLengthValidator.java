package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

public class RangeLengthValidator extends BaseValidator {
	private final int min;
	private final int max;

	public RangeLengthValidator(final int min, final int max) {
		this.min = min;
		this.max = max;
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtils.isEmpty(str)) {
				return null;
			}

			int length = str.length();
			if (length >= min && length <= max) {
				return null;
			}
		}else if(value == null){
			return null;
		}
		return getMessage("valid.rangeLength", getPropertyMessage(ctx.getName()), min, max);
	}
}