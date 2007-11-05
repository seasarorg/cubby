package org.seasar.cubby.validator.validators;

import java.util.Collections;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.Validator;

public class RegexpValidatorTest extends TestCase {

	final static Map<String, Object[]> emptyMap = Collections.emptyMap();

	public void testValidation1() {
		Validator validator = new RegexpValidator("a.*34");
		ValidationContext[] contexts = new ValidationContext[] {
				new ValidationContext("field", new Object[] { null }, emptyMap,
						null),
				new ValidationContext("field", new Object[] { "" }, emptyMap,
						null),
				new ValidationContext("field", new Object[] { "a5634" },
						emptyMap, null) };
		for (ValidationContext context : contexts) {
			validator.validate(context);
			assertFalse(String.valueOf(context.getValues()), context.hasError());
		}
	}

	public void testValidation2() {
		Validator validator = new RegexpValidator("a.*34");
		ValidationContext[] contexts = new ValidationContext[] { new ValidationContext(
				"field", new Object[] { "b5634" }, emptyMap, null) };
		for (ValidationContext context : contexts) {
			validator.validate(context);
			assertTrue(String.valueOf(context.getValues()), context.hasError());
		}
	}
}
