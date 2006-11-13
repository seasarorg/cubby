package org.seasar.cubby.controller.impl;

import junit.framework.TestCase;

public class ActionErrorsImplTest extends TestCase {

	ActionErrorsImpl errors;

	@Override
	protected void setUp() throws Exception {
		errors = new ActionErrorsImpl();
	}

	public void testActionError() throws Exception {
		assertEquals(true, errors.isEmpty());
		errors.addActionError("message1");
		assertEquals(false, errors.isEmpty());
		assertEquals(1, errors.getActionErrors().size());
		assertEquals(0, errors.getFieldErrors().size());
		assertEquals(1, errors.getAllErrors().size());
		assertEquals("message1", errors.getActionErrors().get(0));
	}

	public void testFieldError() throws Exception {
		assertEquals(true, errors.isEmpty());
		errors.addFieldError("name", "name is error.");
		assertEquals(false, errors.isEmpty());
		errors.addFieldError("age", "age is error.");
		assertEquals(0, errors.getActionErrors().size());
		assertEquals(2, errors.getFieldErrors().size());
		assertEquals(2, errors.getAllErrors().size());
		assertEquals("name is error.", errors.getFieldErrors().get("name").get(
				0));
		assertEquals("age is error.", errors.getFieldErrors().get("age").get(0));
	}

}
