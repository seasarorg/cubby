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
package org.seasar.cubby.internal.controller.impl;

import static org.easymock.EasyMock.anyBoolean;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.easymock.IAnswer;
import org.junit.Test;
import org.seasar.cubby.internal.controller.impl.FlashMapImpl;

/**
 * 
 * @author baba
 * 
 */
public class FlashMapTest {

	@Test
	public void sequence1() {
		final State state = new State();
		state.hasSession = false;
		sequence(state);
	}

	@Test
	public void sequence2() {
		final State state = new State();
		state.hasSession = true;
		sequence(state);
	}

	private void sequence(final State state) {
		final Map<String, Object> mapInSession = new ConcurrentHashMap<String, Object>();
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		final HttpSession session = createMock(HttpSession.class);
		expect(request.getSession(anyBoolean())).andStubAnswer(
				new IAnswer<HttpSession>() {

					public HttpSession answer() throws Throwable {
						boolean create = (Boolean) getCurrentArguments()[0];
						if (state.hasSession || create) {
							state.hasSession = true;
							return session;
						}
						return null;
					}

				});
		expect(request.getSession()).andStubAnswer(new IAnswer<HttpSession>() {

			public HttpSession answer() throws Throwable {
				return request.getSession(true);
			}

		});
		expect(session.getAttribute(FlashMapImpl.class.getName() + ".MAP"))
				.andStubReturn(mapInSession);
		session.setAttribute(eq(FlashMapImpl.class.getName() + ".MAP"),
				isA(Map.class));
		expectLastCall().andAnswer(new IAnswer<Object>() {

			@SuppressWarnings("unchecked")
			public Object answer() throws Throwable {
				Map<String, Object> m = (Map<String, Object>) getCurrentArguments()[1];
				assertEquals("abcde", m.get("value1"));
				return null;
			}

		});
		expectLastCall().andAnswer(new IAnswer<Object>() {

			@SuppressWarnings("unchecked")
			public Object answer() throws Throwable {
				Map<String, Object> m = (Map<String, Object>) getCurrentArguments()[1];
				assertEquals("abcde", m.get("value1"));
				assertEquals("fghij", m.get("value2"));
				return null;
			}

		});
		expectLastCall().andAnswer(new IAnswer<Object>() {

			@SuppressWarnings("unchecked")
			public Object answer() throws Throwable {
				Map<String, Object> m = (Map<String, Object>) getCurrentArguments()[1];
				assertEquals("abcde", m.get("value1"));
				return null;
			}

		});
		expectLastCall().andAnswer(new IAnswer<Object>() {

			@SuppressWarnings("unchecked")
			public Object answer() throws Throwable {
				Map<String, Object> m = (Map<String, Object>) getCurrentArguments()[1];
				assertTrue(m.isEmpty());
				return null;
			}

		});
		replay(request, session);

		Map<String, Object> map = new FlashMapImpl(request);
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertTrue(map.keySet().isEmpty());
		assertTrue(map.values().isEmpty());
		assertTrue(map.entrySet().isEmpty());

		map.put("value1", "abcde");
		Map<String, Object> child = new HashMap<String, Object>();
		child.put("value2", "fghij");
		map.putAll(child);

		assertEquals(2, map.size());
		assertFalse(map.isEmpty());
		assertFalse(map.keySet().isEmpty());
		assertFalse(map.values().isEmpty());
		assertFalse(map.entrySet().isEmpty());
		assertTrue(map.containsKey("value1"));
		assertTrue(map.containsKey("value2"));
		assertTrue(map.containsValue("abcde"));
		assertTrue(map.containsValue("fghij"));
		assertEquals("abcde", map.get("value1"));
		assertEquals("fghij", map.get("value2"));

		map.remove("value2");

		assertEquals(1, map.size());
		assertFalse(map.isEmpty());
		assertFalse(map.keySet().isEmpty());
		assertFalse(map.values().isEmpty());
		assertFalse(map.entrySet().isEmpty());
		assertTrue(map.containsKey("value1"));
		assertFalse(map.containsKey("value2"));
		assertTrue(map.containsValue("abcde"));
		assertFalse(map.containsValue("fghij"));
		assertEquals("abcde", map.get("value1"));

		map.clear();

		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertTrue(map.keySet().isEmpty());
		assertTrue(map.values().isEmpty());
		assertTrue(map.entrySet().isEmpty());

		verify(request, session);
	}

	private static class State {
		public boolean hasSession;
	}

}
