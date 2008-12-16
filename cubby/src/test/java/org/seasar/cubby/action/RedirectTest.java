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
package org.seasar.cubby.action;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.mock.MockActionContext;
import org.seasar.cubby.mock.MockPathResolverProvider;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.RoutingException;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.ProviderFactory;

public class RedirectTest {

	private MockAction action = new MockAction();

	private HttpServletRequest request;

	private RequestDispatcher requestDispatcher;

	private HttpServletResponse response;

	@Before
	public void setupContainer() {
		final List<Class<?>> actionClasses = new ArrayList<Class<?>>();
		actionClasses.add(MockAction.class);
		final PathResolver pathResolver = new PathResolverImpl();
		pathResolver.addAll(actionClasses);
		ProviderFactory.bind(PathResolverProvider.class).toInstance(
				new MockPathResolverProvider(pathResolver));
	}

	public void teardownContainer() {
		ProviderFactory.clear();
	}

	@Before
	public void setupRequest() {
		request = createMock(HttpServletRequest.class);
		expect(request.getCharacterEncoding()).andReturn("UTF-8").anyTimes();
		expect(request.getRequestURL()).andReturn(
				new StringBuffer("http://localhost/foo")).anyTimes();
		requestDispatcher = createMock(RequestDispatcher.class);
		response = createMock(HttpServletResponse.class);
	}

	@Test
	public void basicSequence() throws Exception {
		final Method method = action.getClass().getMethod("dummy1");
		final MockActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getContextPath()).andReturn("/cubby").anyTimes();
		expect(response.encodeRedirectURL(isA(String.class))).andStubAnswer(
				new IAnswer<String>() {

					public String answer() throws Throwable {
						return String.class.cast(getCurrentArguments()[0]);
					}

				});
		response.sendRedirect("/cubby/mock/path.jsp");
		replay(request, requestDispatcher, response);

		final Redirect redirect = new Redirect("path.jsp");
		assertFalse(actionContext.isPrerendered());
		redirect.execute(actionContext, request, response);
		assertFalse(actionContext.isPrerendered());
		assertFalse(actionContext.isPostrendered());

