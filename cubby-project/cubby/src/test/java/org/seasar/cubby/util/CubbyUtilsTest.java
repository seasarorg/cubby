/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.cubby.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.seasar.cubby.action.Path;
import org.seasar.cubby.exception.ActionRuntimeException;
import org.seasar.framework.util.ClassUtil;

public class CubbyUtilsTest extends TestCase {

	public void testGetObjectSize() {
		// array
		assertEquals(0, CubbyUtils.getObjectSize(null));
		assertEquals(1, CubbyUtils.getObjectSize(""));
		assertEquals(0, CubbyUtils.getObjectSize(new Object[] {}));
		assertEquals(3, CubbyUtils.getObjectSize(new Object[] { 1, 2, 3 }));
		assertEquals(3, CubbyUtils.getObjectSize(new Object[] { null, 2, 3 }));

		// collection
		assertEquals(0, CubbyUtils.getObjectSize(toCollection(new Object[] {})));
		assertEquals(3, CubbyUtils.getObjectSize(toCollection(new Object[] { 1,
				2, 3 })));
		assertEquals(3, CubbyUtils.getObjectSize(toCollection(new Object[] {
				null, 2, 3 })));

	}

	@SuppressWarnings("unchecked")
	private Collection toCollection(Object[] objects) {
		List<Object> list = new ArrayList<Object>();
		for (Object o : objects) {
			list.add(o);
		}
		return list;
	}

	public void testGetActionPath() {
		assertEquals("/hoge/m1", CubbyUtils.getActionPath(Hoge1Action.class,
				ClassUtil.getMethod(Hoge1Action.class, "m1", null)));
		assertEquals("/hoge/m/m2", CubbyUtils.getActionPath(Hoge1Action.class,
				ClassUtil.getMethod(Hoge1Action.class, "m2", null)));
		assertEquals("/hoge/", CubbyUtils.getActionPath(Hoge1Action.class,
				ClassUtil.getMethod(Hoge1Action.class, "index", null)));
		assertEquals("/hoge/index2", CubbyUtils.getActionPath(
				Hoge1Action.class, ClassUtil.getMethod(Hoge1Action.class,
						"index2", null)));
		assertEquals("/hoge2/m1", CubbyUtils.getActionPath(Hoge2Action.class,
				ClassUtil.getMethod(Hoge2Action.class, "m1", null)));
		assertEquals("/hoge/m2", CubbyUtils.getActionPath(Hoge2Action.class,
				ClassUtil.getMethod(Hoge2Action.class, "m2", null)));
		assertEquals("/", CubbyUtils.getActionPath(MockAction.class, ClassUtil
				.getMethod(MockAction.class, "index", null)));
		assertEquals("/dummy1", CubbyUtils.getActionPath(MockAction.class,
				ClassUtil.getMethod(MockAction.class, "dummy1", null)));
		assertEquals("/dummy2", CubbyUtils.getActionPath(MockAction.class,
				ClassUtil.getMethod(MockAction.class, "dummy2", null)));
		assertEquals("/todo/lists", CubbyUtils.getActionPath(MockAction.class,
				ClassUtil.getMethod(MockAction.class, "todolist", null)));
		assertEquals("/tasklists", CubbyUtils.getActionPath(MockAction.class,
				ClassUtil.getMethod(MockAction.class, "tasklist", null)));
	}

	public void testGetActionClassName() {
		assertEquals("hoge", CubbyUtils.getActionDirectory(Hoge1Action.class));
		assertEquals("hoge2", CubbyUtils.getActionDirectory(Hoge2Action.class));
	}

	public void testReplaceFirst() {
		assertNull(CubbyUtils.replaceFirst(null, "", ""));
		assertEquals("abaa", CubbyUtils.replaceFirst("abaa", null, null));
		assertEquals("abaa", CubbyUtils.replaceFirst("abaa", "a", null));
		assertEquals("baa", CubbyUtils.replaceFirst("abaa", "a", ""));
		assertEquals("zbaa", CubbyUtils.replaceFirst("abaa", "a", "z"));
		assertEquals("xyzaab", CubbyUtils.replaceFirst("abaab", "ab", "xyz"));
		assertNull(CubbyUtils.replaceFirst(null, "", ""));
		assertEquals("3abaa", CubbyUtils.replaceFirst("3abaa", null, null));
		assertEquals("3abaa", CubbyUtils.replaceFirst("3abaa", "a", null));
		assertEquals("3baa", CubbyUtils.replaceFirst("3abaa", "a", ""));
		assertEquals("3zbaa", CubbyUtils.replaceFirst("3abaa", "a", "z"));
		assertEquals("3xyzaab", CubbyUtils.replaceFirst("3abaab", "ab", "xyz"));
	}

	public void testSplit2() {
		assertNull(CubbyUtils.split2(null, '_'));
		assertTrue(Arrays.deepEquals(new String[] { "" }, CubbyUtils.split2("",
				'_')));
		assertTrue(Arrays.deepEquals(new String[] { "ab", "" }, CubbyUtils
				.split2("ab_", '_')));
		assertTrue(Arrays.deepEquals(new String[] { "ab_cd_de_" }, CubbyUtils
				.split2("ab_cd_de_", ',')));
		assertTrue(Arrays.deepEquals(new String[] { "ab", "cd_de_" },
				CubbyUtils.split2("ab_cd_de_", '_')));
	}

	public void testGetPriority() throws Exception {
		Method method = TestGetPriprity.class.getMethod("m1", new Class[0]);
		assertEquals(Integer.MAX_VALUE, CubbyUtils.getPriority(method));

		method = TestGetPriprity.class.getMethod("m2", new Class[0]);
		assertEquals(Integer.MAX_VALUE, CubbyUtils.getPriority(method));

		method = TestGetPriprity.class.getMethod("m3", new Class[0]);
		assertEquals(0, CubbyUtils.getPriority(method));
	}

	static class TestGetPriprity {
		public void m1() {
		}

		@Path
		public void m2() {

		}

		@Path(value = "", priority = 0)
		public void m3() {

		}
	}

	public void testGetFormBean1() throws Exception {
		MockFormAction action = new MockFormAction();
		Method method = ClassUtil.getMethod(action.getClass(), "normal",
				new Class[0]);
		Object actual = CubbyUtils
				.getFormBean(action, MockAction.class, method);
		assertSame(action, actual);
	}

	public void testGetFormBean2() throws Exception {
		MockFormAction action = new MockFormAction();
		Method method = ClassUtil.getMethod(action.getClass(), "legalForm",
				new Class[0]);
		Object actual = CubbyUtils
				.getFormBean(action, MockAction.class, method);
		assertSame(action.form, actual);
	}

	public void testGetFormBean3() throws Exception {
		MockFormAction action = new MockFormAction();
		Method method = ClassUtil.getMethod(action.getClass(), "illegalForm",
				new Class[0]);
		try {
			CubbyUtils.getFormBean(action, MockAction.class, method);
			fail();
		} catch (ActionRuntimeException e) {
			// ok
			e.printStackTrace();
		}
	}

}
