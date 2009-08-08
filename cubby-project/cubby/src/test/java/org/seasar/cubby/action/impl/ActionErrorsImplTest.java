/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.action.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.FieldInfo;
import org.seasar.cubby.action.impl.ActionErrorsImpl;

public class ActionErrorsImplTest {

	private ActionErrors actionErrors = new ActionErrorsImpl();

	@Test
	public void testIsEmpty1() {
		assertTrue(actionErrors.isEmpty());
		actionErrors.add("error1");
		assertFalse(actionErrors.isEmpty());
	}

	@Test
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
		assertEquals(1, actionErrors.getIndexedFields().get("field2").get(0)
				.size());
		assertTrue(actionErrors.getIndexedFields().get("field2").get(1)
				.isEmpty());
		assertEquals("error5", actionErrors.getIndexedFields().get("field2")
				.get(0).get(0));
		assertEquals(5, actionErrors.getAll().size());
		assertEquals("error1", actionErrors.getAll().get(0));
		assertEquals("error2", actionErrors.getAll().get(1));
		assertEquals("error3", actionErrors.getAll().get(2));
		assertEquals("error4", actionErrors.getAll().get(3));
		assertEquals("error5", actionErrors.getAll().get(4));

		actionErrors.add("error6", (FieldInfo) null);
		assertEquals("error6", actionErrors.getAll().get(5));
		assertEquals(6, actionErrors.getAll().size());

		actionErrors.clear();
		assertTrue(actionErrors.isEmpty());
	}

}
