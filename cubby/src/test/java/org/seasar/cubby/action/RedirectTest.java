package org.seasar.cubby.action;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.mock.servlet.MockServletContext;

public class RedirectTest extends S2TestCase {

	public ActionContext context;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testBasicSequence() throws Exception {
		MockServletContext servletContext = this.getServletContext();
		servletContext.setServletContextName("/cubby");
		MockHttpServletRequest request = this.getRequest();
		MockHttpServletResponse response = this.getResponse();
		ComponentDef componentDef = this.getComponentDef(MockAction.class);
		context.initialize(new MockActionDef(componentDef));
		MockAction action = (MockAction) context.getAction();

		Redirect redirect = new Redirect("path.jsp");
		redirect.prerender(context);
		assertFalse(action.isPrerendered());
		redirect.execute(context, request,
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
		ComponentDef componentDef = this.getComponentDef(MockAction.class);
		context.initialize(new MockActionDef(componentDef));

		Redirect redirect = new Redirect("page.jsp");
		redirect.execute(context, request,
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
		ComponentDef componentDef = this.getComponentDef(MockAction.class);
		context.initialize(new MockActionDef(componentDef));

		Redirect redirect = new Redirect("/absolute/path.jsp");
		redirect.execute(context, request, new RequestDispatcherAssertionWrapper(
				response, new Asserter() {
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
		ComponentDef componentDef = this.getComponentDef(MockAction.class);
		context.initialize(new MockActionDef(componentDef));

		Redirect redirect = new Redirect("path.jsp");
		redirect.execute(context, request, new RequestDispatcherAssertionWrapper(
				response, new Asserter() {
					public void assertDispatchPath(String path) {
						assertEquals("/mock/path.jsp", path);
					}
				}));
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

	class MockActionDef implements ActionDef {

		private ComponentDef componentDef;

		public MockActionDef(ComponentDef componentDef) {
			this.componentDef = componentDef;
		}

		public ComponentDef getComponentDef() {
			return componentDef;
		}

		public Method getMethod() {
			return null;
		}

	}

	public static class MockAction extends Action {

		private boolean prerendered = false;

		private boolean postrendered = false;

		@Override
		public void prerender() {
			super.prerender();
			prerendered = true;
		}

		@Override
		public void postrender() {
			super.postrender();
			postrendered = true;
		}

		public boolean isPrerendered() {
			return prerendered;
		}

		public boolean isPostrendered() {
			return postrendered;
		}

	}

}
