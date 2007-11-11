package org.seasar.cubby.validator.validators;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.Validator;

public class EmailValidatorTest extends TestCase {

	final static Map<String, Object[]> emptyMap = Collections.emptyMap();

	public void testValidation1() {
		Validator validator = new EmailValidator();
		ValidationContext[] contexts = new ValidationContext[] {
				new ValidationContext("field", new Object[] { null }, emptyMap,
						null),
				new ValidationContext("field", new Object[] { "" }, emptyMap,
						null),
				new ValidationContext("field",
						new Object[] { "testuser@test.jp" }, emptyMap, null),
				new ValidationContext("field",
						new Object[] { "testuser@192.168.192.168" }, emptyMap,
						null),
				new ValidationContext("field",
						new Object[] { "testuser@192.168.192" }, emptyMap, null) };
		for (ValidationContext context : contexts) {
			validator.validate(context);
			assertFalse(Arrays.deepToString(context.getValues()), context.hasError());
		}
	}

	public void testValidation6() {
		Validator validator = new EmailValidator();
		ValidationContext[] contexts = new ValidationContext[] {
				new ValidationContext("field", new Object[] { "testuser" },
						emptyMap, null),
				new ValidationContext("field", new Object[] { "testuser@" },
						emptyMap, null),
				new ValidationContext("field",
						new Object[] { "testuser@test" }, emptyMap, null),
				new ValidationContext("field",
						new Object[] { "testuser@test." }, emptyMap, null),
				new ValidationContext("field",
						new Object[] { "testuser@192.168.192.256" }, emptyMap,
						null),
				new ValidationContext("field", new Object[] { "ｔｅｓｔ@ｔｅｓｔ.ｊｐ" },
						emptyMap, null),
				new ValidationContext("field", new Object[] { "testuser@jp" },
						emptyMap, null),
				new ValidationContext("field",
						new Object[] { "testuser@test.a" }, emptyMap, null),
				new ValidationContext("field",
						new Object[] { "testuser@test.aaaaa" }, emptyMap, null),
				new ValidationContext("field",
						new Object[] { "test\\user@test.jp" }, emptyMap, null),
				new ValidationContext("field",
						new Object[] { "test[u]ser@test.jp" }, emptyMap, null) };
		for (ValidationContext context : contexts) {
			validator.validate(context);
			assertTrue(Arrays.deepToString(context.getValues()), context.hasError());
		}
	}
}
