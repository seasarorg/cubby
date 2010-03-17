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

package org.seasar.cubby.filter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.impl.DefaultRequestParser;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.RequestParserProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;
import org.seasar.cubby.spi.impl.AbstractRequestParserProvider;

public class CubbyFilterProcessRequestTest {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	private CubbyFilter cubbyFilter = new CubbyFilter();

	private Object action = new MockAction();

	@Before
	public void setupProvider() {
		final BinderPlugin binderPlugin = new BinderPlugin();

		final List<RequestParser> requestParsers = new ArrayList<RequestParser>();
		requestParsers.add(new DefaultRequestParser());
		binderPlugin.bind(RequestParserProvider.class).toInstance(
				new AbstractRequestParserProvider() {

					@Override
					protected Collection<RequestParser> getRequestParsers() {
						return requestParsers;
					}

				});
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) throws LookupException {
						if (MockAction.class.equals(type)) {
							return type.cast(action);
						}
						throw new LookupException();
					}

				}));
		binderPlugin.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());

		pluginRegistry.register(binderPlugin);
	}

	@After
	public void teardownProvider() {
		pluginRegistry.clear();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void process() throws Exception {
		Map<String, Object[]> parameterMap = new HashMap<String, Object[]>();
		Routing routing = createMock(Routing.class);
		expect(routing.getActionClass()).andReturn(
				Class.class.cast(MockAction.class));
		expect(routing.getActionMethod()).andReturn(
				MockAction.class.getMethod("execute"));
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getParameterMap()).andReturn(parameterMap);
		expect(request.getRequestURI()).andReturn("/context/mock/execute");
		request.setAttribute(CubbyConstants.ATTR_PARAMS, parameterMap);
		expect(request.getAttribute(CubbyConstants.ATTR_ERRORS))
				.andReturn(null);
		request.setAttribute(eq(CubbyConstants.ATTR_ERRORS),
				isA(ActionErrors.class));
		expect(request.getAttribute(CubbyConstants.ATTR_FLASH)).andReturn(null);
		request.setAttribute(eq(CubbyConstants.ATTR_FLASH), isA(Map.class));
		expect(request.getAttribute(CubbyConstants.ATTR_ACTION)).andStubReturn(
				action);
		request.setAttribute(CubbyConstants.ATTR_ACTION, action);
		request.setAttribute(eq(CubbyConstants.ATTR_ACTION_CONTEXT),
				isA(ActionContext.class));
		expect(request.getAttribute(CubbyConstants.ATTR_WRAPEE_REQUEST))
				.andReturn(request);
		expect(request.getSession(false)).andReturn(null);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		PathInfo pathInfo = createMock(PathInfo.class);
		expect(pathInfo.dispatch(parameterMap)).andReturn(routing);
		expect(pathInfo.getURIParameters()).andReturn(
				new HashMap<String, String[]>());
		replay(routing, request, response, pathInfo);

		cubbyFilter.processRequest(request, response, pathInfo);

		verify(routing, request, response, pathInfo);
	}

	public static class MockAction {
		public ActionResult execute() {
			return new Direct();
		}
	}

}
