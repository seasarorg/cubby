package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.ValidContext;
import org.seasar.cubby.validator.Validator;

public class ArrayValidator implements Validator {

	private final Validator[] validators;

	public ArrayValidator(Validator... validators) {
		this.validators = validators;
	}

	public String validate(ValidContext context, Object value) {
		if (value == null || !value.getClass().isArray()) {
			return validateAll(context, value);
		}
		Object[] values = (Object[])value;
		for (Object currentValue : values) {
			String error = validateAll(context, currentValue);
			if (error != null) {
				return error;
			}
		}
		return null;
	}

	private String validateAll(ValidContext context, Object value) {
		for (Validator v : validators) {
			String error = v.validate(context, value);
			if (error != null) {
				return error;
			}
		}
		return null;
	}
}
