package org.seasar.cubby.examples.spring.action;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.examples.spring.action.HelloAction;
import org.seasar.cubby.unit.CubbyAssert;
import org.seasar.cubby.unit.CubbyRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.filter.RequestContextFilter;

/**
 * {@link HelloAction} のテスト
 * 
 * @author suzuki-kei
 * @author someda
 * 
 */
public class HelloActionTest {

	private MockServletContext servletContext;

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	@Before
	public void before() throws Exception {
		servletContext = new MockServletContext();
		servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,
				"/applicationContext.xml");
		ContextLoader contextLoader = new ContextLoader();
		contextLoader.initWebApplicationContext(servletContext);

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Test
	public void index1() throws Exception {
		request.setMethod("GET");
		request.setServletPath("/hello/");
		ActionResult actualResult = CubbyRunner.processAction(servletContext,
				request, response, new RequestContextFilter());
		CubbyAssert.assertPathEquals(Forward.class, "index.jsp", actualResult);
	}

	@Test
	public void message1() throws Exception {
		request.setMethod("GET");
		request.setServletPath("/hello/message");
		ActionResult actualResult = CubbyRunner.processAction(servletContext,
				request, response, new RequestContextFilter());
		CubbyAssert.assertPathEquals(Forward.class, "index.jsp", actualResult);

	}

	@Test
	public void message2() throws Exception {
		request.setMethod("GET");
		request.setServletPath("/hello/message");
		request.addParameter("name", "cubby");
		ActionResult actualResult = CubbyRunner.processAction(servletContext,
				request, response, new RequestContextFilter());
		CubbyAssert.assertPathEquals(Forward.class, "hello.jsp", actualResult);
	}

}
