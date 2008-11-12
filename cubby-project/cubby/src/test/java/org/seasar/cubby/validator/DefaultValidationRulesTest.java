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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.seasar.cubby.validator.DefaultValidationRules.DATA_CONSTRAINT;
import static org.seasar.cubby.validator.DefaultValidationRules.DATA_TYPE;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.validator.validators.EqualsValidator;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.NumberValidator;
import org.seasar.cubby.validator.validators.RangeValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class DefaultValidationRulesTest {

	@Test
	public void addAndGetRules1() throws Exception {
		ValidationRules rules = new DefaultValidationRules() {
			@Override
			protected void initialize() {
				add("name", new RequiredValidator(), new MaxLengthValidator(10));
			}
		};
		assertEquals(1, rules.getPhaseValidationRules(DATA_TYPE).size());
		assertEquals(0, rules.getPhaseValidationRules(DATA_CONSTRAINT).size());
	}

	@Test
	public void addAndGetRules2() throws Exception {
		ValidationRules rules = new DefaultValidationRules() {
			@Override
			protected void initialize() {
				add(DATA_CONSTRAINT, new ValidationRule() {
					public void apply(Map<String, Object[]> params,
							Object form, ActionErrors errors)
							throws ValidationException {
						if ("2".equals(params.get("foo"))) {
							if (params.get("bar") == null
									|| "".equals(params.get("bar"))) {
								throw new ValidationException("message");
							}
						}
					}
				});
			}
		};
		assertEquals(0, rules.getPhaseValidationRules(DATA_TYPE).size());
		assertEquals(1, rules.getPhaseValidationRules(DATA_CONSTRAINT).size());
	}

	@Test
	public void initialize() throws Exception {
		ValidationRules rules = new DefaultValidationRules() {
			public void initialize() {
				add("name", new RequiredValidator(), new MaxLengthValidator(10));
				add("age", new NumberValidator(), new RangeValidator(0, 10));
			}
		};
		assertEquals(2, rules.getPhaseValidationRules(DATA_TYPE).size());
		assertEquals(0, rules.getPhaseValidationRules(DATA_CONSTRAINT).size());
	}

	@Test
	public void constractor1() throws Exception {
		ValidationRules rules = new DefaultValidationRules() {
			public void initialize() {
				add("name", new RequiredValidator(), new MaxLengthValidator(10));
				add("age", new NumberValidator(), new RangeValidator(0, 10));
			}
		};
		assertEquals(2, rules.getPhaseValidationRules(DATA_TYPE).size());
		assertEquals(0, rules.getPhaseValidationRules(DATA_CONSTRAINT).size());

		Iterator<ValidationRule> iter = rules
				.getPhaseValidationRules(DATA_TYPE).iterator();
		FieldValidationRule rule = (FieldValidationRule) iter.next();
		assertEquals("name", rule.getFieldName());
		assertEquals("name", rule.getFieldNameKey());
		rule = (FieldValidationRule) iter.next();
		assertEquals("age", rule.getFieldName());
		assertEquals("age", rule.getFieldNameKey());
	}

	@Test
	public void constractor2() throws Exception {
		ValidationRules rules = new DefaultValidationRules("userProfile.") {
			public void initialize() {
				add("name", new RequiredValidator(), new MaxLengthValidator(10));
				add("age", new NumberValidator(), new RangeValidator(0, 10));
			}
		};
		assertEquals(2, rules.getPhaseValidationRules(DATA_TYPE).size());
		assertEquals(0, rules.getPhaseValidationRules(DATA_CONSTRAINT).size());

		Iterator<ValidationRule> iter = rules
				.getPhaseValidationRules(DATA_TYPE).iterator();
		FieldValidationRule rule = (FieldValidationRule) iter.next();
		assertEquals("name", rule.getFieldName());
		assertEquals("userProfile.name", rule.getFieldNameKey());
		rule = (FieldValidationRule) iter.next();
		assertEquals("age", rule.getFieldName());
		assertEquals("userProfile.age", rule.getFieldNameKey());
	}

	@Test
	public void fail() {
		ValidationRules rules = new DefaultValidationRules("userProfile.") {
			public void initialize() {
				add("name", new RequiredValidator(), new MaxLengthValidator(10));
				add("age", new NumberValidator(), new RangeValidator(0, 10));
			}
		};
		ActionResult result = rules.fail("error.jsp");
		assertTrue(result instanceof Forward);
		Forward forward = (Forward) result;
		assertEquals("error.jsp", forward.getPath("UTF-8"));
	}

	@Test
	public void failOverride() {
		ValidationRules rules = new DefaultValidationRules("userProfile.") {
			public void initialize() {
				add("name", new RequiredValidator(), new MaxLengthValidator(10));
				add("age", new NumberValidator(), new RangeValidator(0, 10));
			}

			public ActionResult fail(String errorPage) {
				return new Redirect(errorPage);
			}
		};
		ActionResult result = rules.fail("error.jsp");
		assertTrue(result instanceof Redirect);
		Redirect redirect = (Redirect) result;
		assertEquals("error.jsp", redirect.getPath("UTF-8"));
	}

	@Test
	public void validationPhasePriority() {

		ValidationRules validationRules = new DefaultValidationRules() {

			@Override
			protected void initialize() {
			}

		};

		Iterator<ValidationPhase> iterator = validationRules
				.getValidationPhases().iterator();
		ValidationPhase first = iterator.next();
		ValidationPhase second = iterator.next();
		assertFalse(iterator.hasNext());

		assertEquals(DATA_TYPE, first);
		assertEquals(DATA_CONSTRAINT, second);
	}

	@Test
	public void addAll() {
		final ValidationRules base = new DefaultValidationRules() {
			@Override
			protected void initialize() {
				add("param1", new RequiredValidator());
				add("param2", new EqualsValidator("a"));
				add(DATA_CONSTRAINT, new ValidationRule() {

					public void apply(Map<String, Object[]> params,
							Object form, ActionErrors errors)
							throws ValidationException {
					}

				});
			}
		};

		final ValidationRules rules = new DefaultValidationRules() {
			@Override
			protected void initialize() {
				addAll(base);
			}
		};

		Collection<ValidationRule> dataTypeRules = rules
				.getPhaseValidationRules(DATA_TYPE);
		Collection<ValidationRule> dataConstraintRules = rules
				.getPhaseValidationRules(DATA_CONSTRAINT);

		assertEquals(2, dataTypeRules.size());
		assertEquals(1, dataConstraintRules.size());
	}

}
