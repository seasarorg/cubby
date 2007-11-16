package org.seasar.cubby.action.impl;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.FieldInfo;
import org.seasar.extension.unit.S2TestCase;

public class ActionErrorsImplTest extends S2TestCase {

	public ActionErrors actionErrors;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testRequestAttributes() {
		HttpServletRequest request = this.getRequest();
		assertSame(actionErrors, request.getAttribute("errors"));
	}

	public void testIsEmpty1() {
		assertTrue(actionErrors.isEmpty());
		actionErrors.add("error1");
		assertFalse(actionErrors.isEmpty());
	}

	public void testAdd() {
		actionErrors.add("error1");
		assertEquals(1, actionErrors.getOthers().size());
		assertEquals("error1", actionErrors.getOthers().get(0));
		assertEquals(1, actionErrors.getAll().size());
		assertEquals("error1", actionErrors.getAll().get(0));

		actionErrors.add("error2", new FieldInfo("field1"));
		assertFalse(actionErrors.getFields().get("field1").isEmpty());
		assertTrue(actionErrors.getFields().get("field2").isEmpty());
		assertEquals(1, actionErrors.getFields().get("field1").size());
		assertEquals("error2", actionErrors.getFields().get("field1").get(0));
		assertEquals(2, actionErrors.getAll().size());
		assertEquals("error1", actionErrors.getAll().get(0));
		assertEquals("error2", actionErrors.getAll().get(1));

		actionErrors.add("error3");
		assertEquals(2, actionErrors.getOthers().size());
		assertEquals("error1", actionErrors.getOthers().get(0));
		assertEquals("error3", actionErrors.getOthers().get(1));
		assertEquals(3, actionErrors.getAll().size());
		assertEquals("error1", actionErrors.getAll().get(0));
		assertEquals("error2", actionErrors.getAll().get(1));
		assertEquals("error3", actionErrors.getAll().get(2));

		actionErrors.add("error4", new FieldInfo("field1"));
		actionErrors.add("error5", new FieldInfo("field2", 0));
		assertFalse(actionErrors.getFields().get("field1").isEmpty());
		assertFalse(actionErrors.getFields().get("field2").isEmpty());
		assertEquals(2, actionErrors.getFields().get("field1").size());
		assertEquals("error2", actionErrors.getFields().get("field1").get(0));
		assertEquals("error4", actionErrors.getFields().get("field1").get(1));
		assertEquals(1, actionErrors.getFields().get("field2").size());
		assertEquals("error5", actionErrors.getFields().get("field2").get(0));
		assertEquals(1, actionErrors.getIndexedFields().get("field2").get(0).size());
		assertTrue(actionErrors.getIndexedFields().get("field2").get(1).isEmpty());
		assertEquals("error5", actionErrors.getIndexedFields().get("field2").get(0).get(0));
		assertEquals(5, actionErrors.getAll().size());
		assertEquals("error1", actionErrors.getAll().get(0));
		assertEquals("error2", actionErrors.getAll().get(1));
		assertEquals("error3", actionErrors.getAll().get(2));
		assertEquals("error4", actionErrors.getAll().get(3));
		assertEquals("error5", actionErrors.getAll().get(4));
	}
}
