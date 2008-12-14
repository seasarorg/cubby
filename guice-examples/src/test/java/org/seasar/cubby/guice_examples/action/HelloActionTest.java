package org.seasar.cubby.guice_examples.action;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.plugins.guice.InjectorFactory;
import org.seasar.cubby.plugins.guice.unit.MockServletModule;
import org.seasar.cubby.unit.CubbyAssert;
import org.seasar.cubby.unit.CubbyRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class HelloActionTest {

	@Before
	public void before() throws Exception {
		InjectorFactory.setModuleClassName(MockExampleModule.class.getName());
	}

	@Test
	public void index1() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setServletPath("/hello/");
		MockHttpServletResponse response = new MockHttpServletResponse();

		MockServletModule.setUpContext(request, response);
		ActionResult actualResult = CubbyRunner
				.processAction(request, response);
		CubbyAssert.assertPathEquals(Forward.class, "index.jsp", actualResult);
	}

	@Test
	public void message1() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setServletPath("/hello/message");
		MockHttpServletResponse response = new MockHttpServletResponse();

		MockServletModule.setUpContext(request, response);
		ActionResult actualResult = CubbyRunner
				.processAction(request, response);
		CubbyAssert.assertPathEquals(Forward.class, "index.jsp", actualResult);

	}

	@Test
	public void message2() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setServletPath("/hello/message");
		request.addParameter("name", "cubby");
		MockHttpServletResponse response = new MockHttpServletResponse();

		MockServletModule.setUpContext(request, response);
		ActionResult actualResult = CubbyRunner
				.processAction(request, response);
		CubbyAssert.assertPathEquals(Forward.class, "hello.jsp", actualResult);
	}

}
