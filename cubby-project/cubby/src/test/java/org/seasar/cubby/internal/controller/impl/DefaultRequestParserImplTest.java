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
package org.seasar.cubby.internal.controller.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.seasar.cubby.internal.controller.impl.DefaultRequestParserImpl;

public class DefaultRequestParserImplTest {

	public DefaultRequestParserImpl requestParser = new DefaultRequestParserImpl();

	@Test
	public void getEmptyParameterMap() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		Map<String, String[]> requestParameterMap = new HashMap<String, String[]>();
		expect(request.getParameterMap()).andReturn(requestParameterMap);
		replay(request);

		Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(request);
		assertEquals("parameterMap.size()", 0, parameterMap.size());
		verify(request);
	}

	@Test
	public void getParameterMap() throws Throwable {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		Map<String, String[]> requestParameterMap = new HashMap<String, String[]>();
		requestParameterMap.put("a", new String[] { "12345" });
		requestParameterMap.put("b", new String[] { "abc", "def" });
		expect(request.getParameterMap()).andReturn(requestParameterMap);
		replay(request);

		Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(request);
		assertEquals("parameterMap.size()", 2, parameterMap.size());
		Object[] a = parameterMap.get("a");
		assertEquals("a.length", 1, a.length);
		assertEquals("a[0]", "12345", a[0]);
		Object[] b = parameterMap.get("b");
		assertEquals("b.length", 2, b.length);
		assertEquals("b[0]", "abc", b[0]);
		assertEquals("b[1]", "def", b[1]);
		verify(request);
	}

	@Test
	public void isParsable1() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getContentType()).andReturn(
				"application/x-www-form-urlencoded").anyTimes();
		replay(request);
		assertTrue(requestParser.isParsable(request));
		verify(request);
	}

	@Test
	public void isParsable2() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getContentType()).andReturn("multipart/form-data")
				.anyTimes();
		replay(request);
		assertTrue(requestParser.isParsable(request));
		verify(request);
	}

	@Test
	public void isParsable3() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getContentType()).andReturn("application/atom+xml")
				.anyTimes();
		replay(request);
		assertTrue(requestParser.isParsable(request));
		verify(request);
	}

	@Test
	public void priority() {
		assertEquals(DefaultRequestParserImpl.DEFAULT_PRIORITY, requestParser
				.getPriority());
	}

}
