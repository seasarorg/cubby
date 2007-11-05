package org.seasar.cubby.validator.validators;

import java.util.Collections;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidationContext;

public class DateFormatTest extends TestCase {

	final static Map<String, Object[]> emptyMap = Collections.emptyMap();

	public void testValidate1() {
		DateFormatValidator validator = new DateFormatValidator("yyyy-MM-dd");
		ValidationContext context = new ValidationContext("date",
				new Object[] { "2006-01-01" }, emptyMap, null);
		validator.validate(context);
		assertFalse(context.hasError());
	}

	public void testValidate2() {
		DateFormatValidator validator = new DateFormatValidator("yyyy-MM-dd");
		ValidationContext[] contexts = new ValidationContext[] {
				new ValidationContext("date",  new Object[] { "2006-02-29" }, emptyMap, null),
				new ValidationContext("date",  new Object[] { "06-02-29" }, emptyMap, null),
				new ValidationContext("date",  new Object[] { "2006-2-2a" }, emptyMap, null),

		};
		for (ValidationContext context : contexts) {
			validator.validate(context);
			assertTrue(context.hasError());
		}
	}

}
