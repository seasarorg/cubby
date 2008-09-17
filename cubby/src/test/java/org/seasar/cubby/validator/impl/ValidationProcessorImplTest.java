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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ClassUtil;

public class ValidationProcessorImplTest extends S2TestCase {

	public ValidationProcessorImpl validationProcessor;

	public MockAction action;

	public Map<String, Object[]> params;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
		params = new HashMap<String, Object[]>();
		getRequest().setAttribute(CubbyConstants.ATTR_PARAMS, params);
	}

	public void testProcess1() {
		Method method = ClassUtil.getMethod(MockAction.class, "dummy",
				new Class[0]);
		try {
			validationProcessor.process(getRequest(), action, MockAction.class,
					method);
			fail();
		} catch (ValidationException e) {
			assertFalse(action.getErrors().isEmpty());
			assertEquals(1, action.getErrors().getFields().size());
			assertNotNull(action.getErrors().getFields().get("name"));
		}
	}

	public void testProcess2() {
		params.put("name", new Object[] { "bob" });
		params.put("age", new Object[] { "bob" });

		Method method = ClassUtil.getMethod(MockAction.class, "dummy",
				new Class[0]);
		try {
			validationProcessor.process(getRequest(), action, MockAction.class,
					method);
			fail();
		} catch (ValidationException e) {
			assertFalse(action.getErrors().isEmpty());
			assertEquals(1, action.getErrors().getFields().size());
			assertNotNull(action.getErrors().getFields().get("age"));
		}
	}

	public void testProcess3() {
		params.put("name", new Object[] { "bob" });
		params.put("age", new Object[] { "5" });

		Method method = ClassUtil.getMethod(MockAction.class, "dummy",
				new Class[0]);
		try {
			validationProcessor.process(getRequest(), action, MockAction.class,
					method);
		} catch (ValidationException e) {
			fail();
		}
	}

	public void testHandleValidationException() {
		ValidationException e = new ValidationException("message", "field1");
		Method method = ClassUtil.getMethod(MockAction.class, "dummy",
				new Class[0]);
		ActionResult result = validationProcessor.handleValidationException(e,
				getRequest(), action, method);
		assertTrue(result instanceof Forward);
		Forward forward = (Forward) result;
		assertEquals("error.jsp", forward.getPath("UTF-8"));
	}

}
