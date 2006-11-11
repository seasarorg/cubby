package org.seasar.cubby.examples.todo.entity;

import org.seasar.cubby.examples.todo.base.TodoBase;
import org.seasar.cubby.validator.Validatable;
import org.seasar.cubby.validator.Validators;
import org.seasar.cubby.validator.validators.DateFormat;
import org.seasar.cubby.validator.validators.MaxLength;
import org.seasar.cubby.validator.validators.Required;

public class Todo extends TodoBase implements Validatable {
	
	public static Validators VALIDATORS = new Validators();
	static {
		VALIDATORS.add("text", new Required(), new MaxLength(10));
		VALIDATORS.add("memo", new Required(), new MaxLength(100));
		VALIDATORS.add("typeId", "type",new Required());
		// TODO フォーマットは自動解決の予定 2006/11/06 T.Agata
		VALIDATORS.add("limitDate", "type",new DateFormat("yyyy-MM-dd"));
	}
	
	public Validators getValidators() {
		return VALIDATORS;
	}
	
	private TodoType type;
	public void setType(TodoType type) {
		this.type = type;
	}
	public TodoType getType() {
		return type;
	}
}