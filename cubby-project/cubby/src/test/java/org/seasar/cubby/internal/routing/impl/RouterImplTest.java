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
package org.seasar.cubby.internal.routing.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.internal.routing.Router;
import org.seasar.cubby.mock.MockPathResolverProvider;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.ProviderFactory;

public class RouterImplTest {

	private Router router = new RouterImpl();

	@Before
	public void setupProvider() {
		final PathResolver pathResolver = new PathResolverImpl();
		pathResolver.add(FooAction.class);
		ProviderFactory.bind(PathResolverProvider.class).toInstance(
				new MockPathResolverProvider(pathResolver));
	}

	@After
	public void teardownProvider() {
		ProviderFactory.clear();
	}

	@Test
	public void routing() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andStubReturn("/foo/");
		expect(request.getPathInfo()).andStubReturn("");
		expect(request.getMethod()).andStubReturn("GET");
		expect(request.getAttribute(CubbyConstants.ATTR_ROUTING)).andReturn(
				null);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		PathInfo pathInfo = router.routing(request, response);
		Map<String, Object[]> parameterMap = Collections.emptyMap();
		Routing routing = pathInfo.dispatch(parameterMap);
		assertNotNull(routing);

		verify(request, response);
	}

	@Test
	public void routingWithInternalForward() {
		Routing routing = createMock(Routing.class);
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andStubReturn("/foo/");
		expect(request.getPathInfo()).andStubReturn("");
		expect(request.getMethod()).andStubReturn("GET");
		expect(request.getAttribute(CubbyConstants.ATTR_ROUTING)).andReturn(
				routing);
		request.removeAttribute(CubbyConstants.ATTR_ROUTING);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(routing, request, response);

		PathInfo pathInfo = router.routing(request, response);
		Map<String, Object[]> parameterMap = Collections.emptyMap();
		assertSame(routing, pathInfo.dispatch(parameterMap));

		verify(routing, request, response);
	}

	@Test
	public void ignorePath() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andStubReturn("/js/ignore");
		expect(request.getPathInfo()).andStubReturn("");
		expect(request.getMethod()).andStubReturn("GET");
		expect(request.getAttribute(CubbyConstants.ATTR_ROUTING)).andReturn(
				null);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		PathInfo pathInfo = router.routing(request, response, Arrays
				.asList(new Pattern[] { Pattern.compile("/js/.*") }));
		assertNull(pathInfo);

		verify(request, response);
	}

	public static class FooAction {

		public ActionResult index() {
			return null;
		}

		@Path("/js/ignore")
		public ActionResult ignore() {
			return null;
		}
	}

}
