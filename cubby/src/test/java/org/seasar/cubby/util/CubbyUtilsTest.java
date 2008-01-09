package org.seasar.cubby.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.seasar.cubby.controller.MockAction;
import org.seasar.framework.util.ClassUtil;

public class CubbyUtilsTest extends TestCase {

	public void testGetObjectSize() {
		// array
		assertEquals(0, CubbyUtils.getObjectSize(null));
		assertEquals(1, CubbyUtils.getObjectSize(""));
		assertEquals(0, CubbyUtils.getObjectSize(new Object[] {}));
		assertEquals(3, CubbyUtils.getObjectSize(new Object[] {1,2,3}));
		assertEquals(3, CubbyUtils.getObjectSize(new Object[] {null,2,3}));

		// collection
		assertEquals(0, CubbyUtils.getObjectSize(toCollection(new Object[] {})));
		assertEquals(3, CubbyUtils.getObjectSize(toCollection(new Object[] {1,2,3})));
		assertEquals(3, CubbyUtils.getObjectSize(toCollection(new Object[] {null,2,3})));

	}

	@SuppressWarnings("unchecked")
	private Collection toCollection(Object[] objects) {
		List<Object> list = new ArrayList<Object>();
		for (Object o : objects) {
			list.add(o);
		}
		return list;
	}

	public void testGetActionUrl() {
		assertEquals("/hoge/m1", CubbyUtils.getActionUrl(Hoge1Action.class, ClassUtil.getMethod(Hoge1Action.class, "m1", null)));
		assertEquals("/hoge/m/m2", CubbyUtils.getActionUrl(Hoge1Action.class, ClassUtil.getMethod(Hoge1Action.class, "m2", null)));
		assertEquals("/hoge/", CubbyUtils.getActionUrl(Hoge1Action.class, ClassUtil.getMethod(Hoge1Action.class, "index", null)));
		assertEquals("/hoge/index2", CubbyUtils.getActionUrl(Hoge1Action.class, ClassUtil.getMethod(Hoge1Action.class, "index2", null)));
		assertEquals("/hoge2/m1", CubbyUtils.getActionUrl(Hoge2Action.class, ClassUtil.getMethod(Hoge2Action.class, "m1", null)));
		assertEquals("/hoge/m2", CubbyUtils.getActionUrl(Hoge2Action.class, ClassUtil.getMethod(Hoge2Action.class, "m2", null)));
		assertEquals("/", CubbyUtils.getActionUrl(MockAction.class, ClassUtil.getMethod(MockAction.class, "index", null)));
		assertEquals("/dummy1", CubbyUtils.getActionUrl(MockAction.class, ClassUtil.getMethod(MockAction.class, "dummy1", null)));
		assertEquals("/dummy2", CubbyUtils.getActionUrl(MockAction.class, ClassUtil.getMethod(MockAction.class, "dummy2", null)));
		assertEquals("/todo/lists", CubbyUtils.getActionUrl(MockAction.class, ClassUtil.getMethod(MockAction.class, "todolist", null)));
		assertEquals("/tasklists", CubbyUtils.getActionUrl(MockAction.class, ClassUtil.getMethod(MockAction.class, "tasklist", null)));
	}

	public void testGetActionClassName() {
		assertEquals("hoge", CubbyUtils.getActionClassName(Hoge1Action.class));
		assertEquals("hoge2", CubbyUtils.getActionClassName(Hoge2Action.class));
	}

}
