package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

public class EqualsValidator extends BaseValidator {

	private final String value;

	public EqualsValidator(String value) {
		this.value = value;
	}
	
	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (this.value.equals(value)) {
			return null;
		} else {
			return getMessage("valid.equals", getPropertyMessage(ctx.getName()), this.value);
		}
	}

}
