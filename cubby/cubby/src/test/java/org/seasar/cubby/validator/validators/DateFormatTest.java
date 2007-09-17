package org.seasar.cubby.validator.validators;

import java.util.Collections;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidationContext;

public class DateFormatTest extends TestCase {

	final static Map<String, Object> emptyMap = Collections.emptyMap();

	public void testValidate() {
		DateFormatValidator dateFormat = new DateFormatValidator("yyyy-MM-dd");
		assertEquals(null, dateFormat.validate(new ValidationContext("date",
				"2006-01-01", emptyMap, null)));
		assertNotSame(null, dateFormat.validate(new ValidationContext("date",
				"2006-02-29", emptyMap, null)));
		assertNotSame(null, dateFormat.validate(new ValidationContext("date",
				"06-02-29", emptyMap, null)));
		assertNotSame(null, dateFormat.validate(new ValidationContext("date",
				"2006-2-2a", emptyMap, null)));
	}
}
