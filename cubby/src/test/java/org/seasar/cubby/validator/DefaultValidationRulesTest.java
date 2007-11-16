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
