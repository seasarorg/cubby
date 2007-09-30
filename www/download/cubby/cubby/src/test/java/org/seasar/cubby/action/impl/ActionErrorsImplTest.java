package org.seasar.cubby.action.impl;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.extension.unit.S2TestCase;

public class ActionErrorsImplTest extends S2TestCase {

	private ActionErrors actionErrors;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testRequestAttributes() {
		HttpServletRequest request = this.getRequest();
		assertSame(actionErrors, request.getAttribute("errors"));
		assertSame(actionErrors.getActionErrors(), request.getAttribute("actionErrors"));
		assertSame(actionErrors.getFieldErrors(), request.getAttribute("fieldErrors"));
		assertSame(actionErrors.getAllErrors(), request.getAttribute("allErrors"));
	}

	public void testIsEmpty1() {
		assertTrue(actionErrors.isEmpty());
		actionErrors.addActionError("error1");
		assertFalse(actionErrors.isEmpty());
	}

	public void testIsEmpty2() {
		assertTrue(actionErrors.isEmpty());
		actionErrors.addFieldError("field1", "error1");
		assertFalse(actionErrors.isEmpty());
	}

	public void testAdd() {
		actionErrors.addActionError("error1");
		assertEquals(1, actionErrors.getActionErrors().size());
		assertEquals("error1", actionErrors.getActionErrors().get(0));
		assertEquals(1, actionErrors.getAllErrors().size());
		assertEquals("error1", actionErrors.getAllErrors().get(0));

		actionErrors.addFieldError("field1", "error2");
		assertTrue(actionErrors.hasFieldError("field1"));
		assertFalse(actionErrors.hasFieldError("field2"));
		assertEquals(1, actionErrors.getFieldErrors().get("field1").size());
		assertEquals("error2", actionErrors.getFieldErrors().get("field1").get(0));
		assertEquals(2, actionErrors.getAllErrors().size());
		assertEquals("error1", actionErrors.getAllErrors().get(0));
		assertEquals("error2", actionErrors.getAllErrors().get(1));

		actionErrors.addActionError("error3");
		assertEquals(2, actionErrors.getActionErrors().size());
		assertEquals("error1", actionErrors.getActionErrors().get(0));
		assertEquals("error3", actionErrors.getActionErrors().get(1));
		assertEquals(3, actionErrors.getAllErrors().size());
		assertEquals("error1", actionErrors.getAllErrors().get(0));
		assertEquals("error2", actionErrors.getAllErrors().get(1));
		assertEquals("error3", actionErrors.getAllErrors().get(2));

		actionErrors.addFieldError("field1", "error4");
		actionErrors.addFieldError("field2", "error5");
		assertTrue(actionErrors.hasFieldError("field1"));
		assertTrue(actionErrors.hasFieldError("field2"));
		assertEquals(2, actionErrors.getFieldErrors().get("field1").size());
		assertEquals("error2", actionErrors.getFieldErrors().get("field1").get(0));
		assertEquals("error4", actionErrors.getFieldErrors().get("field1").get(1));
		assertEquals(1, actionErrors.getFieldErrors().get("field2").size());
		assertEquals("error5", actionErrors.getFieldErrors().get("field2").get(0));
		assertEquals(5, actionErrors.getAllErrors().size());
		assertEquals("error1", actionErrors.getAllErrors().get(0));
		assertEquals("error2", actionErrors.getAllErrors().get(1));
		assertEquals("error3", actionErrors.getAllErrors().get(2));
		assertEquals("error4", actionErrors.getAllErrors().get(3));
		assertEquals("error5", actionErrors.getAllErrors().get(4));
	}
}
