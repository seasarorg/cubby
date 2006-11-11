package org.seasar.cubby.examples.todo.logic;

import java.util.List;

import org.seasar.cubby.examples.todo.entity.TodoType;


public interface TodoTypeLogic {

	List<TodoType> findAll();

	TodoType findById(Integer typeId);

}
