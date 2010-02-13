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

package org.seasar.cubby.filter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class SendErrorFilterTest {

	@Test
	public void doFilter1() throws Exception {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("statusCode")).andReturn(null);
		expect(config.getInitParameter("ignorePathPattern")).andReturn(null);
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andReturn("/bar/").anyTimes();
		HttpServletResponse response = createMock(HttpServletResponse.class);
		response.sendError(403);
		FilterChain chain = createMock(FilterChain.class);
		replay(config, request, response, chain);

		SendErrorFilter filter = new SendErrorFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

	@Test
	public void doFilter2() throws Exception {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("statusCode")).andReturn("404");
		expect(config.getInitParameter("ignorePathPattern")).andReturn(null);
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andReturn("/bar/").anyTimes();
		HttpServletResponse response = createMock(HttpServletResponse.class);
		response.sendError(404);
		FilterChain chain = createMock(FilterChain.class);
		replay(config, request, response, chain);

		SendErrorFilter filter = new SendErrorFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

	@Test
	public void doFilter3() throws Exception {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("statusCode")).andReturn("404");
		expect(config.getInitParameter("ignorePathPattern")).andReturn(
				".*\\.jsp,.*\\.html");
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andReturn("/foo/bar.html").anyTimes();
		HttpServletResponse response = createMock(HttpServletResponse.class);
		FilterChain chain = createMock(FilterChain.class);
		chain.doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
		replay(config, request, response, chain);

		SendErrorFilter filter = new SendErrorFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

	@Test
	public void doFilter4() throws Exception {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("statusCode")).andReturn("404");
		expect(config.getInitParameter("ignorePathPattern")).andReturn(
				".*\\.jsp,.*\\.html");
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andReturn("/foo/bar.html").anyTimes();
		HttpServletResponse response = createMock(HttpServletResponse.class);
		FilterChain chain = createMock(FilterChain.class);
		chain.doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
		replay(config, request, response, chain);

		SendErrorFilter filter = new SendErrorFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

	@Test
	public void doFilter5() throws Exception {
		FilterConfig config = createMock(FilterConfig.class);
		expect(config.getInitParameter("statusCode")).andReturn("404");
		expect(config.getInitParameter("ignorePathPattern")).andReturn(
				".*\\.jsp,.*\\.html");
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andReturn("/bar/").anyTimes();
		HttpServletResponse response = createMock(HttpServletResponse.class);
		response.sendError(404);
		FilterChain chain = createMock(FilterChain.class);

		replay(config, request, response, chain);

		SendErrorFilter filter = new SendErrorFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		filter.destroy();

		verify(config, request, response, chain);
	}

}
