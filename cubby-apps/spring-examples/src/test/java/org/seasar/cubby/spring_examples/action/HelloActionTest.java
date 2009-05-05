package org.seasar.cubby.spring_examples.action;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.unit.CubbyAssert;
import org.seasar.cubby.unit.CubbyRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.filter.RequestContextFilter;

public class HelloActionTest {

	private MockServletContext servletContext;

	@Before
	public void before() throws Exception {
		servletContext = new MockServletContext();
		servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,
				"/applicationContext.xml");
		ContextLoader contextLoader = new ContextLoader();
		contextLoader.initWebApplicationContext(servletContext);
	}

	@Test
	public void index1() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setServletPath("/hello/");
		MockHttpServletResponse response = new MockHttpServletResponse();

		ActionResult actualResult = CubbyRunner.processAction(servletContext,
				request, response, new RequestContextFilter());
		CubbyAssert.assertPathEquals(Forward.class, "index.jsp", actualResult);
	}

}
