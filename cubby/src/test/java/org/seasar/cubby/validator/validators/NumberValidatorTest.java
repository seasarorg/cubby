package org.seasar.cubby.validator.validators;

import java.util.Collections;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.Validator;

public class NumberValidatorTest extends TestCase {

	final static Map<String, Object[]> emptyMap = Collections.emptyMap();

	public void testValidation1() {
		Validator validator = new NumberValidator();
		ValidationContext[] contexts = new ValidationContext[] {
				new ValidationContext("field", new Object[] { "1" }, emptyMap, null),
				new ValidationContext("field", new Object[] { "-1" }, emptyMap, null),
				new ValidationContext("field", new Object[] { "1.1" }, emptyMap, null),
				new ValidationContext("field", new Object[] { "-1.1" }, emptyMap, null) };
		for (ValidationContext context : contexts) {
			validator.validate(context);
			assertFalse(context.hasError());
		}
	}

	public void testValidation2() {
		Validator validator = new NumberValidator();
		ValidationContext[] contexts = new ValidationContext[] {
				new ValidationContext("field", new Object[] { "A" }, emptyMap, null),
				new ValidationContext("field", new Object[] { "123,456" }, emptyMap, null),
				new ValidationContext("field", new Object[] { "1.23.5" }, emptyMap, null) };
		for (ValidationContext context : contexts) {
			validator.validate(context);
			assertTrue(context.hasError());
		}
	}
}
