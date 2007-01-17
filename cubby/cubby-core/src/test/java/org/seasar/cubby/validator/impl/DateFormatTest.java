package org.seasar.cubby.validator.impl;

import java.util.HashMap;

import org.seasar.cubby.validator.ValidContext;
import org.seasar.cubby.validator.validators.DateFormatValidator;

import junit.framework.TestCase;

public class DateFormatTest extends TestCase {

	
	public void testValidate() {
		DateFormatValidator dateFormat = new DateFormatValidator("yyyy-MM-dd");
		ValidContext context = new ValidContext("date", new HashMap());
		assertEquals(null, dateFormat.validate(context, "2006-01-01"));
		assertNotSame(null, dateFormat.validate(context, "2006-02-29"));
		
	}
	
}
