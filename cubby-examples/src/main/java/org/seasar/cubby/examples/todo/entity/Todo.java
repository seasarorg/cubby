package org.seasar.cubby.examples.todo.entity;

import org.seasar.cubby.examples.todo.base.TodoBase;
import org.seasar.cubby.validator.Validators;
import org.seasar.cubby.validator.validators.DateFormatValidator;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class Todo extends TodoBase {
	
	public static Validators VALIDATORS = new Validators();
	static {
		VALIDATORS.add("text", new RequiredValidator(), new MaxLengthValidator(10));
		VALIDATORS.add("memo", new RequiredValidator(), new MaxLengthValidator(100));
		VALIDATORS.add("typeId", "type",new RequiredValidator());
		VALIDATORS.add("limitDate", new DateFormatValidator("yyyy-MM-dd"));
	}
	
	private TodoType type;
	public void setType(TodoType type) {
		this.type = type;
	}
	public TodoType getType() {
		return type;
	}
}