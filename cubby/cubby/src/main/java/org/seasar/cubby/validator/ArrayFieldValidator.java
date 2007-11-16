package org.seasar.cubby.validator;

public interface ArrayFieldValidator extends Validator {

	void validate(ValidationContext context, Object[] values);

}
