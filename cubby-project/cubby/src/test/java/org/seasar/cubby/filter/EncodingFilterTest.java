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
package org.seasar.cubby.filter;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.IAnswer;
import org.junit.Test;

public class EncodingFilterTest {

	@Test
	public void setEncoding1() throws ServletException, IOException {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("encoding")).andReturn("UTF-8");
		expect(config.getInitParameter("forceEncoding")).andReturn(null);
		expect(config.getInitParameter("URIEncoding")).andReturn(null);
		expect(config.getInitParameter("URIBytesEncoding")).andReturn(null);

		HttpServletRequest request = createMockHttpServletRequest();
		expect(request.getCharacterEncoding()).andReturn(null);
		request.setCharacterEncoding("UTF-8");

		HttpServletResponse response = createMock(HttpServletResponse.class);

		FilterChain chain = createMock(FilterChain.class);
		chain.doFilter(isA(ServletRequest.class), isA(ServletResponse.class));

		replay(config, request, response, chain);

		EncodingFilter filter = new EncodingFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

	@Test
	public void setEncoding2() throws ServletException, IOException {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("encoding")).andReturn("UTF-8");
		expect(config.getInitParameter("forceEncoding")).andReturn(null);
		expect(config.getInitParameter("URIEncoding")).andReturn(null);
		expect(config.getInitParameter("URIBytesEncoding")).andReturn(null);

		HttpServletRequest request = createMockHttpServletRequest();
		expect(request.getCharacterEncoding()).andReturn("Shift_JIS");

		HttpServletResponse response = createMock(HttpServletResponse.class);

		FilterChain chain = createMock(FilterChain.class);
		chain.doFilter(isA(ServletRequest.class), isA(ServletResponse.class));

		replay(config, request, response, chain);

		EncodingFilter filter = new EncodingFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

	@Test
	public void setForceEncoding1() throws ServletException, IOException {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("encoding")).andReturn("UTF-8");
		expect(config.getInitParameter("forceEncoding")).andReturn("true");
		expect(config.getInitParameter("URIEncoding")).andReturn(null);
		expect(config.getInitParameter("URIBytesEncoding")).andReturn(null);

		HttpServletRequest request = createMockHttpServletRequest();
		expect(request.getCharacterEncoding()).andReturn("Shift_JIS");
		request.setCharacterEncoding("UTF-8");

		HttpServletResponse response = createMock(HttpServletResponse.class);

		FilterChain chain = createMock(FilterChain.class);
		chain.doFilter(isA(ServletRequest.class), isA(ServletResponse.class));

		replay(config, request, response, chain);

		EncodingFilter filter = new EncodingFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

	@Test
	public void setForceEncoding2() throws ServletException, IOException {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("encoding")).andReturn("UTF-8");
		expect(config.getInitParameter("forceEncoding")).andReturn("true");
		expect(config.getInitParameter("URIEncoding")).andReturn(null);
		expect(config.getInitParameter("URIBytesEncoding")).andReturn(null);

		HttpServletRequest request = createMockHttpServletRequest();
		expect(request.getCharacterEncoding()).andReturn("Shift_JIS");
		request.setCharacterEncoding("UTF-8");

		HttpServletResponse response = createMock(HttpServletResponse.class);

		FilterChain chain = createMock(FilterChain.class);
		chain.doFilter(isA(ServletRequest.class), isA(ServletResponse.class));

		replay(config, request, response, chain);

		EncodingFilter filter = new EncodingFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

	@Test
	public void setForceEncoding3() throws ServletException, IOException {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("encoding")).andReturn("UTF-8");
		expect(config.getInitParameter("forceEncoding")).andReturn("false");
		expect(config.getInitParameter("URIEncoding")).andReturn(null);
		expect(config.getInitParameter("URIBytesEncoding")).andReturn(null);

		HttpServletRequest request = createMockHttpServletRequest();
		expect(request.getCharacterEncoding()).andReturn("Shift_JIS");

		HttpServletResponse response = createMock(HttpServletResponse.class);

		FilterChain chain = createMock(FilterChain.class);
		chain.doFilter(isA(ServletRequest.class), isA(ServletResponse.class));

		replay(config, request, response, chain);

		EncodingFilter filter = new EncodingFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

	@Test
	public void doURIEncoding1() throws ServletException, IOException {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("encoding")).andReturn("UTF-8");
		expect(config.getInitParameter("forceEncoding")).andReturn("false");
		expect(config.getInitParameter("URIEncoding")).andReturn("UTF-8");
		expect(config.getInitParameter("URIBytesEncoding")).andReturn(null);

		HttpServletRequest request = createMockHttpServletRequest();
		expect(request.getCharacterEncoding()).andReturn(null);
		request.setCharacterEncoding("UTF-8");
		expect(request.getServletPath()).andReturn(
				new String("あいうえおABC123".getBytes("UTF-8"), "ISO-8859-1"));
		expect(request.getPathInfo()).andReturn(
				new String("かきくけこXYZ987".getBytes("UTF-8"), "ISO-8859-1"));

		HttpServletResponse response = createMock(HttpServletResponse.class);

		FilterChain chain = createMock(FilterChain.class);
		chain.doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
		expectLastCall().andAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				HttpServletRequest request = (HttpServletRequest) getCurrentArguments()[0];
				assertEquals("あいうえおABC123", request.getServletPath());
				assertEquals("かきくけこXYZ987", request.getPathInfo());
				return null;
			}

		});

		replay(config, request, response, chain);

		EncodingFilter filter = new EncodingFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

	@Test
	public void doURIEncoding2() throws ServletException, IOException {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("encoding")).andReturn("UTF-8");
		expect(config.getInitParameter("forceEncoding")).andReturn("false");
		expect(config.getInitParameter("URIEncoding")).andReturn("UTF-8");
		expect(config.getInitParameter("URIBytesEncoding")).andReturn(
				"ISO-8859-1");

		HttpServletRequest request = createMockHttpServletRequest();
		expect(request.getCharacterEncoding()).andReturn(null);
		request.setCharacterEncoding("UTF-8");
		expect(request.getServletPath()).andReturn(
				new String("あいうえおABC123".getBytes("UTF-8"), "ISO-8859-1"));
		expect(request.getPathInfo()).andReturn(
				new String("かきくけこXYZ987".getBytes("UTF-8"), "ISO-8859-1"));

		HttpServletResponse response = createMock(HttpServletResponse.class);

		FilterChain chain = createMock(FilterChain.class);
		chain.doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
		expectLastCall().andAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				HttpServletRequest request = (HttpServletRequest) getCurrentArguments()[0];
				assertEquals("あいうえおABC123", request.getServletPath());
				assertEquals("かきくけこXYZ987", request.getPathInfo());
				return null;
			}

		});

		replay(config, request, response, chain);

		EncodingFilter filter = new EncodingFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

	private HttpServletRequest createMockHttpServletRequest() {
		final Map<String, Object> requestAttributes = new HashMap<String, Object>();

		HttpServletRequest request = createMock(HttpServletRequest.class);

		expect(request.getAttribute((String) anyObject())).andStubAnswer(
				new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return requestAttributes.get(getCurrentArguments()[0]);
					}

				});

		request.setAttribute((String) anyObject(), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				requestAttributes.put((String) getCurrentArguments()[0],
						getCurrentArguments()[1]);
				return null;
			}

		});

		request.removeAttribute((String) anyObject());
		expectLastCall().andAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				requestAttributes.remove((String) getCurrentArguments()[0]);
				return null;
			}

		});

		return request;
	}

}
