package org.seasar.cubby.validator.validators;

import java.util.Collections;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.Validator;

public class RegexpValidatorTest extends TestCase {

	final static Map<String, Object[]> emptyMap = Collections.emptyMap();

	public void testValidation() {
		Validator validator = new RegexpValidator("a.*34");
		assertNull(validator.validate(new ValidationContext("field", null,
				emptyMap, null)));
		assertNull(validator.validate(new ValidationContext("field", "",
				emptyMap, null)));
		assertNull(validator.validate(new ValidationContext("field", "a5634",
				emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field",
				"b5634", emptyMap, null)));
	}
}
