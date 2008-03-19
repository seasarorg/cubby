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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.controller.ActionDefBuilder;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockServletContext;

public class ValidationProcessorImplTest extends S2TestCase {

	public ValidationProcessorImpl validationProcessor;

	public ActionDefBuilder actionDefBuilder;

	public ActionContext context;

	public MockAction action;

	public Map<String, Object[]> params;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testProcess1() {
		MockServletContext servletContext = getServletContext();
		HttpServletRequest request = servletContext.createRequest(CubbyUtils
				.getInternalForwardPath(MockAction.class, "dummy"));
		ActionDef actionDef = actionDefBuilder.build(request);
		context.initialize(actionDef);

		try {
			validationProcessor.process();
			fail();
		} catch (ValidationException e) {
			assertFalse(action.getErrors().isEmpty());
			assertEquals(1, action.getErrors().getFields().size());
			assertNotNull(action.getErrors().getFields().get("name"));
		}
	}

	public void testProcess2() {
		MockServletContext servletContext = getServletContext();
		HttpServletRequest request = servletContext.createRequest(CubbyUtils
				.getInternalForwardPath(MockAction.class, "dummy"));
		ActionDef actionDef = actionDefBuilder.build(request);
		context.initialize(actionDef);

		params.put("name", new Object[] { "bob" });
		params.put("age", new Object[] { "bob" });

		try {
			validationProcessor.process();
			fail();
		} catch (ValidationException e) {
			assertFalse(action.getErrors().isEmpty());
			assertEquals(1, action.getErrors().getFields().size());
			assertNotNull(action.getErrors().getFields().get("age"));
		}
	}

	public void testProcess3() {
		MockServletContext servletContext = getServletContext();
		HttpServletRequest request = servletContext.createRequest(CubbyUtils
				.getInternalForwardPath(MockAction.class, "dummy"));
		ActionDef actionDef = actionDefBuilder.build(request);
		context.initialize(actionDef);

		params.put("name", new Object[] { "bob" });
		params.put("age", new Object[] { "5" });

		try {
			validationProcessor.process();
		} catch (ValidationException e) {
			fail();
		}
	}

	public void testHandleValidationException() {
		MockServletContext servletContext = getServletContext();
		HttpServletRequest request = servletContext.createRequest(CubbyUtils
				.getInternalForwardPath(MockAction.class, "dummy"));
		ActionDef actionDef = actionDefBuilder.build(request);
		context.initialize(actionDef);

		ValidationException e = new ValidationException("message", "field1");
		ActionResult result = validationProcessor.handleValidationException(e);
		assertTrue(result instanceof Forward);
		Forward forward = (Forward) result;
		assertEquals("error.jsp", forward.getPath());
	}

}
