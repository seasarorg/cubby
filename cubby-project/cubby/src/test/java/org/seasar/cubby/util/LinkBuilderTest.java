/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.util;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

public class LinkBuilderTest {

	@Test
	public void httpDefaultPort() throws MalformedURLException {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getRequestURL()).andStubReturn(
				new StringBuffer("http://example.com/aaa"));
		replay(request);

		// change protocol
		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("https");
			String link = linkBuilder.toLink(request);
			assertEquals("https://example.com/aaa", link);
		}

		// relative
		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("/bbb", link);
		}

		// use default port
		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setPort(-1);
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("/bbb", link);
		}

		// set default port
		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setPort(80);
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("/bbb", link);
		}

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("http");
			linkBuilder.setPort(80);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("/bbb", link);
		}

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("http");
			linkBuilder.setPort(8080);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("http://example.com:8080/bbb", link);
		}

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("https");
			linkBuilder.setPort(-1);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("https://example.com/bbb", link);
		}

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("https");
			linkBuilder.setPort(443);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("https://example.com/bbb", link);
		}

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("https");
			linkBuilder.setPort(8443);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("https://example.com:8443/bbb", link);
		}

		verify(request);
	}

	@Test
	public void http8080() throws MalformedURLException {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getRequestURL()).andStubReturn(
				new StringBuffer("http://example.com:8080/aaa"));
		replay(request);

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("http");
			linkBuilder.setPort(-1);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/aaa");
			String link = linkBuilder.toLink(request);
			assertEquals("http://example.com/aaa", link);
		}

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("http");
			linkBuilder.setPort(-1);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("http://example.com/bbb", link);
		}

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("http");
			linkBuilder.setPort(80);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("http://example.com/bbb", link);
		}

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("http");
			linkBuilder.setPort(8080);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("/bbb", link);
		}

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("https");
			linkBuilder.setPort(-1);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("https://example.com/bbb", link);
		}

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("https");
			linkBuilder.setPort(443);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("https://example.com/bbb", link);
		}

		{
			LinkBuilder linkBuilder = new LinkBuilder();
			linkBuilder.setProtocol("https");
			linkBuilder.setPort(8443);
			linkBuilder.setHost("example.com");
			linkBuilder.setFile("/bbb");
			String link = linkBuilder.toLink(request);
			assertEquals("https://example.com:8443/bbb", link);
		}

		verify(request);
	}

}
