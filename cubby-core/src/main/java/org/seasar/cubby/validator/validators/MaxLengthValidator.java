package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

public class MaxLengthValidator extends BaseValidator {
	private final int max;

	public MaxLengthValidator(final int max) {
		this.max = max;
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtils.isEmpty((String)value)) {
				return null;
			}
			if (str.length() <= max) {
				return null;
			} else {
				return getMessage("valid.maxLength", getPropertyMessage(ctx
						.getName()), (Integer) max);
			}
		} else if(value == null){
			return null;
		} else {
			return getMessage("valid.maxLength", getPropertyMessage(ctx
					.getName()), (Integer) max);
		}
	}
}