package org.seasar.cubby.validator.validators;

import java.util.HashMap;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidContext;
import org.seasar.cubby.validator.Validator;

public class RegexpValidatorTest extends TestCase {

	public void testValidation() {
		Validator validator = new RegexpValidator("a.*34");

		ValidContext context = new ValidContext("field", new HashMap());

		assertNull(validator.validate(context, "a5634"));
		assertNotNull(validator.validate(context, "b5634"));
	}
}
