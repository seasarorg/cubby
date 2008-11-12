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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.container.Container;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.mock.MockActionContext;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationProcessor;

public class ValidationProcessorImplTest {

	private ValidationProcessor validationProcessor = new ValidationProcessorImpl();

	private MockAction action = new MockAction();

	private Map<String, Object[]> params = new HashMap<String, Object[]>();

	private HttpServletRequest request;

	@Before
	public void setupContainer() {
		MockContainerProvider.setContainer(new Container() {

			public <T> T lookup(Class<T> type) {
				if (type.equals(MessagesBehaviour.class)) {
					return type.cast(new DefaultMessagesBehaviour());
				}
				return null;
			}

		});
	}

	@Before
	public void setupMock() throws Exception {
		request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_PARAMS)).andReturn(
				params).anyTimes();
		replay(request);
		// include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
		// action = new MockAction();
		// params = new HashMap<String, Object[]>();
		// getRequest().setAttribute(CubbyConstants.ATTR_PARAMS, params);

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

	public void testProcess2() throws Exception {
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

	public void testProcess3() throws Exception {
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

	public void testHandleValidationException() throws Exception {
		ValidationException e = new ValidationException("message", "field1");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, MockAction.class.getMethod("dummy"));
		ActionResult result = validationProcessor.handleValidationException(e,
				request, actionContext);
		assertTrue(result instanceof Forward);
		Forward forward = (Forward) result;
		assertEquals("error.jsp", forward.getPath("UTF-8"));
	}

}
