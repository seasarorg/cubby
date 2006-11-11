package org.seasar.cubby.validator.impl;

import org.seasar.cubby.validator.ValidContext;
import org.seasar.cubby.validator.validators.DateFormat;

import junit.framework.TestCase;

public class DateFormatTest extends TestCase {

	
	public void testValidate() {
		DateFormat dateFormat = new DateFormat("yyyy-MM-dd");
		ValidContext context = new ValidContext();
		context.setName("date");
		assertEquals(null, dateFormat.validate(context, "2006-01-01"));
		assertNotSame(null, dateFormat.validate(context, "2006-02-29"));
		
	}
	
}