		verify(request, response, requestDispatcher);
	}

	@Test
	public void basicSequenceWithProtocol() throws Exception {
		final Method method = action.getClass().getMethod("dummy1");
		final MockActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getContextPath()).andReturn("/cubby").anyTimes();
		expect(response.encodeRedirectURL(isA(String.class))).andStubAnswer(
				new IAnswer<String>() {

					public String answer() throws Throwable {
						return String.class.cast(getCurrentArguments()[0]);
					}

				});
		response.sendRedirect("https://localhost/cubby/mock/path.jsp");
		replay(request, requestDispatcher, response);

		final Redirect redirect = new Redirect("path.jsp", "https");
		assertFalse(actionContext.isPrerendered());
		redirect.execute(actionContext, request, response);
		assertFalse(actionContext.isPrerendered());
		assertFalse(actionContext.isPostrendered());

		verify(request, response, requestDispatcher);
	}

	@Test
	public void basicSequenceWithProtocolAndPort() throws Exception {
		final Method method = action.getClass().getMethod("dummy1");
		final MockActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getContextPath()).andReturn("/cubby").anyTimes();
		expect(response.encodeRedirectURL(isA(String.class))).andStubAnswer(
				new IAnswer<String>() {

					public String answer() throws Throwable {
						return String.class.cast(getCurrentArguments()[0]);
					}

				});
		response.sendRedirect("https://localhost:8080/cubby/mock/path.jsp");
		replay(request, requestDispatcher, response);

		final Redirect redirect = new Redirect("path.jsp", "https", 8080);
		assertFalse(actionContext.isPrerendered());
		redirect.execute(actionContext, request, response);
		assertFalse(actionContext.isPrerendered());
		assertFalse(actionContext.isPostrendered());

		verify(request, response, requestDispatcher);
	}

	@Test
	public void relativePath() throws Exception {
		final Method method = action.getClass().getMethod("dummy1");
		final ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getContextPath()).andReturn("/cubby").anyTimes();
		expect(response.encodeRedirectURL(isA(String.class))).andStubAnswer(
				new IAnswer<String>() {

					public String answer() throws Throwable {
						return String.class.cast(getCurrentArguments()[0]);
					}

				});
		response.sendRedirect("/cubby/mock/page.jsp");
		replay(request, requestDispatcher, response);

		final Redirect redirect = new Redirect("page.jsp");
		redirect.execute(actionContext, request, response);

		verify(request, response, requestDispatcher);
	}

	@Test
	public void relativePathWithProtocol() throws Exception {
		final Method method = action.getClass().getMethod("dummy1");
		final ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getContextPath()).andReturn("/cubby").anyTimes();
		expect(response.encodeRedirectURL(isA(String.class))).andStubAnswer(
				new IAnswer<String>() {

					public String answer() throws Throwable {
						return String.class.cast(getCurrentArguments()[0]);
					}

				});
		response.sendRedirect("https://localhost/cubby/mock/page.jsp");
		replay(request, requestDispatcher, response);

		final Redirect redirect = new Redirect("page.jsp", "https");
		redirect.execute(actionContext, request, response);

		verify(request, response, requestDispatcher);
	}

	@Test
	public void absolutePath() throws Exception {
		final Method method = action.getClass().getMethod("dummy1");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getContextPath()).andReturn("/cubby").anyTimes();
		expect(response.encodeRedirectURL(isA(String.class))).andStubAnswer(
				new IAnswer<String>() {

					public String answer() throws Throwable {
						return String.class.cast(getCurrentArguments()[0]);
					}

				});
		response.sendRedirect("/cubby/absolute/path.jsp");
		replay(request, requestDispatcher, response);

		final Redirect redirect = new Redirect("/absolute/path.jsp");
		redirect.execute(actionContext, request, response);

		verify(request, response, requestDispatcher);
	}

	@Test
	public void absolutePathWithProtocol() throws Exception {
		final Method method = action.getClass().getMethod("dummy1");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getContextPath()).andReturn("/cubby").anyTimes();
		expect(response.encodeRedirectURL(isA(String.class))).andStubAnswer(
				new IAnswer<String>() {

					public String answer() throws Throwable {
						return String.class.cast(getCurrentArguments()[0]);
					}

				});
		response.sendRedirect("https://localhost/cubby/absolute/path.jsp");
		replay(request, requestDispatcher, response);

		final Redirect redirect = new Redirect("/absolute/path.jsp", "https");
		redirect.execute(actionContext, request, response);

		verify(request, response, requestDispatcher);
	}

	@Test
	public void absoluteURL() throws Exception {
		final Method method = action.getClass().getMethod("dummy1");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getContextPath()).andReturn("/cubby").anyTimes();
		expect(response.encodeRedirectURL(isA(String.class))).andStubAnswer(
				new IAnswer<String>() {

					public String answer() throws Throwable {
						return String.class.cast(getCurrentArguments()[0]);
					}

				});
		response.sendRedirect("http://example.com/");
		replay(request, requestDispatcher, response);

		final Redirect redirect = new Redirect("http://example.com/");
		redirect.execute(actionContext, request, response);

		verify(request, response, requestDispatcher);
	}

	@Test
	public void rootContextPath() throws Exception {
		final Method method = action.getClass().getMethod("dummy1");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getContextPath()).andReturn("/").anyTimes();
		expect(response.encodeRedirectURL(isA(String.class))).andStubAnswer(
				new IAnswer<String>() {

					public String answer() throws Throwable {
						return String.class.cast(getCurrentArguments()[0]);
					}

				});
		response.sendRedirect("/mock/path.jsp");
		replay(request, requestDispatcher, response);

		final Redirect redirect = new Redirect("path.jsp");
		redirect.execute(actionContext, request, response);

		verify(request, response, requestDispatcher);
	}

	@Test
	public void redirectByClassAndMethod1() throws Exception {
		final Redirect redirect = new Redirect(MockAction.class, "dummy1");
		assertEquals("/mock/dummy1", redirect.getPath("UTF-8"));
	}

	@Test
	public void redirectByClassAndMethod2() throws Exception {
		final Map<String, String[]> values = new LinkedHashMap<String, String[]>();
		values.put("value1", new String[] { "123" });
		values.put("value2", new String[] { "456" });

		final Redirect redirect = new Redirect(MockAction.class, "dummy1",
				values);
		assertEquals("/mock/dummy1?value1=123&value2=456", redirect
				.getPath("UTF-8"));
	}

	@Test
	public void redirectByClassAndMethod3() throws Exception {
		final Map<String, String[]> values = new LinkedHashMap<String, String[]>();
		values.put("value1", new String[] { "123" });
		values.put("value2", new String[] { "456" });
		final Redirect redirect = new Redirect(MockAction.class, "dummy2",
				values);
		assertEquals("/mock/dummy2/123/456", redirect.getPath("UTF-8"));
	}

	@Test
	public void redirectByClassAndMethod4() throws Exception {
		final Map<String, String[]> values = new LinkedHashMap<String, String[]>();
		values.put("value1", new String[] { "123" });
		values.put("value2", new String[] { "456" });
		values.put("value3", new String[] { "789" });
		final Redirect redirect = new Redirect(MockAction.class, "dummy2",
				values);
		assertEquals("/mock/dummy2/123/456?value3=789", redirect
				.getPath("UTF-8"));
	}

	@Test
	public void redirectByClassAndMethod5() throws Exception {
		final Redirect redirect1 = new Redirect(MockAction.class, "index");
		assertEquals("/mock/", redirect1.getPath("UTF-8"));
		final Redirect redirect2 = new Redirect(MockAction.class);
		assertEquals("/mock/", redirect2.getPath("UTF-8"));
	}

	@Test
	public void redirectByClassAndMethodFailureNoRouting() throws Exception {
		try {
			new Redirect(MockAction.class, "none").getPath("UTF-8");
			fail();
		} catch (final RoutingException e) {
			// ok
		}
	}

	@Test
	public void redirectByClassAndMethodFailureLessParameter() throws Exception {
		try {
			new Redirect(MockAction.class, "dummy2").getPath("UTF-8");
			fail();
		} catch (final RoutingException e) {
			// ok
		}
	}

	@Test
	public void redirectByClassAndMethodFailureUnmatchParameter()
			throws Exception {
		final Map<String, String[]> values = new LinkedHashMap<String, String[]>();
		values.put("value1", new String[] { "abc" });
		values.put("value2", new String[] { "456" });
		try {
			new Redirect(MockAction.class, "dummy2", values).getPath("UTF-8");
			fail();
		} catch (final RoutingException e) {
			// ok
		}
	}

	@Test
	public void getPath() throws Exception {
		final Redirect redirect = new Redirect("/absolute/redirect");
		assertEquals("/absolute/redirect", redirect.getPath("UTF-8"));
	}

	@Test
	public void param1() throws Exception {
		final Redirect redirect = new Redirect(MockAction.class, "dummy1")
				.param("value1", "123").param("value2", "456");
		assertEquals("/mock/dummy1?value1=123&value2=456", redirect
				.getPath("UTF-8"));
	}

	@Test
	public void pParam2() throws Exception {
		Map<String, String[]> params = new LinkedHashMap<String, String[]>();
		params.put("value1", new String[] { "123" });
		final Redirect redirect = new Redirect(MockAction.class, "dummy1",
				params).param("value2", "456");
		assertEquals("/mock/dummy1?value1=123&value2=456", redirect
				.getPath("UTF-8"));
	}

	@Test
	public void param3() throws Exception {
		Redirect redirect = new Redirect("hoge").param("value1", "123").param(
				"value2", "456");
		assertEquals("hoge?value1=123&value2=456", redirect.getPath("UTF-8"));
	}

	interface Asserter {
		void assertDispatchPath(String path);
	}

	class RequestDispatcherAssertionWrapper extends HttpServletResponseWrapper {

		private final Asserter asserter;

		public RequestDispatcherAssertionWrapper(
				final HttpServletResponse response, final Asserter asserter) {
			super(response);
			this.asserter = asserter;
		}

		@Override
		public void sendRedirect(final String location) throws IOException {
			asserter.assertDispatchPath(location);
			super.sendRedirect(location);
		}

	}

}
