package org.seasar.cubby.examples.todo.entity;

import org.seasar.cubby.examples.todo.base.TodoBase;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.DateFormatValidator;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class Todo extends TodoBase {
	
	public static ValidationRules VALIDATION = new DefaultValidationRules() {
		public void initialize() {
			add("text", new RequiredValidator(), new MaxLengthValidator(10));
			add("memo", new RequiredValidator(), new MaxLengthValidator(100));
			add("typeId", "type",new RequiredValidator());
			add("limitDate", new DateFormatValidator("yyyy-MM-dd"));
		}
	};
	
	private TodoType type;
	public void setType(TodoType type) {
		this.type = type;
	}

	public TodoType getType() {
		return type;
	}
}