package org.seasar.cubby.filter;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Routing;

public class CubbyFilterLifecycleTest {

	@Test
	public void unmatchAnyAction() throws ServletException, IOException {
		final FilterConfig filterConfig = createNiceMock(FilterConfig.class);
		expect(filterConfig.getInitParameter("ignorePathPattern")).andReturn(
				"/js/.*,/img/.*");
		final FilterChain filterChain = createNiceMock(FilterChain.class);
		final HttpServletRequest request = createNiceMock(HttpServletRequest.class);
		expect(request.getServletPath()).andStubReturn("/a/b");
		expect(request.getMethod()).andStubReturn("GET");
		expect(request.getCharacterEncoding()).andStubReturn("UTF-8");
		final HttpServletResponse response = createNiceMock(HttpServletResponse.class);
		final PathResolver pathResolver = createNiceMock(PathResolver.class);
		replay(filterConfig, filterChain, request, response, pathResolver);

		final CubbyFilter cubbyFilter = new CubbyFilter() {

			@Override
			protected PathResolver createPathResolver() {
				return pathResolver;
			}

		};
		cubbyFilter.init(filterConfig);
		cubbyFilter.doFilter(request, response, filterChain);
		cubbyFilter.destroy();

		verify(filterConfig, filterChain, request, response, pathResolver);
	}

	@Test
	public void matchAnyAction() throws Exception {
		final FilterConfig filterConfig = createNiceMock(FilterConfig.class);
		expect(filterConfig.getInitParameter("ignorePathPattern")).andReturn(
				"/js/.*,/img/.*");
		final FilterChain filterChain = createNiceMock(FilterChain.class);
		final HttpServletRequest request = createNiceMock(HttpServletRequest.class);
		expect(request.getServletPath()).andStubReturn("/a/b");
		expect(request.getMethod()).andStubReturn("GET");
		expect(request.getCharacterEncoding()).andStubReturn("UTF-8");
		final HttpServletResponse response = createNiceMock(HttpServletResponse.class);
		final PathInfo pathInfo = createNiceMock(PathInfo.class);
		final PathResolver pathResolver = createNiceMock(PathResolver.class);
		expect(pathResolver.getPathInfo("/a/b", "GET", "UTF-8")).andReturn(
				pathInfo);
		final ActionResultWrapper actionResultWrapper = createNiceMock(ActionResultWrapper.class);
		final ActionProcessor actionProcessor = createNiceMock(ActionProcessor.class);
		expect(
				actionProcessor.process((HttpServletRequest) anyObject(),
						(HttpServletResponse) anyObject(),
						(Routing) anyObject())).andStubReturn(
				actionResultWrapper);
		replay(filterConfig, filterChain, request, response, pathResolver,
				pathInfo, actionProcessor);

		final CubbyFilter cubbyFilter = new CubbyFilter() {

			@Override
			protected PathResolver createPathResolver() {
				return pathResolver;
			}

			@Override
			protected Map<String, Object[]> parseRequest(
					HttpServletRequest request) {
				return Collections.emptyMap();
			}

			@Override
			protected ActionProcessor createActionProcessor() {
				return actionProcessor;
			}

		};
		cubbyFilter.init(filterConfig);
		cubbyFilter.doFilter(request, response, filterChain);
		cubbyFilter.destroy();

		verify(filterConfig, filterChain, request, response, pathResolver,
				pathInfo, actionProcessor);
	}
}
