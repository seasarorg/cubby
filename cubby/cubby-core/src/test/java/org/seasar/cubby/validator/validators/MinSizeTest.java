package org.seasar.cubby.validator.validators;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.Validator;

public class MinSizeTest extends TestCase {

	public void testValidation() {
		Validator validator = new MinSizeValidator(3);
		assertNull(validator.validate(new ValidationContext("field", null)));
		assertNotNull(validator.validate(new ValidationContext("field", new Object[]{"1","2"})));
		assertNull(validator.validate(new ValidationContext("field", new Object[]{"1","2","3"})));
		assertNull(validator.validate(new ValidationContext("field", new Object[]{"1","2","3","4"})));
	}
}
