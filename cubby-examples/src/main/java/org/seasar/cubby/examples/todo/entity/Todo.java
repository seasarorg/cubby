package org.seasar.cubby.examples.todo.entity;

import org.seasar.cubby.examples.todo.base.TodoBase;

public class Todo extends TodoBase {
	
	private TodoType type;
	public void setType(TodoType type) {
		this.type = type;
	}

	public TodoType getType() {
		return type;
	}
}