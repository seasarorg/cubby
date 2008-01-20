package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

public class EqualsValidator extends BaseValidator {

	private final String value;

	public EqualsValidator(final String value) {
		this(value, "valid.equals");
	}

	public EqualsValidator(final String value, final String messageKey) {
		this.value = value;
		this.setMessageKey(messageKey);
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (this.value.equals(value)) {
			return null;
		} else {
			return getMessage(getPropertyMessage(ctx.getName()), this.value);
		}
	}

}
