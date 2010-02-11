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

package org.seasar.cubby.unit;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.IAnswer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.impl.DefaultRequestParser;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.routing.impl.PathTemplateParserImpl;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.RequestParserProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;
import org.seasar.cubby.spi.impl.AbstractRequestParserProvider;

public class CubbyRunnerTest {

	private PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	private final Hashtable<String, Object> attributes = new Hashtable<String, Object>();

	private final Map<String, String[]> parameterMap = new HashMap<String, String[]>();

	private MockAction mockAction = new MockAction();

	@Before
	public void before() {
		PathResolver pathResolver = new PathResolverImpl(
				new PathTemplateParserImpl());
		pathResolver.add(MockAction.class);

		BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(RequestParserProvider.class).toInstance(
				new AbstractRequestParserProvider() {

					@Override
					protected Collection<RequestParser> getRequestParsers() {
						return Arrays
								.asList(new RequestParser[]{new DefaultRequestParser()});
					}

				});
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) throws LookupException {
						if (MockAction.class.equals(type)) {
							return type.cast(mockAction);
						}
						throw new LookupException("type:" + type);
					}

				}));
		binderPlugin.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
		binderPlugin.bind(ConverterProvider.class).toInstance(
				new MockConverterProvider());
		binderPlugin.bind(PathResolverProvider.class).toInstance(
				new MockPathResolverProvider(pathResolver));
		pluginRegistry.register(binderPlugin);
	}

	@After
	public void after() {
		pluginRegistry.clear();
	}

	@Test
	public void processAction() throws Exception {
		parameterMap.put("name", new String[]{"abcdefg"});

		HttpServletRequest request = createMock(HttpServletRequest.class);
		request.setAttribute(isA(String.class), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Void>() {

			public Void answer() throws Throwable {
				attributes.put((String) getCurrentArguments()[0],
						getCurrentArguments()[1]);
				return null;
			}

		});
		expect(request.getAttribute(isA(String.class))).andStubAnswer(
				new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return attributes.get(getCurrentArguments()[0]);
					}

				});
		request.removeAttribute(isA(String.class));
		expectLastCall().andStubAnswer(new IAnswer<Void>() {

			public Void answer() throws Throwable {
				attributes.remove(getCurrentArguments()[0]);
				return null;
			}

		});
		expect(request.getServletPath()).andReturn("/mock/execute");
		expect(request.getPathInfo()).andReturn(null);
		expect(request.getParameterMap()).andReturn(parameterMap);
		expect(request.getRequestURI()).andReturn("/context/mock/execute");
		expect(request.getMethod()).andReturn("GET");
		expect(request.getCharacterEncoding()).andReturn("UTF-8");
		expect(request.getSession(false)).andReturn(null);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionResult actionResult = createMock(ActionResult.class);
		replay(request, response, actionResult);

		mockAction.setActionResult(actionResult);
		ActionResult actual = CubbyRunner.processAction(request, response);
		assertSame(actionResult, actual);
		assertEquals("abcdefg", mockAction.getName());

		verify(request, response, actionResult);
	}

	@Test
	public void processActionWithServletContext() throws Exception {
		parameterMap.put("name", new String[]{"abcdefg"});

		HttpServletRequest request = createMock(HttpServletRequest.class);
		request.setAttribute(isA(String.class), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Void>() {

			public Void answer() throws Throwable {
				attributes.put((String) getCurrentArguments()[0],
						getCurrentArguments()[1]);
				return null;
			}

		});
		expect(request.getAttribute(isA(String.class))).andStubAnswer(
				new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return attributes.get(getCurrentArguments()[0]);
					}

				});
		request.removeAttribute(isA(String.class));
		expectLastCall().andStubAnswer(new IAnswer<Void>() {

			public Void answer() throws Throwable {
				attributes.remove(getCurrentArguments()[0]);
				return null;
			}

		});
		expect(request.getServletPath()).andReturn("/mock/execute");
		expect(request.getPathInfo()).andReturn(null);
		expect(request.getParameterMap()).andReturn(parameterMap);
		expect(request.getRequestURI()).andReturn("/context/mock/execute");
		expect(request.getMethod()).andReturn("GET");
		expect(request.getCharacterEncoding()).andReturn("UTF-8");
		expect(request.getSession(false)).andReturn(null);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ServletContext servletContext = createMock(ServletContext.class);
		ActionResult actionResult = createMock(ActionResult.class);
		replay(request, response, servletContext, actionResult);

		mockAction.setActionResult(actionResult);
		ActionResult actual = CubbyRunner.processAction(servletContext,
				request, response);
		assertSame(actionResult, actual);
		assertEquals("abcdefg", mockAction.getName());

		verify(request, response, servletContext, actionResult);
	}

	@Test
	public void processActionAndExecuteActionResult() throws Exception {
		parameterMap.put("name", new String[] { "abcdefg" });

		HttpServletRequest request = createMock(HttpServletRequest.class);
		request.setAttribute(isA(String.class), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Void>() {

			public Void answer() throws Throwable {
				attributes.put((String) getCurrentArguments()[0],
						getCurrentArguments()[1]);
				return null;
			}

		});
		expect(request.getAttribute(isA(String.class))).andStubAnswer(
				new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return attributes.get(getCurrentArguments()[0]);
					}

				});
		request.removeAttribute(isA(String.class));
		expectLastCall().andStubAnswer(new IAnswer<Void>() {

			public Void answer() throws Throwable {
				attributes.remove(getCurrentArguments()[0]);
				return null;
			}

		});
		expect(request.getServletPath()).andReturn("/mock/execute");
		expect(request.getPathInfo()).andReturn(null);
		expect(request.getParameterMap()).andReturn(parameterMap);
		expect(request.getRequestURI()).andReturn("/context/mock/execute");
		expect(request.getMethod()).andReturn("GET");
		expect(request.getCharacterEncoding()).andReturn("UTF-8");
		expect(request.getSession(false)).andReturn(null);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionResult actionResult = createMock(ActionResult.class);
		actionResult.execute(isA(ActionContext.class),
				isA(HttpServletRequest.class), isA(HttpServletResponse.class));
		replay(request, response, actionResult);

		mockAction.setActionResult(actionResult);
		ActionResult actual = CubbyRunner.processActionAndExecuteActionResult(
				request, response);

		assertSame(actionResult, actual);
		assertEquals("abcdefg", mockAction.getName());

		verify(request, response, actionResult);
	}

	@Test
	public void processActionAndExecuteActionResultWithServletContext()
			throws Exception {
		parameterMap.put("name", new String[] { "abcdefg" });

		HttpServletRequest request = createMock(HttpServletRequest.class);
		request.setAttribute(isA(String.class), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Void>() {

			public Void answer() throws Throwable {
				attributes.put((String) getCurrentArguments()[0],
						getCurrentArguments()[1]);
				return null;
			}

		});
		expect(request.getAttribute(isA(String.class))).andStubAnswer(
				new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return attributes.get(getCurrentArguments()[0]);
					}

				});
		request.removeAttribute(isA(String.class));
		expectLastCall().andStubAnswer(new IAnswer<Void>() {

			public Void answer() throws Throwable {
				attributes.remove(getCurrentArguments()[0]);
				return null;
			}

		});
		expect(request.getServletPath()).andReturn("/mock/execute");
		expect(request.getPathInfo()).andReturn(null);
		expect(request.getParameterMap()).andReturn(parameterMap);
		expect(request.getRequestURI()).andReturn("/context/mock/execute");
		expect(request.getMethod()).andReturn("GET");
		expect(request.getCharacterEncoding()).andReturn("UTF-8");
		expect(request.getSession(false)).andReturn(null);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ServletContext servletContext = createMock(ServletContext.class);
		ActionResult actionResult = createMock(ActionResult.class);
		actionResult.execute(isA(ActionContext.class),
				isA(HttpServletRequest.class), isA(HttpServletResponse.class));
		replay(request, response, servletContext, actionResult);

		mockAction.setActionResult(actionResult);
		ActionResult actual = CubbyRunner.processActionAndExecuteActionResult(
				servletContext, request, response);
		assertSame(actionResult, actual);
		assertEquals("abcdefg", mockAction.getName());

		verify(request, response, servletContext, actionResult);
	}

	public static class MockAction {
		private String name;

		private ActionResult actionResult;

		@RequestParameter
		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setActionResult(ActionResult actionResult) {
			this.actionResult = actionResult;
		}

		public ActionResult execute() {
			return actionResult;
		}
	}

}
