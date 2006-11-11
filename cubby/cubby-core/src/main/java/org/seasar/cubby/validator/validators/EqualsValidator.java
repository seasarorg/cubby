package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidContext;

public class EqualsValidator extends BaseValidator {

	private final String value;

	public EqualsValidator(String value) {
		this.value = value;
	}
	
	public String validate(ValidContext context, Object value) {
		if (this.value.equals(value)) {
			return null;
		} else {
			return getMessage("valid.equals", getPropertyMessage(context.getName()), this.value);
		}
	}

}
