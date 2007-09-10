package org.seasar.cubby.validator.validators;

import java.util.HashMap;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.validators.DateFormatValidator;

import junit.framework.TestCase;

public class DateFormatTest extends TestCase {
	public void testValidate() {
		DateFormatValidator dateFormat = new DateFormatValidator("yyyy-MM-dd");
		assertEquals(null, dateFormat.validate(new ValidationContext("date", "2006-01-01", new HashMap())));
		assertNotSame(null, dateFormat.validate(new ValidationContext("date", "2006-02-29", new HashMap())));	
		assertNotSame(null, dateFormat.validate(new ValidationContext("date", "06-02-29", new HashMap())));	
		assertNotSame(null, dateFormat.validate(new ValidationContext("date", "2006-2-29a", new HashMap())));	
	}
}
