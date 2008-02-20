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
