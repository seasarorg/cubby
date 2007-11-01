package org.seasar.cubby.validator.validators;

import java.util.Collections;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.Validator;

public class NumberValidatorTest extends TestCase {

	final static Map<String, Object[]> emptyMap = Collections.emptyMap();

	public void testValidation() {
		Validator validator = new NumberValidator();
		
		assertNull(validator.validate(new ValidationContext("field",
				"1", emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field",
				"A", emptyMap, null)));
		assertNull(validator.validate(new ValidationContext("field",
				"-1", emptyMap, null)));
		assertNull(validator.validate(new ValidationContext("field",
				"1.1", emptyMap, null)));
		assertNull(validator.validate(new ValidationContext("field",
				"-1.1", emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field",
				"123,456", emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field",
				"1.23.5", emptyMap, null)));
	}
}