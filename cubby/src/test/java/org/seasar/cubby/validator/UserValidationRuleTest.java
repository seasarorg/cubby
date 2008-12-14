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
package org.seasar.cubby.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.internal.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.internal.spi.BeanDescProvider;
import org.seasar.cubby.internal.spi.ProviderFactory;
import org.seasar.cubby.internal.validator.impl.ValidationProcessorImpl;
import org.seasar.cubby.mock.MockActionContext;

public class UserValidationRuleTest {

	public ValidationProcessorImpl validationProcessor = new ValidationProcessorImpl();

	public MockAction action = new MockAction();

	@Before
	public void setup() {
		ProviderFactory.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
	}

	@After
	public void teardown() {
		ProviderFactory.clear();
	}

	@Test
	public void userValidation() throws Exception {
		Method method = MockAction.class.getMethod("dummy");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);

		Map<String, Object[]> params = new HashMap<String, Object[]>();
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(ATTR_PARAMS)).andReturn(params).anyTimes();
		replay(request);

		validationProcessor.process(request, actionContext);

		action.value2 = "ng";
		try {
			validationProcessor.process(request, actionContext);
			fail();
		} catch (ValidationException e) {
			ActionErrors errors = actionContext.getActionErrors();
			assertEquals(1, errors.getAll().size());
			assertEquals("validation failed", errors.getAll().get(0));
		}
	}

	public static class MockAction extends Action {

		private String value1;

		private String value2;

		private ValidationRules rules = new DefaultValidationRules() {
			@Override
			public void initialize() {
				add(DATA_CONSTRAINT, new UserValidationRule());
			}
		};

		public String getValue1() {
			return value1;
		}

		public String getValue2() {
			return value2;
		}

		public ValidationRules getRules() {
			return rules;
		}

		class UserValidationRule implements ValidationRule {

			public void apply(Map<String, Object[]> params, Object form,
					ActionErrors errors) {
				if ("ng".equals(value1) || "ng".equals(value2)) {
					errors.add("validation failed", "value1", "value2");
				}
			}

		}

		@Validation(rules = "rules")
		public ActionResult dummy() {
			return null;
		}

	}
}
