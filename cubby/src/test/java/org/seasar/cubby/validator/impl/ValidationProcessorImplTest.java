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
package org.seasar.cubby.validator.impl;

import static org.easymock.EasyMock.anyBoolean;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.easymock.IAnswer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.internal.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.container.LookupException;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.spi.BeanDescProvider;
import org.seasar.cubby.internal.spi.ContainerProvider;
import org.seasar.cubby.internal.spi.ProviderFactory;
import org.seasar.cubby.internal.validator.impl.ValidationProcessorImpl;
import org.seasar.cubby.mock.MockActionContext;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationProcessor;

public class ValidationProcessorImplTest {

	private ValidationProcessor validationProcessor = new ValidationProcessorImpl();

	private MockAction action = new MockAction();

	private Map<String, Object[]> params = new HashMap<String, Object[]>();

	private HttpServletRequest request;

	private boolean validationFail;

	@Before
	public void setupProvider() {
		ProviderFactory.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) {
						if (type.equals(MessagesBehaviour.class)) {
							return type.cast(new DefaultMessagesBehaviour());
						}
						throw new LookupException();
					}

				}));
		ProviderFactory.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
	}

	@After
	public void teardownProvider() {
		ProviderFactory.clear();
	}

	@Before
	public void setupMock() throws Exception {
		request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_PARAMS)).andStubReturn(
				params);
		expect(request.getLocale()).andStubReturn(null);
		request.setAttribute(eq(CubbyConstants.ATTR_VALIDATION_FAIL),
				anyBoolean());
		expectLastCall().andStubAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				validationFail = (Boolean) getCurrentArguments()[1];
				return null;
			}

		});
		replay(request);
		ThreadContext.newContext(request);
	}

	@After
	public void teardownMock() {
		ThreadContext.restoreContext();
	}

	@Test
	public void process1() throws Exception {
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, MockAction.class.getMethod("dummy"));
		try {
			validationProcessor.process(request, actionContext);
			fail();
		} catch (ValidationException e) {
			ActionErrors errors = actionContext.getActionErrors();
			assertFalse(errors.isEmpty());
			assertEquals(1, errors.getFields().size());
			assertNotNull(errors.getFields().get("name"));
		}
	}

	@Test
	public void process2() throws Exception {
		params.put("name", new Object[] { "bob" });
		params.put("age", new Object[] { "bob" });

		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, MockAction.class.getMethod("dummy"));
		try {
			validationProcessor.process(request, actionContext);
			fail();
		} catch (ValidationException e) {
			ActionErrors errors = actionContext.getActionErrors();
			assertFalse(errors.isEmpty());
			assertEquals(1, errors.getFields().size());
			assertNotNull(errors.getFields().get("age"));
		}
	}

	@Test
	public void process3() throws Exception {
		params.put("name", new Object[] { "bob" });
		params.put("age", new Object[] { "5" });

		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, MockAction.class.getMethod("dummy"));
		try {
			validationProcessor.process(request, actionContext);
		} catch (ValidationException e) {
			fail();
		}
	}

	@Test
	public void handleValidationException() throws Exception {
		ValidationException e = new ValidationException("message", "field1");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, MockAction.class.getMethod("dummy"));
		ActionResult result = validationProcessor.handleValidationException(e,
				request, actionContext);
		assertTrue(result instanceof Forward);
		Forward forward = (Forward) result;
		assertEquals("error.jsp", forward.getPath("UTF-8"));
		assertTrue(validationFail);
	}

}
