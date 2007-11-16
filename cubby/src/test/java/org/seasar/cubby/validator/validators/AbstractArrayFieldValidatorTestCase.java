package org.seasar.cubby.validator.validators;

import junit.framework.TestCase;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.validator.ArrayFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

public abstract class AbstractArrayFieldValidatorTestCase extends TestCase {

	public static void assertSuccess(ArrayFieldValidator validator, Object[]... valueses) {
		for (Object[] values : valueses)  {
			Action action = new MockAction();
			ValidationContext context = new MockValidationContext(action);
			action.getErrors().clear();
			validator.validate(context, values);
			assertTrue(action.getErrors().isEmpty());
		}
	}

	public static void assertFail(ArrayFieldValidator validator, Object[]... valueses) {
		for (Object[] values : valueses)  {
			Action action = new MockAction();
			ValidationContext context = new MockValidationContext(action);
			action.getErrors().clear();
			validator.validate(context, values);
			assertFalse(action.getErrors().isEmpty());
		}
	}

}
