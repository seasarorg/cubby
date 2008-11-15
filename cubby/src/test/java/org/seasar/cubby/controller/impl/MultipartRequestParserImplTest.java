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
package org.seasar.cubby.controller.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.controller.RequestParser;
import org.seasar.cubby.internal.controller.impl.DefaultRequestParserImpl;
import org.seasar.cubby.internal.controller.impl.MultipartRequestParserImpl;
import org.seasar.cubby.mock.MockContainerProvider;

public class MultipartRequestParserImplTest {

	private RequestParser requestParser = new MultipartRequestParserImpl();

	private HttpServletRequest request;

	private Hashtable<String, Object> attributes = new Hashtable<String, Object>();

	private String contentType;

	@Before
	@SuppressWarnings("unchecked")
	public void setupRequest() {
		request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(String.class.cast(anyObject())))
				.andStubAnswer(new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return attributes.get(getCurrentArguments()[0]);
					}

				});
		request.setAttribute(String.class.cast(anyObject()), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				attributes.put(String.class.cast(getCurrentArguments()[0]),
						getCurrentArguments()[1]);
				return null;
			}

		});
		expect(request.getAttributeNames()).andStubAnswer(
				new IAnswer<Enumeration>() {

					public Enumeration answer() throws Throwable {
						return attributes.keys();
					}

				});
		expect(request.getParameterMap()).andReturn(attributes);
		expect(request.getMethod()).andReturn("GET");
		expect(request.getContentType()).andStubAnswer(new IAnswer<String>() {

			public String answer() throws Throwable {
				return contentType;
			}

		});
		replay(request);

		final FileUpload fileUpload = new ServletFileUpload();
		final RequestContext requestContext = new ServletRequestContext(request);
		MockContainerProvider.setContainer(new Container() {

			public <T> T lookup(Class<T> type) {
				if (FileUpload.class.equals(type)) {
					return type.cast(fileUpload);
				}

				if (RequestContext.class.equals(type)) {
					return type.cast(requestContext);
				}

				return null;
			}

		});
	}

	@Test
	public void getEmptyParameterMap() {
		Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(request);
		assertEquals("parameterMap.size()", 0, parameterMap.size());
	}

	@Test
	public void getParameterMap() {
		attributes.put("a", new String[] { "12345" });
		attributes.put("b", new String[] { "abc", "def" });
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
	}

	@Test
	public void isParsable() {
		contentType = "application/x-www-form-urlencoded";
		assertFalse(requestParser.isParsable(request));

		contentType = "multipart/form-data";
		assertTrue(requestParser.isParsable(request));

		contentType = "application/atom+xml";
		assertFalse(requestParser.isParsable(request));
	}

	public void testPriority() {
		assertEquals(DefaultRequestParserImpl.DEFAULT_PRIORITY - 1,
				requestParser.getPriority());
	}

}
