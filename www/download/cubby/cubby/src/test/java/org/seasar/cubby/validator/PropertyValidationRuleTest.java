package org.seasar.cubby.validator;

import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

import junit.framework.TestCase;

public class PropertyValidationRuleTest extends TestCase {
	public void testConstractor1() throws Exception {
		PropertyValidationRule rule = new PropertyValidationRule("name", new RequiredValidator(), new MaxLengthValidator(10));
		assertEquals("name", rule.getPropertyName());
		assertEquals("name", rule.getPropertyNameKey());
		assertEquals(2, rule.getValidators().size());
	}
	public void testConstractor2() throws Exception {
		PropertyValidationRule rule = new PropertyValidationRule("name", "label.name", new RequiredValidator(), new MaxLengthValidator(10));
		assertEquals("name", rule.getPropertyName());
		assertEquals("label.name", rule.getPropertyNameKey());
		assertEquals(2, rule.getValidators().size());
	}
}
