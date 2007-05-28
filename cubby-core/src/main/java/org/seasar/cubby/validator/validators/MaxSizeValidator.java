package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

public class MaxSizeValidator extends BaseValidator {
	private final int max;

	public MaxSizeValidator(final int max) {
		this.max = max;
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value == null) {
			return null; 
		} 
		int size = CubbyUtils.getObjectSize(value);
		if (size <= max) {
			return null;
		} else {
			return getMessage("valid.maxSize", getPropertyMessage(ctx
					.getName()), (Integer) max);
		}
	}
}