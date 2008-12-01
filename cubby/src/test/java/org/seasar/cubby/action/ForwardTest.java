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
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.container.LookupException;
import org.seasar.cubby.internal.factory.PathResolverFactory;
import org.seasar.cubby.internal.routing.PathResolver;
import org.seasar.cubby.internal.routing.Routing;
import org.seasar.cubby.internal.routing.impl.PathResolverImpl;
import org.seasar.cubby.mock.MockActionContext;
import org.seasar.cubby.mock.MockContainerProvider;

public class ForwardTest {

	private MockAction action = new MockAction();

	private HttpServletRequest request;

	private RequestDispatcher requestDispatcher;

	private HttpServletResponse response;

	@Before
	public void setupContainer() {
		final List<Class<?>> actionClasses = new ArrayList<Class<?>>();
		actionClasses.add(MockAction.class);
		final PathResolver pathResolver = new PathResolverImpl();
		pathResolver.addAllActionClasses(actionClasses);
		final PathResolverFactory pathResolverFactory = new PathResolverFactory() {

			public PathResolver getPathResolver() {
				return pathResolver;
			}

		};
		MockContainerProvider.setContainer(new Container() {

			public <T> T lookup(Class<T> type) {
				if (PathResolverFactory.class.equals(type)) {
					return type.cast(pathResolverFactory);
				}
				throw new LookupException();
			}

		});
	}

	@Before
	public void setupRequest() {
		request = createMock(HttpServletRequest.class);
		expect(request.getCharacterEncoding()).andReturn("UTF-8").anyTimes();
		requestDispatcher = createMock(RequestDispatcher.class);
		response = createMock(HttpServletResponse.class);
	}

	@Test
	public void basicSequence() throws Exception {
		Method method = action.getClass().getMethod("dummy1");
		final MockActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getRequestDispatcher("/mock/path.jsp")).andStubAnswer(
				new IAnswer<RequestDispatcher>() {

					public RequestDispatcher answer() throws Throwable {
						assertTrue(actionContext.isPrerendered());
						return requestDispatcher;
					}

				});
		requestDispatcher.forward(request, response);
		expectLastCall();
		replay(request, requestDispatcher, response);

		Forward forward = new Forward("path.jsp");
		forward.execute(actionContext, request, response);
		assertTrue(actionContext.isPostrendered());
		verify(request, requestDispatcher, response);
	}

	@Test
	public void relativePath() throws Exception {
		Method method = action.getClass().getMethod("dummy1");
		final MockActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getRequestDispatcher("/mock/page.jsp")).andReturn(
				requestDispatcher);
		requestDispatcher.forward(request, response);
		expectLastCall();
		replay(request, requestDispatcher, response);

		Forward forward = new Forward("page.jsp");
		forward.execute(actionContext, request, response);
		verify(request, requestDispatcher, response);
	}

	@Test
	public void absolutePath() throws Exception {
		Method method = action.getClass().getMethod("dummy1");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		expect(request.getRequestDispatcher("/absolute/path.jsp")).andReturn(
				requestDispatcher);
		requestDispatcher.forward(request, response);
		expectLastCall();
		replay(request, requestDispatcher, response);

		Forward forward = new Forward("/absolute/path.jsp");
		forward.execute(actionContext, request, response);
		verify(request, requestDispatcher, response);
	}

	@Test
	public void getPath() throws Exception {
		Forward forward = new Forward("/absolute/path.jsp");
		assertEquals("/absolute/path.jsp", forward.getPath("UTF-8"));
	}

	@Test
	public void param() throws Exception {
		Forward forward = new Forward("/absolute/path.jsp").param("value1",
				"123").param("value2", "456");
		assertEquals("/absolute/path.jsp?value1=123&value2=456", forward
				.getPath("UTF-8"));
	}

	@Test
	public void forwardByClassAndMethodName() throws Exception {
		Method method = action.getClass().getMethod("dummy1");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		request.setAttribute(eq(CubbyConstants.ATTR_ROUTING),
				isA(Routing.class));
		expectLastCall().andAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				Routing routing = Routing.class.cast(getCurrentArguments()[1]);
				assertNotNull(routing);
				assertEquals(MockAction.class, routing.getActionClass());
				Method forwardMethod = action.getClass().getMethod("dummy2");
				assertEquals(forwardMethod, routing.getMethod());
				return null;
			}

		});
		expect(request.getRequestDispatcher("/mock/dummy2/5/abc")).andReturn(
				requestDispatcher);
		requestDispatcher.forward(request, response);
		expectLastCall();
		replay(request, requestDispatcher, response);

		Forward forward = new Forward(MockAction.class, "dummy2").param(
				"value1", "5").param("value2", "abc");
		forward.execute(actionContext, request, response);
	}

	@Test
	public void forwardByClassAndIndex() throws Exception {
		Method method = action.getClass().getMethod("dummy1");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		request.setAttribute(eq(CubbyConstants.ATTR_ROUTING),
				isA(Routing.class));
		expectLastCall().andAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				Routing routing = Routing.class.cast(getCurrentArguments()[1]);
				assertNotNull(routing);
				assertEquals(MockAction.class, routing.getActionClass());
				Method forwardMethod = action.getClass().getMethod("index");
				assertEquals(forwardMethod, routing.getMethod());
				return null;
			}

		});
		expect(request.getRequestDispatcher("/mock/")).andReturn(
				requestDispatcher);
		requestDispatcher.forward(request, response);
		expectLastCall();
		replay(request, requestDispatcher, response);

		Forward forward = new Forward(MockAction.class);
		forward.execute(actionContext, request, response);
	}

	@Test
	public void forwardByClassAndMethodNameWithParam() throws Exception {
		Method method = action.getClass().getMethod("dummy1");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		request.setAttribute(eq(CubbyConstants.ATTR_ROUTING),
				isA(Routing.class));
		expectLastCall().andAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				Routing routing = Routing.class.cast(getCurrentArguments()[1]);
				assertNotNull(routing);
				assertEquals(MockAction.class, routing.getActionClass());
				Method forwardMethod = action.getClass().getMethod("dummy2");
				assertEquals(forwardMethod, routing.getMethod());
				return null;
			}

		});
		expect(request.getRequestDispatcher("/mock/dummy2/123/456")).andReturn(
				requestDispatcher);
		requestDispatcher.forward(request, response);
		expectLastCall();
		replay(request, requestDispatcher, response);

		Forward forward = new Forward(MockAction.class, "dummy2").param(
				"value1", "123").param("value2", "456");
		forward.execute(actionContext, request, response);
	}

	interface Asserter {
		void assertDispatchPath(String path);
	}

	class RequestDispatcherAssertionWrapper extends HttpServletRequestWrapper {

		private Asserter asserter;

		public RequestDispatcherAssertionWrapper(HttpServletRequest request,
				Asserter asserter) {
			super(request);
			this.asserter = asserter;
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			asserter.assertDispatchPath(path);
			return super.getRequestDispatcher(path);
		}

	}

}
