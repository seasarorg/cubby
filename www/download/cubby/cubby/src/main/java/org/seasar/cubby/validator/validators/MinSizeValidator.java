package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

public class MinSizeValidator extends BaseValidator {

	private final int min;

	public MinSizeValidator(final int min) {
		this(min, "valid.minSize");
	}

	public MinSizeValidator(final int min, final String messageKey) {
		this.min = min;
		this.setMessageKey(messageKey);
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value == null) {
			return null; 
		} 
		int size = CubbyUtils.getObjectSize(value);
		if (size >= min) {
			return null;
		} else {
			return getMessage(getPropertyMessage(ctx.getName()), min);
		}
	}
}