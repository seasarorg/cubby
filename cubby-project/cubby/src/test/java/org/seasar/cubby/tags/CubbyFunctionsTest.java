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
package org.seasar.cubby.tags;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;

public class CubbyFunctionsTest {

	@Test
	public void containsInCollection() {
		List<String> action = new ArrayList<String>();
		action.add("unvalidate");
		action.add("validateRecord");
		action.add("branch");
		assertFalse(CubbyFunctions.contains(action, "validate"));
		action.add("validate");
		assertTrue(CubbyFunctions.contains(action, "validate"));
	}

	@Test
	public void containsInArray() {
		String[] array1 = { "unvalidate", "validateRecord", "branch" };
		String[] array2 = { "unvalidate", "validateRecord", "branch",
				"validate" };
		assertFalse(CubbyFunctions.contains(array1, "validate"));
		assertTrue(CubbyFunctions.contains(array2, "validate"));
	}

	@Test
	public void containsInNull() {
		assertFalse(CubbyFunctions.contains(null, null));
	}

	@Test
	public void containesKey() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "value");
		assertTrue(CubbyFunctions.containsKey(map, "name"));
		assertFalse(CubbyFunctions.containsKey(map, "value"));
	}

	@Test
	public void containesValue() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "value");
		assertTrue(CubbyFunctions.containsValue(map, "value"));
		assertFalse(CubbyFunctions.containsValue(map, "name"));
	}

	@Test
	public void odd() {
		assertEquals("a", CubbyFunctions.odd(0, "a, b, c"));
		assertEquals("b", CubbyFunctions.odd(1, "a, b, c"));
		assertEquals("c", CubbyFunctions.odd(2, "a, b, c"));
		assertEquals("a", CubbyFunctions.odd(3, "a, b, c"));
		assertEquals("b", CubbyFunctions.odd(4, "a, b, c"));
		assertEquals("c", CubbyFunctions.odd(5, "a, b, c"));
	}

	@Test
	public void out() {
		assertEquals("abc&amp;&lt;&gt;&quot;&#39;def", CubbyFunctions
				.out("abc&<>\"'def"));
		assertEquals("", CubbyFunctions.out(null));
	}

	@Test
	public void dateFormat() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2008, Calendar.DECEMBER, 24);
		Date date = calendar.getTime();
		assertEquals("20081224", CubbyFunctions.dateFormat(date, "yyyyMMdd"));
		assertEquals("", CubbyFunctions.dateFormat(new Object(), "yyyyMMdd"));
	}

	@Test
	public void ifrender() {
		assertEquals("abc", CubbyFunctions.ifrender(true, "abc"));
		assertEquals(TagUtils.REMOVE_ATTRIBUTE, CubbyFunctions.ifrender(false,
				"abc"));
	}

	@Test
	public void urlWithUTF8() throws Exception {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getCharacterEncoding()).andStubReturn("UTF-8");
		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command() {

			public void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				assertEquals("abc%20%E3%81%82%E3%81%84%E3%81%86",
						CubbyFunctions.url("abc あいう"));
				assertEquals("", CubbyFunctions.url(null));
			}

		});

		verify(request, response);
	}

	@Test
	public void urlWithWindows31J() throws Exception {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getCharacterEncoding()).andStubReturn("Windows-31J");
		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command() {

			public void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				assertEquals("abc%20%82%A0%82%A2%82%A4", CubbyFunctions
						.url("abc あいう"));
				assertEquals("", CubbyFunctions.url(null));
			}

		});

		verify(request, response);
	}

}
