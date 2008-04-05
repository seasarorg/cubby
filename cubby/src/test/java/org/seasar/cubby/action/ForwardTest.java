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

import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.seasar.cubby.util.CubbyUtils;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.mock.servlet.MockServletContext;
import org.seasar.framework.util.ClassUtil;

public class ForwardTest extends S2TestCase {

	public MockAction action;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testBasicSequence() throws Exception {
		MockServletContext servletContext = this.getServletContext();
		servletContext.setServletContextName("/cubby");
		MockHttpServletRequest request = this.getRequest();
		MockHttpServletResponse response = this.getResponse();
		Method method = ClassUtil.getMethod(action.getClass(), "dummy1", null);

		Forward forward = new Forward("path.jsp");
		forward.prerender(action);
		assertTrue(action.isPrerendered());
		forward.execute(action, MockAction.class, method,
				new RequestDispatcherAssertionWrapper(request, new Asserter() {
					public void assertDispatchPath(String path) {
						assertEquals("/mock/path.jsp", path);
					}
				}), response);
		assertTrue(action.isPostrendered());
	}

	public void testRelativePath() throws Exception {
		MockServletContext servletContext = this.getServletContext();
		servletContext.setServletContextName("/cubby");
		MockHttpServletRequest request = this.getRequest();
		MockHttpServletResponse response = this.getResponse();
		Method method = ClassUtil.getMethod(action.getClass(), "dummy1", null);

		Forward forward = new Forward("page.jsp");
		forward.execute(action, MockAction.class, method,
				new RequestDispatcherAssertionWrapper(request, new Asserter() {
					public void assertDispatchPath(String path) {
						assertEquals("/mock/page.jsp", path);
					}
				}), response);
	}

	public void testAbsolutePath() throws Exception {
		MockServletContext servletContext = this.getServletContext();
		servletContext.setServletContextName("/cubby");
		MockHttpServletRequest request = this.getRequest();
		MockHttpServletResponse response = this.getResponse();
		Method method = ClassUtil.getMethod(action.getClass(), "dummy1", null);

		Forward forward = new Forward("/absolute/path.jsp");
		forward.execute(action, MockAction.class, method,
				new RequestDispatcherAssertionWrapper(request, new Asserter() {
					public void assertDispatchPath(String path) {
						assertEquals("/absolute/path.jsp", path);
					}
				}), response);
	}

	public void testGetPath() throws Exception {
		Forward forward = new Forward("/absolute/path.jsp");
		assertEquals("/absolute/path.jsp", forward.getPath());
	}

	public void testForwardByClassAndMethodName() {
		Forward forward = new Forward(MockAction.class, "dummy1");
		String expect = CubbyUtils.getInternalForwardPath(MockAction.class,
				"dummy1");
		assertEquals(expect, forward.getPath());
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
