package org.seasar.cubby.action;

import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class InputTagTextAction extends Action {

	public ValidationRules VALIDATION = new DefaultValidationRules() {
		public void initialize() {
			add("integerValue", new RequiredValidator());
			add("byteValue", new RequiredValidator());
			add("longValue", new RequiredValidator());
			add("floatValue", new RequiredValidator());
			add("doubleValue", new RequiredValidator());
			add("booleanValue", new RequiredValidator());
			add("stringValue", new RequiredValidator());
		}
	};
	
	public Integer integerValue = 11;
	public Byte byteValue = 12;
	//public Character characterValue = 'B';
	public Long longValue = 13L;
	public Float floatValue = 14.5f;
	public Double doubleValue = 16.7d;
	public Boolean booleanValue = false;

	public String stringValue = "value1";
	
	public ActionResult index() {
		return new Forward("index.jsp");
	}

	@Form
	@Validation(errorPage="index.jsp")
	public ActionResult post() {
		return new Forward("index.jsp");
	}

}
