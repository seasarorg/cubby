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
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.mock.MockActionErrors;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;

public class UserValidationRuleTest {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	// private ValidationProcessorImpl validationProcessor = new
	// ValidationProcessorImpl();

	private MockAction action = new MockAction();

	@Before
	public void setup() {
		BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
		pluginRegistry.register(binderPlugin);
	}

	@After
	public void teardown() {
		pluginRegistry.clear();
	}

	@Test
	public void userValidation() throws Exception {
		Method method = MockAction.class.getMethod("dummy");

		Map<String, Object[]> params = new HashMap<String, Object[]>();
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(ATTR_PARAMS)).andReturn(params).anyTimes();
		replay(request);

		ActionErrors actionErrors = new MockActionErrors();

		Validation validation = ValidationUtils.getValidation(method);
		ValidationRules rules = ValidationUtils.getValidationRules(action,
				validation.rules());

		action.value2 = "ng";
		try {
			rules.validate(params, action, actionErrors);
			fail();
		} catch (ValidationException e) {
			assertEquals(1, actionErrors.getAll().size());
			assertEquals("validation failed", actionErrors.getAll().get(0));
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
