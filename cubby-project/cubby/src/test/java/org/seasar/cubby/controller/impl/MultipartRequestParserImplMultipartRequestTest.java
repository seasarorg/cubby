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
package org.seasar.cubby.controller.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.easymock.IAnswer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;

public class MultipartRequestParserImplMultipartRequestTest {

	private final RequestParser requestParser = new MultipartRequestParser();

	private HttpServletRequest request;

	private final Hashtable<String, Object> attributes = new Hashtable<String, Object>();

	private InputStream input;

	private MultipartRequestEntity entity;

	@Before
	@SuppressWarnings("unchecked")
	public void setupRequest() throws Exception {
		request = createMock(HttpServletRequest.class);
		expect(request.getCharacterEncoding()).andStubReturn("UTF-8");
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
		expect(request.getParameterMap()).andStubReturn(attributes);
		expect(request.getContentType()).andStubAnswer(new IAnswer<String>() {

			public String answer() throws Throwable {
				return entity.getContentType();
			}

		});
		expect(request.getContentLength()).andStubAnswer(
				new IAnswer<Integer>() {

					public Integer answer() throws Throwable {
						return (int) entity.getContentLength();
					}

				});
		expect(request.getInputStream()).andStubReturn(
				new ServletInputStream() {

					@Override
					public int read() throws IOException {
						return input.read();
					}

				});
		replay(request);

		final FileUpload fileUpload = new ServletFileUpload();
		fileUpload.setFileItemFactory(new DiskFileItemFactory());
		final RequestContext requestContext = new ServletRequestContext(request);
		ProviderFactory.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(final Class<T> type) {
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
	public void getParameterMap() throws Exception {
		final PartSource filePartSource = new ByteArrayPartSource("upload.txt",
				"upload test".getBytes("UTF-8"));
		final PostMethod method = new PostMethod();
		final Part[] parts = new Part[] { new StringPart("a", "12345"),
				new StringPart("b", "abc"), new StringPart("b", "def"),
				new FilePart("file", filePartSource) };
		this.entity = new MultipartRequestEntity(parts, method.getParams());
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		this.entity.writeRequest(out);
		out.flush();
		out.close();
		this.input = new ByteArrayInputStream(out.toByteArray());
		this.attributes.put("c", new String[] { "999" });

		final Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(request);
		assertEquals("parameterMap.size()", 4, parameterMap.size());
		final Object[] a = parameterMap.get("a");
		assertEquals("a.length", 1, a.length);
		assertEquals("a[0]", "12345", a[0]);
		final Object[] b = parameterMap.get("b");
		assertEquals("b.length", 2, b.length);
		assertEquals("b[0]", "abc", b[0]);
		assertEquals("b[1]", "def", b[1]);
		final Object[] c = parameterMap.get("c");
		assertEquals("c.length", 1, c.length);
		assertEquals("c[0]", "999", c[0]);
		final Object[] file = parameterMap.get("file");
		assertEquals("file.length", 1, file.length);
		assertTrue("file[0]", file[0] instanceof FileItem);
		final FileItem item = (FileItem) file[0];
		assertEquals("upload test", new String(item.get(), "UTF-8"));
	}

	@Test
	public void getParameterMapEmpty() throws Exception {
		final PostMethod method = new PostMethod();
		final Part[] parts = new Part[0];
		this.entity = new MultipartRequestEntity(parts, method.getParams());
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		this.entity.writeRequest(out);
		out.flush();
		out.close();
		this.input = new ByteArrayInputStream(out.toByteArray());

		final Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(request);
		assertTrue("parameterMap.isEmpty()", parameterMap.isEmpty());
	}

}
