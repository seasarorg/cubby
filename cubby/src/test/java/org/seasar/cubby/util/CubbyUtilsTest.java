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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionException;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.internal.util.CubbyUtils;

public class CubbyUtilsTest {

	@Test
	@Deprecated
	public void getObjectSize() {
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

	private static Object toCollection(Object[] objects) {
		List<Object> list = new ArrayList<Object>();
		for (Object o : objects) {
			list.add(o);
		}
		return list;
	}

	@Test
	public void getActionPath() throws Exception {
		assertEquals("/hoge/m1", CubbyUtils.getActionPath(Hoge1Action.class,
				Hoge1Action.class.getMethod("m1")));
		assertEquals("/hoge/m/m2", CubbyUtils.getActionPath(Hoge1Action.class,
				Hoge1Action.class.getMethod("m2")));
		assertEquals("/hoge/", CubbyUtils.getActionPath(Hoge1Action.class,
				Hoge1Action.class.getMethod("index")));
		assertEquals("/hoge/index2", CubbyUtils.getActionPath(
				Hoge1Action.class, Hoge1Action.class.getMethod("index2")));
		assertEquals("/hoge2/m1", CubbyUtils.getActionPath(Hoge2Action.class,
				Hoge2Action.class.getMethod("m1")));
		assertEquals("/hoge/m2", CubbyUtils.getActionPath(Hoge2Action.class,
				Hoge2Action.class.getMethod("m2")));
		assertEquals("/", CubbyUtils.getActionPath(MockAction.class,
				MockAction.class.getMethod("index")));
		assertEquals("/dummy1", CubbyUtils.getActionPath(MockAction.class,
				MockAction.class.getMethod("dummy1")));
		assertEquals("/dummy2", CubbyUtils.getActionPath(MockAction.class,
				MockAction.class.getMethod("dummy2")));
		assertEquals("/todo/lists", CubbyUtils.getActionPath(MockAction.class,
				MockAction.class.getMethod("todolist")));
		assertEquals("/tasklists", CubbyUtils.getActionPath(MockAction.class,
				MockAction.class.getMethod("tasklist")));
	}

	@Test
	public void getActionClassName() {
		assertEquals("hoge", CubbyUtils.getActionDirectory(Hoge1Action.class));
		assertEquals("hoge2", CubbyUtils.getActionDirectory(Hoge2Action.class));
	}

	@Test
	@SuppressWarnings("deprecation")
	public void replaceFirst() {
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

	@Test
	@SuppressWarnings("deprecation")
	public void split2() {
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

	@Test
	public void gGetPriority() throws Exception {
		Method method = TestGetPriprity.class.getMethod("m1", new Class[0]);
		assertEquals(Integer.MAX_VALUE, CubbyUtils.getPriority(method));

		method = TestGetPriprity.class.getMethod("m2", new Class[0]);
		assertEquals(Integer.MAX_VALUE, CubbyUtils.getPriority(method));

		method = TestGetPriprity.class.getMethod("m3", new Class[0]);
		assertEquals(0, CubbyUtils.getPriority(method));
	}

	private static class TestGetPriprity {
		public void m1() {
		}

		@Path
		public void m2() {

		}

		@Path(value = "", priority = 0)
		public void m3() {

		}
	}

	@Test
	@Deprecated
	public void formBean1() throws Exception {
		MockFormAction action = new MockFormAction();
		Method method = action.getClass().getMethod("normal");
		Object actual = CubbyUtils
				.getFormBean(action, MockFormAction.class, method);
		assertSame(action, actual);
	}

	@Test
	@Deprecated
	public void getFormBean2() throws Exception {
		MockFormAction action = new MockFormAction();
		Method method = action.getClass().getMethod("legalForm");
		Object actual = CubbyUtils
				.getFormBean(action, MockFormAction.class, method);
		assertSame(action.getForm(), actual);
	}

	@Test
	@Deprecated
	public void getFormBean3() throws Exception {
		MockFormAction action = new MockFormAction();
		Method method = action.getClass().getMethod("illegalForm");
		try {
			CubbyUtils.getFormBean(action, MockFormAction.class, method);
			fail();
		} catch (ActionException e) {
			// ok
			assertTrue(true);
		}
	}

	@Test
	public void isActionClass() throws Exception {
		assertTrue("アクションクラスであればtrue", CubbyUtils
				.isActionClass(ChildAction.class));
		assertFalse("Actionを継承していないクラスはアクションクラスではない", CubbyUtils
				.isActionClass(Object.class));
		assertFalse("抽象クラスはアクションクラスではない", CubbyUtils
				.isActionClass(ParentAction.class));
	}

	@Test
	public void isActionMethod() throws Exception {
		assertTrue("親クラスのアクションメソッド", CubbyUtils
				.isActionMethod(ChildAction.class.getMethod("m1")));
		assertTrue("オーバーライドした親クラスのアクションメソッド", CubbyUtils
				.isActionMethod(ChildAction.class.getMethod("m2")));
		assertTrue("子クラスのアクションメソッド", CubbyUtils
				.isActionMethod(ChildAction.class.getMethod("m3")));
		assertFalse("メソッドの引数が不正", CubbyUtils.isActionMethod(ChildAction.class
				.getMethod("m4", int.class)));
		assertFalse("メソッドの戻り値が不正", CubbyUtils.isActionMethod(ChildAction.class
				.getMethod("m5")));
	}

	public abstract class ParentAction extends Action {
		public ActionResult m1() {
			return null;
		}

		public abstract ActionResult m2();
	}

	public class ChildAction extends ParentAction {
		@Override
		public ActionResult m2() {
			return null;
		}

		public ActionResult m3() {
			return null;
		}

		public ActionResult m4(int value) {
			return null;
		}

		public Object m5() {
			return null;
		}
	}
}
