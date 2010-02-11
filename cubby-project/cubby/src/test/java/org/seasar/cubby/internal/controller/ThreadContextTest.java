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

package org.seasar.cubby.internal.controller;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

public class ThreadContextTest {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	@Before
	public void setup() {
		final BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(final Class<T> type) {
						if (MessagesBehaviour.class.equals(type)) {
							return type.cast(new DefaultMessagesBehaviour());
						}
						throw new LookupException();
					}

				}));
		pluginRegistry.register(binderPlugin);
	}

	@After
	public void teardown() {
		pluginRegistry.clear();
	}

	// @Test
	// public void getMessagesMap_ja() throws Exception {
	// final HttpServletRequest request = createMock(HttpServletRequest.class);
	// expect(request.getLocale()).andStubReturn(Locale.JAPANESE);
	// final HttpServletResponse response =
	// createMock(HttpServletResponse.class);
	// replay(request, response);
	//
	// ThreadContext.runInContext(request, response, new Command() {
	//
	// public void execute(final HttpServletRequest request,
	// final HttpServletResponse response) throws Exception {
	// final Map<?, ?> result = ThreadContext.getMessagesMap();
	// assertEquals("result.size()", 16, result.size());
	// assertEquals("(HashMap) result.get(\"valid.arrayMaxSize\")",
	// "{0}は{1}以下選択してください。", result.get("valid.arrayMaxSize"));
	// }
	//
	// });
	// }
	//
	// @Test
	// public void getMessagesMap_en() throws Exception {
	// final HttpServletRequest request = createMock(HttpServletRequest.class);
	// expect(request.getLocale()).andStubReturn(Locale.ENGLISH);
	// final HttpServletResponse response =
	// createMock(HttpServletResponse.class);
	// replay(request, response);
	//
	// ThreadContext.runInContext(request, response, new Command() {
	//
	// public void execute(final HttpServletRequest request,
	// final HttpServletResponse response) throws Exception {
	// final Map<?, ?> result = ThreadContext.getMessagesMap();
	// assertEquals("result.size()", 16, result.size());
	// assertEquals("(HashMap) result.get(\"valid.arrayMaxSize\")",
	// "{0} : selects <= {1}.", result
	// .get("valid.arrayMaxSize"));
	// }
	// });
	// }
	//
	// @Test
	// public void getMessagesResourceBundle_ja() throws Exception {
	// final HttpServletRequest request = createMock(HttpServletRequest.class);
	// expect(request.getLocale()).andStubReturn(Locale.JAPANESE);
	// final HttpServletResponse response =
	// createMock(HttpServletResponse.class);
	// replay(request, response);
	//
	// ThreadContext.runInContext(request, response, new Command() {
	//
	// public void execute(final HttpServletRequest request,
	// final HttpServletResponse response) throws Exception {
	// final PropertyResourceBundle result = (PropertyResourceBundle)
	// ThreadContext
	// .getMessagesResourceBundle();
	// assertTrue("result.getKeys().hasMoreElements()", result
	// .getKeys().hasMoreElements());
	// }
	// });
	// }
	//
	// @Test
	// public void getMessagesResourceBundle_en() throws Exception {
	// final HttpServletRequest request = createMock(HttpServletRequest.class);
	// expect(request.getLocale()).andStubReturn(Locale.ENGLISH);
	// final HttpServletResponse response =
	// createMock(HttpServletResponse.class);
	// replay(request, response);
	//
	// ThreadContext.runInContext(request, response, new Command() {
	//
	// public void execute(final HttpServletRequest request,
	// final HttpServletResponse response) throws Exception {
	// final PropertyResourceBundle result = (PropertyResourceBundle)
	// ThreadContext
	// .getMessagesResourceBundle();
	// assertTrue("result.getKeys().hasMoreElements()", result
	// .getKeys().hasMoreElements());
	// }
	// });
	// }

	@Test
	public void getRequest() throws Exception {
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.enter(request, response);
		try {
			final ThreadContext currentContext = ThreadContext
					.getCurrentContext();
			assertSame("ThreadContext.getRequest()", request, currentContext
					.getRequest());
			assertSame("ThreadContext.getResponse()", response, currentContext
					.getResponse());
		} finally {
			ThreadContext.exit();
		}
		ThreadContext.remove();
		verify(request);
	}

	@Test
	public void lifeCycle() throws Exception {
		final HttpServletRequest request1 = createMock(HttpServletRequest.class);
		final HttpServletResponse response1 = createMock(HttpServletResponse.class);
		final HttpServletRequest request2 = createMock(HttpServletRequest.class);
		final HttpServletResponse response2 = createMock(HttpServletResponse.class);
		replay(request1, response1, request2, response2);

		ThreadContext.enter(request1, response1);
		try {
			assertSame(request1, ThreadContext.getCurrentContext().getRequest());
			assertSame(response1, ThreadContext.getCurrentContext()
					.getResponse());

			ThreadContext.enter(request2, response2);
			try {
				assertSame(request2, ThreadContext.getCurrentContext()
						.getRequest());
				assertSame(response2, ThreadContext.getCurrentContext()
						.getResponse());
			} finally {
				ThreadContext.exit();
			}
			assertSame(request1, ThreadContext.getCurrentContext().getRequest());
			assertSame(response1, ThreadContext.getCurrentContext()
					.getResponse());

			try {
				ThreadContext.enter(request2, response2);
				try {
					assertSame(request2, ThreadContext.getCurrentContext()
							.getRequest());
					assertSame(response2, ThreadContext.getCurrentContext()
							.getResponse());
					throw new Exception();
				} finally {
					ThreadContext.exit();
				}
			} catch (Exception e) {
				assertSame(request1, ThreadContext.getCurrentContext()
						.getRequest());
				assertSame(response1, ThreadContext.getCurrentContext()
						.getResponse());
			}
		} finally {
			ThreadContext.exit();
		}
		ThreadContext.remove();

		try {
			ThreadContext.getCurrentContext().getRequest();
			fail();
		} catch (IllegalStateException e) {
			// ok
		}
		try {
			ThreadContext.getCurrentContext().getResponse();
			fail();
		} catch (IllegalStateException e) {
			// ok
		}

		try {
			ThreadContext.enter(request1, response1);
			try {
				assertSame(request1, ThreadContext.getCurrentContext()
						.getRequest());
				assertSame(response1, ThreadContext.getCurrentContext()
						.getResponse());
				throw new Exception();
			} finally {
				ThreadContext.exit();
			}
		} catch (Exception e) {
			assertSame(request1, ThreadContext.getCurrentContext().getRequest());
			assertSame(response1, ThreadContext.getCurrentContext()
					.getResponse());
		}
		ThreadContext.remove();

		try {
			ThreadContext.getCurrentContext().getRequest();
			fail();
		} catch (IllegalStateException e) {
			// ok
		}
		try {
			ThreadContext.getCurrentContext().getResponse();
			fail();
		} catch (IllegalStateException e) {
			// ok
		}

		verify(request1, response1, request2, response2);
	}
}
