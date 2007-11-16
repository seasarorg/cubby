package org.seasar.cubby.validator.validators;

import junit.framework.TestCase;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

public abstract class AbstractScalarFieldValidatorTestCase extends TestCase {

	public static void assertSuccess(ScalarFieldValidator validator,
			Object... values) {
		for (Object value : values) {
			Action action = new MockAction();
			ValidationContext context = new MockValidationContext(action);
			validator.validate(context, value);
			assertTrue("validate " + value
					+ " extected success, but validation failed", action
					.getErrors().isEmpty());
		}
	}

	public static void assertFail(ScalarFieldValidator validator,
			Object... values) {
		for (Object value : values) {
			Action action = new MockAction();
			ValidationContext context = new MockValidationContext(action);
			validator.validate(context, value);
			assertFalse("validate " + value
					+ " extected fail, but validation succeed", action
					.getErrors().isEmpty());
		}
	}

}
