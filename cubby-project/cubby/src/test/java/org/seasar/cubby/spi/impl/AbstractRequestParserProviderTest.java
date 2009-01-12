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
package org.seasar.cubby.spi.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.impl.DefaultRequestParserImpl;
import org.seasar.cubby.spi.RequestParserProvider;
import org.seasar.cubby.spi.impl.AbstractRequestParserProvider;

public class AbstractRequestParserProviderTest {

	private RequestParserProvider provider = new AbstractRequestParserProvider() {

		private List<RequestParser> parsers = Arrays
				.asList(new RequestParser[] { new DefaultRequestParserImpl(),
						new MyRequestParserImpl() });

		@Override
		protected Collection<RequestParser> getRequestParsers() {
			return parsers;
		}

	};

	@Test
	public void testSelect1() {
		Map<String, Object[]> parameterMap = new HashMap<String, Object[]>();
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getContentType()).andStubReturn(null);
		expect(request.getParameterMap()).andStubReturn(parameterMap);
		replay(request);
		assertEquals(parameterMap, provider.getParameterMap(request));
		verify(request);
	}

	@Test
	public void testSelect2() {
		Map<String, Object[]> parameterMap = new HashMap<String, Object[]>();
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getContentType()).andStubReturn(
				"application/x-www-form-urlencoded");
		expect(request.getParameterMap()).andStubReturn(parameterMap);
		replay(request);
		assertEquals(parameterMap, provider.getParameterMap(request));
		verify(request);
	}

	@Test
	public void testSelect4() {
		Map<String, Object[]> parameterMap = new HashMap<String, Object[]>();
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getContentType()).andStubReturn("application/atom+xml");
		expect(request.getParameterMap()).andStubReturn(parameterMap);
		replay(request);
		assertEquals(parameterMap, provider.getParameterMap(request));
		verify(request);
	}

	@Test
	public void testSelect5() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getContentType()).andStubReturn("foo/bar");
		replay(request);
		assertEquals(MY_REQUEST_PARSER_PARAMETER_MAP, provider
				.getParameterMap(request));
		verify(request);
	}

	private static Map<String, Object[]> MY_REQUEST_PARSER_PARAMETER_MAP;
	static {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("abc", new Object[] { "def" });
		MY_REQUEST_PARSER_PARAMETER_MAP = map;
	}

	private static class MyRequestParserImpl implements RequestParser {

		public Map<String, Object[]> getParameterMap(HttpServletRequest request) {
			return MY_REQUEST_PARSER_PARAMETER_MAP;
		}

		public int getPriority() {
			return 1;
		}

		public boolean isParsable(HttpServletRequest request) {
			String contentType = request.getContentType();
			return contentType != null && contentType.startsWith("foo/");
		}

	}
}
