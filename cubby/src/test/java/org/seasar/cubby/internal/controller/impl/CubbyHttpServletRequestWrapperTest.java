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
package org.seasar.cubby.internal.controller.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.IAnswer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.plugin.BinderPlugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

/**
 * 
 * @author agata
 */
public class CubbyHttpServletRequestWrapperTest {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private Hashtable<String, String[]> parameters = new Hashtable<String, String[]>();

	@Before
	public void setupProvider() {
		final BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
		final MessagesBehaviour messagesBehaviour = new DefaultMessagesBehaviour();
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) throws LookupException {
						if (MessagesBehaviour.class.equals(type)) {
							return type.cast(messagesBehaviour);
						}
						throw new LookupException(type.getName());
					}

				}));
		pluginRegistry.register(binderPlugin);
	}

	@After
	public void teardownProvider() {
		pluginRegistry.clear();
	}

	@Before
	@SuppressWarnings("unchecked")
	public void setupRequest() {
		final Hashtable<String, Object> attributes = new Hashtable<String, Object>();
		request = createMock(HttpServletRequest.class);
		expect(request.getContextPath()).andStubReturn("/context");
		expect(request.getLocale()).andStubReturn(null);
		expect(request.getAttribute(String.class.cast(anyObject())))
				.andStubAnswer(new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return attributes.get(getCurrentArguments()[0]);
					}

				});
		request.setAttribute(String.class.cast(anyObject()), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				attributes.put(String.class.cast(getCurrentArguments()[0]),
						getCurrentArguments()[1]);
				return null;
			}

		});
		expect(request.getAttributeNames()).andStubAnswer(
				new IAnswer<Enumeration>() {

					public Enumeration answer() throws Throwable {
						return attributes.keys();
					}

				});
		expect(request.getParameter(isA(String.class))).andStubAnswer(
				new IAnswer<String>() {

					public String answer() throws Throwable {
						return parameters.get(String.class
								.cast(getCurrentArguments()[0]))[0];
					}

				});
		expect(request.getParameterMap()).andStubAnswer(new IAnswer<Map>() {

			public Map answer() throws Throwable {
				return parameters;
			}

		});
		expect(request.getParameterNames()).andAnswer(
				new IAnswer<Enumeration>() {

					public Enumeration answer() throws Throwable {
						return parameters.keys();
					}

				});
		expect(request.getParameterValues(isA(String.class))).andAnswer(
				new IAnswer<String[]>() {

					public String[] answer() throws Throwable {
						return parameters.get(String.class
								.cast(getCurrentArguments()[0]));
					}

				});
		response = createMock(HttpServletResponse.class);
		replay(request, response);
	}

	@Test
	public void getAttributeNames() throws Exception {
		request.setAttribute("a1", "a1");
		Collection<String> origNames = toCollection(request.getAttributeNames());
		assertTrue("追加済みの属性", origNames.contains("a1"));
		assertFalse("存在しない属性", origNames.contains("a2"));
		assertFalse("ラップ後の追加の属性", origNames
				.contains(CubbyConstants.ATTR_CONTEXT_PATH));
		assertFalse("ラップ後の追加の属性", origNames
				.contains(CubbyConstants.ATTR_MESSAGES));
		assertFalse("ラップ後の追加の属性", origNames
				.contains(CubbyConstants.ATTR_ACTION));

		CubbyHttpServletRequestWrapper wrapper = new CubbyHttpServletRequestWrapper(
				request, new HashMap<String, String[]>());
		Action action = new MockAction();
		wrapper.setAttribute(CubbyConstants.ATTR_ACTION, action);

		Collection<String> wrappedNames = toCollection(wrapper
				.getAttributeNames());
		assertTrue("追加済みの属性", origNames.contains("a1"));
		assertFalse("存在しない属性", origNames.contains("a2"));
		assertTrue("ラップ後の追加の属性", wrappedNames
				.contains(CubbyConstants.ATTR_CONTEXT_PATH));
		assertTrue("ラップ後の追加の属性", wrappedNames
				.contains(CubbyConstants.ATTR_MESSAGES));
		assertTrue("ラップ後の追加の属性", wrappedNames
				.contains(CubbyConstants.ATTR_ACTION));
	}

	private Collection<String> toCollection(Enumeration<?> attributeNames) {
		List<String> names = new ArrayList<String>();
		while (attributeNames.hasMoreElements()) {
			names.add((String) attributeNames.nextElement());
		}
		return names;
	}

	@Test
	public void getAttribute() throws Exception {
		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				CubbyHttpServletRequestWrapper wrapper = new CubbyHttpServletRequestWrapper(
						request, new HashMap<String, String[]>());

				assertEquals("/context", wrapper
						.getAttribute(CubbyConstants.ATTR_CONTEXT_PATH));
				assertEquals(ThreadContext.getMessagesMap(), wrapper
						.getAttribute(CubbyConstants.ATTR_MESSAGES));

				assertNull(wrapper.getAttribute("name"));
				Action action = new MockAction();
				wrapper.setAttribute(CubbyConstants.ATTR_ACTION, action);
				assertSame(action, wrapper
						.getAttribute(CubbyConstants.ATTR_ACTION));
				assertEquals("expect name", wrapper.getAttribute("name"));
				assertNull(wrapper.getAttribute("value"));
				assertNull(wrapper.getAttribute("noprop"));
				return null;
			}

		});
	}

	@Test
	public void parameter() {
		parameters.put("abc", new String[] { "value1" });
		parameters.put("def", new String[] { "value2" });

		Map<String, String[]> uriParameterMap = new HashMap<String, String[]>();
		uriParameterMap.put("abc", new String[] { "value3" });
		uriParameterMap.put("ghi", new String[] { "value4" });

		CubbyHttpServletRequestWrapper wrapper = new CubbyHttpServletRequestWrapper(
				request, uriParameterMap);

		Hashtable<String, String[]> expects = new Hashtable<String, String[]>();
		expects.put("abc", new String[] { "value1", "value3" });
		expects.put("def", new String[] { "value2" });
		expects.put("ghi", new String[] { "value4" });

		@SuppressWarnings("unchecked")
		Enumeration parameterNames = wrapper.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = (String) parameterNames.nextElement();
			assertArrayEquals(expects.get(parameterName), wrapper
					.getParameterValues(parameterName));
			assertEquals(expects.get(parameterName)[0], wrapper
					.getParameter(parameterName));
			expects.remove(parameterName);
		}
		assertTrue(expects.isEmpty());

		assertNull(wrapper.getParameter("jkl"));

		@SuppressWarnings("unchecked")
		Map<String, String[]> parameterMap = wrapper.getParameterMap();
		assertEquals(3, parameterMap.size());
		assertArrayEquals(new String[] { "value1", "value3" }, parameterMap
				.get("abc"));
		assertArrayEquals(new String[] { "value2" }, parameterMap.get("def"));
		assertArrayEquals(new String[] { "value4" }, parameterMap.get("ghi"));

	}

	public class MockAction extends Action {
		public String getName() {
			return "expect name";
		}

		public void setValue(String value) {
		}
	}

}
