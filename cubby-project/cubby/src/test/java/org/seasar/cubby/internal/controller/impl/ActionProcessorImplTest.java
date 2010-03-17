/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.internal.controller.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionClass;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.FlashMap;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.container.Container;

public class ActionProcessorImplTest {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	FooAction action = new FooAction();

	@Before
	public void setup() {
		BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(final Class<T> type) {
						if (FooAction.class.equals(type)) {
							return type.cast(action);
						}

						return null;
					}

				}));
		pluginRegistry.register(binderPlugin);

	}

	@Test
	@SuppressWarnings("unchecked")
	public void constructor() throws Throwable {
		Class<?> actionClass = action.getClass();
		Method actionMethod = actionClass.getMethod("foo");

		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getRequestURI()).andReturn("http://example.com/ctx/foo");
		request.setAttribute(eq(CubbyConstants.ATTR_ACTION), eq(action));
		request.setAttribute(eq(CubbyConstants.ATTR_ACTION_CONTEXT),
				isA(ActionContext.class));
		expect(request.getAttribute(CubbyConstants.ATTR_ERRORS))
				.andReturn(null);
		request.setAttribute(eq(CubbyConstants.ATTR_ERRORS),
				isA(ActionErrors.class));
		expect(request.getAttribute(CubbyConstants.ATTR_FLASH)).andReturn(null);
		request
				.setAttribute(eq(CubbyConstants.ATTR_FLASH),
						isA(FlashMap.class));
		expect(request.getAttribute(CubbyConstants.ATTR_WRAPEE_REQUEST))
				.andReturn(request);
		request.setAttribute("assert", "ok");
		expect(request.getSession(false)).andReturn(null);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		Routing routing = createMock(Routing.class);
		expect(routing.getActionClass()).andReturn((Class) actionClass);
		expect(routing.getActionMethod()).andReturn(actionMethod);
		replay(request, response, routing);

		ActionProcessor actionProcessor = new ActionProcessorImpl();
		ActionResultWrapper actionResultWrapper = actionProcessor.process(
				request, response, routing);
		actionResultWrapper.execute(request, response);

		verify(request, response, routing);
	}

	@ActionClass
	public static class FooAction {
		public ActionResult foo() {
			return new ActionResult() {

				public void execute(ActionContext actionContext,
						HttpServletRequest request, HttpServletResponse response)
						throws Exception {
					request.setAttribute("assert", "ok");
				}
			};
		}
	}

}
