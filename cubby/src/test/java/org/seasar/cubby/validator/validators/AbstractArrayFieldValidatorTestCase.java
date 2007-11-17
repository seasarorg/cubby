package org.seasar.cubby.validator.validators;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ArrayFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

public abstract class AbstractArrayFieldValidatorTestCase extends TestCase {

	public static void assertSuccess(ArrayFieldValidator validator, Object[]... valueses) {
		for (Object[] values : valueses)  {
			ValidationContext context = new ValidationContext();
			validator.validate(context, values);
			assertTrue(context.getMessageInfos().isEmpty());
		}
	}

	public static void assertFail(ArrayFieldValidator validator, Object[]... valueses) {
		for (Object[] values : valueses)  {
			ValidationContext context = new ValidationContext();
			validator.validate(context, values);
			assertFalse(context.getMessageInfos().isEmpty());
		}
	}

}
