package org.seasar.cubby.validator.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.impl.ActionErrorsImpl;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.PropertyValidationRule;
import org.seasar.cubby.validator.Validator;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.seasar.framework.util.ClassUtil;

public class ActionValidatorImplTest extends TestCase {

	ActionValidatorImpl av = new ActionValidatorImpl();
	Action action;
	Map<String, Object> params;
	DefaultValidationRules validators = new DefaultValidationRules();
	Validation validation;
	
	@Override
	protected void setUp() throws Exception {
		action = new SampleAction();
		action.setErrors(new ActionErrorsImpl());
		params = new HashMap<String, Object>();
		validators = new DefaultValidationRules();
		validators.add("prop1", new RequiredValidator());
		validators.add("prop2", new RequiredValidator());
		Method method = ClassUtil.getMethod(SampleAction.class, "test", null);
		validation = method.getAnnotation(Validation.class);
	}
	
	public void testProcessValidation() {
		params.put("prop2", "prop2 value");
		boolean success = av.processValidation(validation, action, params, new Sample1Form(), validators);
		assertFalse(success);
	}

	public void testValidateAction() {
		params.put("prop2", "prop2 value");
		av.validateAction(action, params, new Sample1Form(), validators);
		assertEquals(1, action.getErrors().getFieldErrors().get("prop1").size());
		assertEquals("prop1は必須です。", action.getErrors().getFieldErrors().get("prop1").get(0));
		assertNull(action.getErrors().getFieldErrors().get("prop2"));
	}

	public void testValidate() {
		params.put("prop2", "prop2 value");
		Object form = new Object();
		Validator req = new RequiredValidator();
		PropertyValidationRule propValids1 = new PropertyValidationRule("prop1");
		av.validate(action, params, form, req, propValids1);
		PropertyValidationRule propValids2 = new PropertyValidationRule("prop2");
		av.validate(action, params, form, req, propValids2);
		assertEquals(1, action.getErrors().getFieldErrors().get("prop1").size());
		assertEquals("prop1は必須です。", action.getErrors().getFieldErrors().get("prop1").get(0));
		assertNull(action.getErrors().getFieldErrors().get("prop2"));
	}

	public static class Foo {
		public String value1 = "1";
		private String value2 = "2";
		public String getValue2() {
			return value2;
		}
	}

	public void testValidateNestedValue() {
		Foo foo = new Foo();
		params.put("foo", foo);
		Object form = new Object();
		Validator validator = new RequiredValidator();
		PropertyValidationRule rule1 = new PropertyValidationRule("foo.value1.class");
		av.validate(action, params, form, validator, rule1);
		PropertyValidationRule rule2 = new PropertyValidationRule("foo.value2");
		av.validate(action, params, form, validator, rule2);
		System.out.println(action.getErrors().getAllErrors());
		assertTrue(action.getErrors().isEmpty());
	}

	class SampleAction extends Action {
		@Validation(errorPage="error.jsp")
		public String test() {
			return null;
		}
	}

	class Sample1Form {
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
	
	class Sample2Form {}

}
