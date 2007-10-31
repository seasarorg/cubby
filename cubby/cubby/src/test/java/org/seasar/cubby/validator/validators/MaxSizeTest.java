package org.seasar.cubby.validator.validators;

import java.util.Collections;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.Validator;

public class MaxSizeTest extends TestCase {

	final static Map<String, Object[]> emptyMap = Collections.emptyMap();

	public void testValidation() {
		Validator validator = new MaxSizeValidator(3);
		assertNull(validator.validate(new ValidationContext("field", null,
				emptyMap, null)));
		assertNull(validator.validate(new ValidationContext("field",
				new Object[] { "1", "2" }, emptyMap, null)));
		assertNull(validator.validate(new ValidationContext("field",
				new Object[] { "1", "2", "3" }, emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field",
				new Object[] { "1", "2", "3", "4" }, emptyMap, null)));
	}
}
