package org.seasar.cubby.guice_examples.action;

import org.junit.Test;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.guice_examples.ApplicationModule;
import org.seasar.cubby.plugins.guice.GuicePlugin;
import org.seasar.cubby.unit.CubbyAssert;
import org.seasar.cubby.unit.CubbyRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

import com.google.inject.servlet.GuiceFilter;

public class HelloActionTest {

	@Test
	public void index1() throws Exception {
		MockServletContext servletContext = new MockServletContext();
		servletContext.addInitParameter(GuicePlugin.MODULE_INIT_PARAM_NAME,
				ApplicationModule.class.getName());
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setServletPath("/hello/");
		MockHttpServletResponse response = new MockHttpServletResponse();

		ActionResult actualResult = CubbyRunner.processAction(servletContext,
				request, response, new GuiceFilter());
		CubbyAssert.assertPathEquals(Forward.class, "index.jsp", actualResult);
	}

	@Test
	public void message1() throws Exception {
		MockServletContext servletContext = new MockServletContext();
		servletContext.addInitParameter(GuicePlugin.MODULE_INIT_PARAM_NAME,
				ApplicationModule.class.getName());
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setServletPath("/hello/message");
		MockHttpServletResponse response = new MockHttpServletResponse();

		ActionResult actualResult = CubbyRunner.processAction(servletContext,
				request, response, new GuiceFilter());
		CubbyAssert.assertPathEquals(Forward.class, "index.jsp", actualResult);

	}

	@Test
	public void message2() throws Exception {
		MockServletContext servletContext = new MockServletContext();
		servletContext.addInitParameter(GuicePlugin.MODULE_INIT_PARAM_NAME,
				ApplicationModule.class.getName());
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setServletPath("/hello/message");
		request.addParameter("name", "cubby");
		MockHttpServletResponse response = new MockHttpServletResponse();

		ActionResult actualResult = CubbyRunner.processAction(servletContext,
				request, response, new GuiceFilter());
		CubbyAssert.assertPathEquals(Forward.class, "hello.jsp", actualResult);
	}

}