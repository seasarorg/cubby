package org.seasar.cubby.action;

import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.mock.servlet.MockServletContext;
import org.seasar.framework.util.ClassUtil;

public class ForwardTest extends S2TestCase {

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

		Forward forward = new Forward("path.jsp");
		forward.prerender(context);
		assertTrue(action.isPrerendered());
		forward.execute(context, new RequestDispatcherAssertionWrapper(request,
				new Asserter() {
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
		ComponentDef componentDef = this.getComponentDef(MockAction.class);
		context.initialize(new MockActionDef(componentDef));

		Forward forward = new Forward("page.jsp");
		forward.execute(context, new RequestDispatcherAssertionWrapper(request,
				new Asserter() {
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
		ComponentDef componentDef = this.getComponentDef(MockAction.class);
		context.initialize(new MockActionDef(componentDef));

		Forward forward = new Forward("/absolute/path.jsp");
		forward.execute(context, new RequestDispatcherAssertionWrapper(request,
				new Asserter() {
					public void assertDispatchPath(String path) {
						assertEquals("/absolute/path.jsp", path);
					}
				}), response);
	}
	
	public void testGetPath() throws Exception {
		Forward forward = new Forward("/absolute/path.jsp");
		assertEquals("/absolute/path.jsp", forward.getPath());
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

	class MockActionDef implements ActionDef {

		private ComponentDef componentDef;

		public MockActionDef(ComponentDef componentDef) {
			this.componentDef = componentDef;
		}

		public ComponentDef getComponentDef() {
			return componentDef;
		}

		public Method getMethod() {
			Class<?> clazz = componentDef.getComponentClass();
			Method method = ClassUtil.getMethod(clazz, "dummy", null);
			return method;
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

		public ActionResult dummy() {
			return null;
		}
	}

}
