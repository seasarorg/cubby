package org.seasar.cubby.examples.todo.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.cubby.examples.todo.entity.TodoType;
import org.seasar.cubby.examples.todo.logic.TodoTypeLogic;


public class TodoTypeLogicImpl implements TodoTypeLogic {

	public List<TodoType> findAll() {
		List<TodoType> types = new ArrayList<TodoType>();
		types.add(makeTodoType(1));
		types.add(makeTodoType(2));
		types.add(makeTodoType(3));
		return types;
	}

	private TodoType makeTodoType(int id) {
		TodoType type = new TodoType();
		type.setId(id);
		type.setName("todo type" + id);
		return type;
	}

	public TodoType findById(Integer typeId) {
		return makeTodoType(typeId);
	}
}
