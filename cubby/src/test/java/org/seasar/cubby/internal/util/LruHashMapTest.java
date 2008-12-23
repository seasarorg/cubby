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
package org.seasar.cubby.internal.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Iterator;

import org.junit.Test;
import org.seasar.cubby.internal.util.LruHashMap;

public class LruHashMapTest {

	@Test
	public void all() throws Exception {
		LruHashMap<String, String> lru = new LruHashMap<String, String>(3);
		assertEquals(3, lru.getLimitSize());
		lru.put("aaa", "111");
		lru.put("bbb", "222");
		lru.put("ccc", "333");
		assertEquals("111", lru.get("aaa"));
		Iterator<String> i = lru.keySet().iterator();
		assertEquals("bbb", i.next());
		assertEquals("ccc", i.next());
		assertEquals("aaa", i.next());
		lru.put("ddd", "444");
		assertEquals(3, lru.size());
		assertNull(lru.get("bbb"));
		i = lru.keySet().iterator();
		assertEquals("ccc", i.next());
		assertEquals("aaa", i.next());
		assertEquals("ddd", i.next());
	}

}