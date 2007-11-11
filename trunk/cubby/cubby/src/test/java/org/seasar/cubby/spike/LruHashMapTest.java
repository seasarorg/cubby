package org.seasar.cubby.spike;

import java.util.Iterator;

import org.seasar.framework.util.LruHashMap;

import junit.framework.TestCase;

public class LruHashMapTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testA() throws Exception {
		LruHashMap map = new LruHashMap(3);
		map.put("a", "1");
		map.put("b", "1");
		map.put("c", "1");
		assertEquals(3, map.size());
		Iterator iterator = map.keySet().iterator();
		assertEquals("a", iterator.next());
		assertEquals("b", iterator.next());
		assertEquals("c", iterator.next());
		
		map.put("d", "1");
		assertEquals("最新の履歴3件のみ保持", 3, map.size());
		iterator = map.keySet().iterator();
		assertEquals("b", iterator.next());
		assertEquals("c", iterator.next());
		assertEquals("d", iterator.next());
		
		map.put("e", "1");
		assertEquals("最新の履歴3件のみ保持", 3, map.size());
		iterator = map.keySet().iterator();
		assertEquals("c", iterator.next());
		assertEquals("d", iterator.next());
		assertEquals("e", iterator.next());
	}

}
