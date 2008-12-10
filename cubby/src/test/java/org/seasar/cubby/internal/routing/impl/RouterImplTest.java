package org.seasar.cubby.internal.routing.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.internal.routing.PathInfo;
import org.seasar.cubby.internal.routing.PathResolver;
import org.seasar.cubby.internal.routing.Router;
import org.seasar.cubby.internal.routing.Routing;
import org.seasar.cubby.mock.MockPathResolverProvider;

public class RouterImplTest {

	private Router router = new RouterImpl();

	private PathResolver pathResolver = new PathResolverImpl();

	@Test
	public void routing1() {
		MockPathResolverProvider.setPathResolver(pathResolver);
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andStubReturn("/foo/bar");
		expect(request.getPathInfo()).andStubReturn("");
		expect(request.getMethod()).andStubReturn("GET");

		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		PathInfo pathInfo = router.routing(request, response);
		assertNull(pathInfo);
	}

	public static class Foo {
		public ActionResult bar() {
			return null;
		}
	}

	@Test
	public void routing2() throws Exception {

		pathResolver.addAll(Arrays.asList(new Class<?>[] { Foo.class }));
		MockPathResolverProvider.setPathResolver(pathResolver);
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andStubReturn("/foo/bar");
		expect(request.getPathInfo()).andStubReturn("");
		expect(request.getMethod()).andStubReturn("GET");

		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		PathInfo pathInfo = router.routing(request, response);
		assertNotNull(pathInfo);
		Map<String, Routing> routings = pathInfo.getOnSubmitRoutings();
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertNotNull(routing);
		assertEquals(Foo.class, routing.getActionClass());
		assertEquals(Foo.class.getMethod("bar"), routing.getMethod());
	}

	@Test
	public void routingWithIgnorePath1() {
		MockPathResolverProvider.setPathResolver(pathResolver);
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andStubReturn("/foo/bar");
		expect(request.getPathInfo()).andStubReturn("");
		expect(request.getMethod()).andStubReturn("GET");

		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		List<Pattern> ignorePathPatterns = Arrays.asList(new Pattern[] {
				Pattern.compile("/foo/.*"), Pattern.compile("/baz/.*") });
		PathInfo pathInfo = router.routing(request, response,
				ignorePathPatterns);
		assertNull(pathInfo);
	}

	@Test
	public void routingWithIgnorePath2() {
		MockPathResolverProvider.setPathResolver(pathResolver);
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getServletPath()).andStubReturn("/exists/bar");
		expect(request.getPathInfo()).andStubReturn("");
		expect(request.getMethod()).andStubReturn("GET");

		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		List<Pattern> ignorePathPatterns = Arrays.asList(new Pattern[] {
				Pattern.compile("/foo/.*"), Pattern.compile("/baz/.*") });
		PathInfo pathInfo = router.routing(request, response,
				ignorePathPatterns);
		assertNull(pathInfo);
	}

}
