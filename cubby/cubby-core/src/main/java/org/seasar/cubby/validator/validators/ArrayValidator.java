package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.Validator;

public class ArrayValidator implements Validator {

	private final Validator[] validators;

	public ArrayValidator(final Validator... validators) {
		this.validators = validators;
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value == null || !value.getClass().isArray()) {
			return validateAll(ctx);
		}
		Object[] values = (Object[])value;
		for (Object currentValue : values) {
			ValidationContext currentCtx = new ValidationContext(ctx.getName(), currentValue, ctx.getParams());
			String error = validateAll(currentCtx);
			if (error != null) {
				return error;
			}
		}
		return null;
	}

	private String validateAll(final ValidationContext ctx) {
		for (Validator v : validators) {
			String error = v.validate(ctx);
			if (error != null) {
				return error;
			}
		}
		return null;
	}
}
