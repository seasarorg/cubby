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

import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.validator.impl.ValidationProcessorImpl;
import org.seasar.extension.unit.S2TestCase;

public class UserValidationRuleTest extends S2TestCase {

	public ValidationProcessorImpl validationProcessor;

	public MockAction action;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testUserValidation() throws ValidationException {
		ActionErrors errors = action.getErrors();
		Object form = action;
		ValidationRules rules = action.rules;
		Map<String, Object[]> params = new HashMap<String, Object[]>();

		validationProcessor.validate(rules, params, form);

		action.value2 = "ng";
		try {
			validationProcessor.validate(rules, params, form);
			fail();
		} catch (ValidationException e) {
			assertEquals(1, errors.getAll().size());
			assertEquals("validation failed", errors.getAll().get(0));
		}
	}

	public static class MockAction extends Action {

		public String value1;

		public String value2;

		public ValidationRules rules = new DefaultValidationRules() {
			@Override
			public void initialize() {
				add(DATA_CONSTRAINT, new UserValidationRule());
			}
		};

		class UserValidationRule implements ValidationRule {

			public void apply(Map<String, Object[]> params, Object form,
					ActionErrors errors) {
				if ("ng".equals(value1) || "ng".equals(value2)) {
					errors.add("validation failed", "value1", "value2");
				}
			}

		}
	}
}
