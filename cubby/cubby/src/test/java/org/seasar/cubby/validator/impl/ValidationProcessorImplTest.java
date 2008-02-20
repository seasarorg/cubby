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

import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.impl.ActionErrorsImpl;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.FieldValidationRule;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.seasar.extension.unit.S2TestCase;

public class ValidationProcessorImplTest extends S2TestCase {

	public ValidationProcessorImpl processor;
	Map<String, Object[]> params;
	ActionErrors errors = new ActionErrorsImpl();
	DefaultValidationRules validators = new DefaultValidationRules();

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");

		params = new HashMap<String, Object[]>();
		validators = new DefaultValidationRules();
		validators.add("prop1", new RequiredValidator());
		validators.add("prop2", new RequiredValidator());
	}

	public void testProcessValidation() {
		params.put("prop2", new Object[] { "prop2 value" });
		boolean success = processor.process(errors, params,
				new Sample1Form(), validators);
		assertFalse(success);
	}

	public void testValidateAction() {
		params.put("prop2", new Object[] { "prop2 value" });
		for (ValidationRule rule : validators.getRules()) {
			rule.apply(params, new Sample1Form(), errors);
		}
		assertEquals(1, errors.getFields().get("prop1").size());
		assertEquals("prop1は必須です。", errors.getFields().get("prop1").get(0));
		assertEquals(0, errors.getFields().get("prop2").size());
	}

	public void testValidate() {
		params.put("prop2", new Object[] { "prop2 value" });
		Object form = new Object();
		FieldValidationRule[] rules = {
				new FieldValidationRule("prop1", new RequiredValidator()),
				new FieldValidationRule("prop2", new RequiredValidator()), };
		for (final ValidationRule rule : rules) {
			rule.apply(params, form, errors);
		}
		assertEquals(1, errors.getFields().get("prop1").size());
		assertEquals("prop1は必須です。", errors.getFields().get("prop1").get(0));
		assertEquals(0, errors.getFields().get("prop2").size());
	}

	public static class Foo {
		public String value1 = "1";
		private String value2 = "2";

		public String getValue2() {
			return value2;
		}
	}

//	public static class SampleAction extends Action {
//
//		@Validation(rules = "validation", errorPage = "error.jsp")
//		public String test() {
//			return null;
//		}
//	}

	static class Sample1Form {
		private String prop1;
		private Integer prop2;

		public String getProp1() {
			return prop1;
		}

		public void setProp1(String prop1) {
			this.prop1 = prop1;
		}

		public Integer getProp2() {
			return prop2;
		}

		public void setProp2(Integer prop2) {
			this.prop2 = prop2;
		}

	}

	static class Sample2Form {
	}

}
