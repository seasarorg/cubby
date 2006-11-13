package org.seasar.cubby.validator.impl;

import java.lang.reflect.Method;
import java.util.HashMap;

import junit.framework.TestCase;

import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.impl.ActionErrorsImpl;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.util.ParameterMap;
import org.seasar.cubby.validator.LabelKey;
import org.seasar.cubby.validator.PropertyValidators;
import org.seasar.cubby.validator.Validator;
import org.seasar.cubby.validator.Validators;
import org.seasar.cubby.validator.validators.Required;

public class ActionValidatorImplTest extends TestCase {

	ActionValidatorImpl av = new ActionValidatorImpl();
	Controller controller;
	Validators validators = new Validators();
	Validation validation;
	
	@Override
	protected void setUp() throws Exception {
		controller = new SampleContoroller();
		controller.setParams(new ParameterMap(new HashMap()));
		controller.setErrors(new ActionErrorsImpl());
		validators = new Validators();
		validators.add("prop1", new Required());
		validators.add("prop2", new Required());
		Method method = ClassUtils.getMethod(SampleContoroller.class, "test", null);
		validation = method.getAnnotation(Validation.class);
	}
	
	public void testProcessValidation() {
		controller.getParams().put("prop2", "prop2 value");
		boolean success = av.processValidation(validation, controller, new Sample1Form(), validators);
		assertFalse(success);
	}

	public void testValidateAction() {
		controller.getParams().put("prop2", "prop2 value");
		av.validateAction(controller, new Sample1Form(), validators);
		assertEquals(1, controller.getErrors().getFieldErrors().get("prop1").size());
		assertEquals("prop1は必須です。", controller.getErrors().getFieldErrors().get("prop1").get(0));
		assertNull(controller.getErrors().getFieldErrors().get("prop2"));
	}

	public void testValidate() {
		controller.getParams().put("prop2", "prop2 value");
		Object form = new Object();
		Validator req = new Required();
		PropertyValidators propValids1 = new PropertyValidators("prop1");
		av.validate(controller, form, req, propValids1);
		PropertyValidators propValids2 = new PropertyValidators("prop2");
		av.validate(controller, form, req, propValids2);
		assertEquals(1, controller.getErrors().getFieldErrors().get("prop1").size());
		assertEquals("prop1は必須です。", controller.getErrors().getFieldErrors().get("prop1").get(0));
		assertNull(controller.getErrors().getFieldErrors().get("prop2"));
	}

	public void testGetLabelKey() {
		assertEquals("prop1", av.getLabelKey(new Sample1Form(), "prop1"));
		assertEquals("sample.prop2", av.getLabelKey(new Sample2Form(), "prop2"));
	}
	
	class SampleContoroller extends Controller {
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
	
	@LabelKey("sample.")
	class Sample2Form {}

}
