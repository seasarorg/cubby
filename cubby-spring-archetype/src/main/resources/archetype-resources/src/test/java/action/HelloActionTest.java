/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package ${package}.action;

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

/**
 * {@link HelloAction} のテスト
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
