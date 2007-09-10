package org.seasar.cubby.validator.validators;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.Validator;

public class RegexpValidatorTest extends TestCase {

	public void testValidation() {
		Validator validator = new RegexpValidator("a.*34");
		assertNull(validator.validate(new ValidationContext("field", null)));
		assertNull(validator.validate(new ValidationContext("field", "")));
		assertNull(validator.validate(new ValidationContext("field", "a5634")));
		assertNotNull(validator.validate(new ValidationContext("field", "b5634")));
	}
}
