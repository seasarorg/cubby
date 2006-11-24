package org.seasar.cubby.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

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

	private Collection toCollection(Object[] objects) {
		List<Object> list = new ArrayList<Object>();
		for (Object o : objects) {
			list.add(o);
		}
		return list;
	}
	
}
