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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.easymock.IAnswer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.controller.RequestParseException;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;

public class MultipartRequestParserImplTest {

	private RequestParser requestParser = new MultipartRequestParser();

	private HttpServletRequest request;

	private String contentType;

	@Before
	public void setupRequest() throws Exception {
		request = createMock(HttpServletRequest.class);
		expect(request.getCharacterEncoding()).andStubReturn("UTF-8");
		expect(request.getParameterMap()).andStubReturn(
				new HashMap<String, String[]>());
		expect(request.getContentType()).andStubAnswer(new IAnswer<String>() {

			public String answer() throws Throwable {
				return contentType;
			}

		});
		replay(request);

		final FileUpload fileUpload = new ServletFileUpload();
		final RequestContext requestContext = new ServletRequestContext(request);
		ProviderFactory.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) {
						if (FileUpload.class.equals(type)) {
							return type.cast(fileUpload);
						}

						if (RequestContext.class.equals(type)) {
							return type.cast(requestContext);
						}

						return null;
					}

				}));
	}

	@After
	public void teardown() {
		ProviderFactory.clear();
	}

	@Test
	public void invalidCotntentType() {
		contentType = "application/x-www-form-urlencoded";
		try {
			Map<String, Object[]> parameterMap = requestParser
					.getParameterMap(request);
			fail();
		} catch (RequestParseException e) {
			assertTrue(e.getCause() instanceof InvalidContentTypeException);
		}
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
		assertEquals(DefaultRequestParser.DEFAULT_PRIORITY - 1,
				requestParser.getPriority());
	}

}
