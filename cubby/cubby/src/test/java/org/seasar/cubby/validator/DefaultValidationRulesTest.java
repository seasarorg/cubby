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

import java.util.Iterator;

import junit.framework.TestCase;

import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.NumberValidator;
import org.seasar.cubby.validator.validators.RangeValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class DefaultValidationRulesTest extends TestCase {

	public void testAddAndGetRules() throws Exception {
		DefaultValidationRules rules = new DefaultValidationRules();
		rules.add(new FieldValidationRule("name", new RequiredValidator(),
				new MaxLengthValidator(10)));
		assertEquals(1, rules.getRules().size());
	}

	public void testInitialize() throws Exception {
		ValidationRules rules = new DefaultValidationRules() {
			public void initialize() {
				add("name", new RequiredValidator(), new MaxLengthValidator(10));
				add("age", new NumberValidator(), new RangeValidator(0, 10));
			}
		};
		assertEquals(2, rules.getRules().size());
	}

	public void testConstractor1() throws Exception {
		ValidationRules rules = new DefaultValidationRules() {
			public void initialize() {
				add("name", new RequiredValidator(), new MaxLengthValidator(10));
				add("age", new NumberValidator(), new RangeValidator(0, 10));
			}
		};
		assertEquals(2, rules.getRules().size());
		Iterator<ValidationRule> iter = rules.getRules().iterator();
		FieldValidationRule rule = (FieldValidationRule) iter.next();
		assertEquals("name", rule.getFieldName());
		assertEquals("name", rule.getFieldNameKey());
		rule = (FieldValidationRule) iter.next();
		assertEquals("age", rule.getFieldName());
		assertEquals("age", rule.getFieldNameKey());
	}

	public void testConstractor2() throws Exception {
		ValidationRules rules = new DefaultValidationRules("userProfile.") {
			public void initialize() {
				add("name", new RequiredValidator(), new MaxLengthValidator(10));
				add("age", new NumberValidator(), new RangeValidator(0, 10));
			}
		};
		assertEquals(2, rules.getRules().size());
		Iterator<ValidationRule> iter = rules.getRules().iterator();
		FieldValidationRule rule = (FieldValidationRule) iter.next();
		assertEquals("name", rule.getFieldName());
		assertEquals("userProfile.name", rule.getFieldNameKey());
		rule = (FieldValidationRule) iter.next();
		assertEquals("age", rule.getFieldName());
		assertEquals("userProfile.age", rule.getFieldNameKey());
	}
}
