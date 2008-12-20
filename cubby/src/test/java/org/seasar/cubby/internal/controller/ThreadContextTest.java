/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

public class ThreadContextTest {

	@Before
	public void setup() {
		ProviderFactory.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(final Class<T> type) {
						if (MessagesBehaviour.class.equals(type)) {
							return type.cast(new DefaultMessagesBehaviour());
						}
						throw new LookupException();
					}

				}));
	}

	@After
	public void teardown() {
		ProviderFactory.clear();
	}

	@Test
	public void getMessagesMap_ja() throws Exception {
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getLocale()).andStubReturn(Locale.JAPANESE);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute() throws Exception {
				final Map<?, ?> result = ThreadContext.getMessagesMap();
				assertEquals("result.size()", 14, result.size());
				assertEquals("(HashMap) result.get(\"valid.arrayMaxSize\")",
						"{0}は{1}以下選択してください。", result.get("valid.arrayMaxSize"));
				return null;
			}

		});
	}

	@Test
	public void getMessagesMap_en() throws Exception {
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getLocale()).andStubReturn(Locale.ENGLISH);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute() throws Exception {
				final Map<?, ?> result = ThreadContext.getMessagesMap();
				assertEquals("result.size()", 14, result.size());
				assertEquals("(HashMap) result.get(\"valid.arrayMaxSize\")",
						"{0} : selects <= {1}.", result
								.get("valid.arrayMaxSize"));
				return null;
			}
		});
	}

	@Test
	public void getMessagesResourceBundle_ja() throws Exception {
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getLocale()).andStubReturn(Locale.JAPANESE);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute() throws Exception {
				final PropertyResourceBundle result = (PropertyResourceBundle) ThreadContext
						.getMessagesResourceBundle();
				assertTrue("result.getKeys().hasMoreElements()", result
						.getKeys().hasMoreElements());
				return null;
			}
		});
	}

	@Test
	public void getMessagesResourceBundle_en() throws Exception {
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getLocale()).andStubReturn(Locale.ENGLISH);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute() throws Exception {
				final PropertyResourceBundle result = (PropertyResourceBundle) ThreadContext
						.getMessagesResourceBundle();
				assertTrue("result.getKeys().hasMoreElements()", result
						.getKeys().hasMoreElements());
				return null;
			}
		});
	}

	@Test
	public void getRequest() throws Exception {
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {
			public Void execute() throws Exception {
				assertSame("ThreadContext.getRequest()", request, ThreadContext
						.getRequest());
				assertSame("ThreadContext.getResponse()", response,
						ThreadContext.getResponse());
				return null;
			}
		});
		verify(request);
	}

	@Test
	public void lifeCycle() throws Exception {
		final HttpServletRequest request1 = createMock(HttpServletRequest.class);
		final HttpServletResponse response1 = createMock(HttpServletResponse.class);
		final HttpServletRequest request2 = createMock(HttpServletRequest.class);
		final HttpServletResponse response2 = createMock(HttpServletResponse.class);
		replay(request1, response1, request2, response2);

		ThreadContext.runInContext(request1, response1, new Command<Void>() {

			public Void execute() throws Exception {
				assertSame(request1, ThreadContext.getRequest());
				assertSame(response1, ThreadContext.getResponse());

				ThreadContext.runInContext(request2, response2,
						new Command<Void>() {

							public Void execute() throws Exception {
								assertSame(request2, ThreadContext.getRequest());
								assertSame(response2, ThreadContext
										.getResponse());
								return null;
							}

						});
				assertSame(request1, ThreadContext.getRequest());
				assertSame(response1, ThreadContext.getResponse());

				try {
					ThreadContext.runInContext(request2, response2,
							new Command<Void>() {

								public Void execute() throws Exception {
									assertSame(request2, ThreadContext
											.getRequest());
									assertSame(response2, ThreadContext
											.getResponse());
									throw new Exception();
								}

							});
				} catch (Exception e) {
					assertSame(request1, ThreadContext.getRequest());
					assertSame(response1, ThreadContext.getResponse());
				}

				return null;
			}
		});

		try {
			ThreadContext.getRequest();
			fail();
		} catch (final IllegalStateException e) {
			// ok
		}

		try {
			ThreadContext.getResponse();
			fail();
		} catch (final IllegalStateException e) {
			// ok
		}

		try {
			ThreadContext.runInContext(request1, response1,
					new Command<Void>() {

						public Void execute() throws Exception {
							assertSame(request1, ThreadContext.getRequest());
							assertSame(response1, ThreadContext.getResponse());
							throw new Exception();
						}

					});
		} catch (Exception e) {
			try {
				ThreadContext.getRequest();
				fail();
			} catch (final IllegalStateException e1) {
				// ok
			}

			try {
				ThreadContext.getResponse();
				fail();
			} catch (final IllegalStateException e1) {
				// ok
			}
		}

		verify(request1, response1, request2, response2);
	}
}
