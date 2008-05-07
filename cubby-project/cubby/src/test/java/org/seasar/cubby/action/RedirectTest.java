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

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.seasar.cubby.exception.ActionRuntimeException;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.mock.servlet.MockServletContext;
import org.seasar.framework.util.ClassUtil;

public class RedirectTest extends S2TestCase {

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

		Redirect redirect = new Redirect("path.jsp");
		redirect.prerender(action);
		assertFalse(action.isPrerendered());
		redirect.execute(action, MockAction.class, method, request,
				new RequestDispatcherAssertionWrapper(response, new Asserter() {
					public void assertDispatchPath(String path) {
						assertEquals("/cubby/mock/path.jsp", path);
					}
				}));
		assertFalse(action.isPostrendered());
	}

	public void testRelativePath() throws Exception {
		MockServletContext servletContext = this.getServletContext();
		servletContext.setServletContextName("/cubby");
		MockHttpServletRequest request = this.getRequest();
		MockHttpServletResponse response = this.getResponse();
		Method method = ClassUtil.getMethod(action.getClass(), "dummy1", null);

		Redirect redirect = new Redirect("page.jsp");
		redirect.execute(action, MockAction.class, method, request,
				new RequestDispatcherAssertionWrapper(response, new Asserter() {
					public void assertDispatchPath(String path) {
						assertEquals("/cubby/mock/page.jsp", path);
					}
				}));
	}

	public void testAbsolutePath() throws Exception {
		MockServletContext servletContext = this.getServletContext();
		servletContext.setServletContextName("/cubby");
		MockHttpServletRequest request = this.getRequest();
		MockHttpServletResponse response = this.getResponse();
		Method method = ClassUtil.getMethod(action.getClass(), "dummy1", null);

		Redirect redirect = new Redirect("/absolute/path.jsp");
		redirect.execute(action, MockAction.class, method, request,
				new RequestDispatcherAssertionWrapper(response, new Asserter() {
					public void assertDispatchPath(String path) {
						assertEquals("/cubby/absolute/path.jsp", path);
					}
				}));
	}

	public void testRootContextPath() throws Exception {
		MockServletContext servletContext = this.getServletContext();
		servletContext.setServletContextName("/");
		MockHttpServletRequest request = this.getRequest();
		MockHttpServletResponse response = this.getResponse();
		Method method = ClassUtil.getMethod(action.getClass(), "dummy1", null);

		Redirect redirect = new Redirect("path.jsp");
		redirect.execute(action, MockAction.class, method, request,
				new RequestDispatcherAssertionWrapper(response, new Asserter() {
					public void assertDispatchPath(String path) {
						assertEquals("/mock/path.jsp", path);
					}
				}));
	}

	public void testRedirectByClassAndMethod1() throws Exception {
		Redirect redirect = new Redirect(MockAction.class, "dummy1");
		assertEquals("/routing/test", redirect.getPath());
	}

	public void testRedirectByClassAndMethod2() throws Exception {
		Map<String, String[]> values = new LinkedHashMap<String, String[]>();
		values.put("value1", new String[] { "123" });
		values.put("value2", new String[] { "456" });

		Redirect redirect = new Redirect(MockAction.class, "dummy1", values);
		assertEquals("/routing/test?value1=123&value2=456", redirect.getPath());
	}

	public void testRedirectByClassAndMethod3() throws Exception {
		Map<String, String[]> values = new LinkedHashMap<String, String[]>();
		values.put("value1", new String[] { "123" });
		values.put("value2", new String[] { "456" });
		Redirect redirect = new Redirect(MockAction.class, "dummy2", values);
		assertEquals("/routing/test/123/456", redirect.getPath());
	}

	public void testRedirectByClassAndMethod4() throws Exception {
		Map<String, String[]> values = new LinkedHashMap<String, String[]>();
		values.put("value1", new String[] { "123" });
		values.put("value2", new String[] { "456" });
		values.put("value3", new String[] { "789" });
		Redirect redirect = new Redirect(MockAction.class, "dummy2", values);
		assertEquals("/routing/test/123/456?value3=789", redirect.getPath());
	}

	public void testRedirectByClassAndMethodFailureNoRouting() throws Exception {
		try {
			new Redirect(MockAction.class, "none");
			fail();
		} catch (ActionRuntimeException e) {
			// ok
		}
	}

	public void testRedirectByClassAndMethodFailureLessParameter()
			throws Exception {
		try {
			new Redirect(MockAction.class, "dummy2");
			fail();
		} catch (ActionRuntimeException e) {
			// ok
		}
	}

	public void testRedirectByClassAndMethodFailureUnmatchParameter()
			throws Exception {
		Map<String, String[]> values = new LinkedHashMap<String, String[]>();
		values.put("value1", new String[] { "abc" });
		values.put("value2", new String[] { "456" });
		try {
			new Redirect(MockAction.class, "dummy2", values);
			fail();
		} catch (ActionRuntimeException e) {
			// ok
		}
	}

	public void testGetPath() throws Exception {
		Redirect redirect = new Redirect("/absolute/redirect");
		assertEquals("/absolute/redirect", redirect.getPath());
	}

	interface Asserter {
		void assertDispatchPath(String path);
	}

	class RequestDispatcherAssertionWrapper extends HttpServletResponseWrapper {

		private Asserter asserter;

		public RequestDispatcherAssertionWrapper(HttpServletResponse response,
				Asserter asserter) {
			super(response);
			this.asserter = asserter;
		}

		@Override
		public void sendRedirect(String location) throws IOException {
			asserter.assertDispatchPath(location);
			super.sendRedirect(location);
		}

	}

}
