package org.seasar.cubby.validator;

public interface Validator {
	public String validate(ValidContext context, Object value);
}
