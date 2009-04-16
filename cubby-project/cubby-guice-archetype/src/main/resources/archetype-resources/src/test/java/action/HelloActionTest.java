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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.guice.GuicePlugin;
import org.seasar.cubby.unit.CubbyAssert;
import org.seasar.cubby.unit.CubbyRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.inject.Guice;
#if ($use-guice-servlet.matches("(?i)y|yes|true|on"))
import com.google.inject.servlet.GuiceFilter;
#end

import ${package}.ApplicationModule;

public class HelloActionTest {

	private PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	@Before
	public void before() {
		GuicePlugin guicePlugin = new GuicePlugin();
		guicePlugin.setInjector(Guice.createInjector(new ApplicationModule()));
		pluginRegistry.register(guicePlugin);
	}

	@After
	public void after() {
		pluginRegistry.clear();
	}

	@Test
	public void index1() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setServletPath("/hello/");
		MockHttpServletResponse response = new MockHttpServletResponse();

#if ($use-guice-servlet.matches("(?i)y|yes|true|on"))
		ActionResult actualResult = CubbyRunner.processAction(request,
				response, new GuiceFilter());
#else
		ActionResult actualResult = CubbyRunner.processAction(request,
				response);
#end
		CubbyAssert.assertPathEquals(Forward.class, "index.jsp", actualResult);
	}

	@Test
	public void message1() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setServletPath("/hello/message");
		MockHttpServletResponse response = new MockHttpServletResponse();

#if ($use-guice-servlet.matches("(?i)y|yes|true|on"))
		ActionResult actualResult = CubbyRunner.processAction(request,
				response, new GuiceFilter());
#else
		ActionResult actualResult = CubbyRunner.processAction(request,
				response);
#end
		CubbyAssert.assertPathEquals(Forward.class, "index.jsp", actualResult);

	}

	@Test
	public void message2() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setServletPath("/hello/message");
		request.addParameter("name", "cubby");
		MockHttpServletResponse response = new MockHttpServletResponse();

#if ($use-guice-servlet.matches("(?i)y|yes|true|on"))
		ActionResult actualResult = CubbyRunner.processAction(request,
				response, new GuiceFilter());
#else
		ActionResult actualResult = CubbyRunner.processAction(request,
				response);
#end
		CubbyAssert.assertPathEquals(Forward.class, "hello.jsp", actualResult);
	}

}
