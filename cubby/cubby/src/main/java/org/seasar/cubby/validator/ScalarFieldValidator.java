package org.seasar.cubby.validator;

public interface ScalarFieldValidator extends Validator {

	void validate(ValidationContext context, Object value);

}
