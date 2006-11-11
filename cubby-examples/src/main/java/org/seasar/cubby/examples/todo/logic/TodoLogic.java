package org.seasar.cubby.examples.todo.logic;

import java.util.List;

import org.seasar.cubby.examples.todo.dto.FindTodoDto;
import org.seasar.cubby.examples.todo.dto.LoginDto;
import org.seasar.cubby.examples.todo.entity.Todo;


public interface TodoLogic {

	void addTodo(Todo todo);

	void updateTodo(Todo todo);

	Todo findById(Integer id);

	void deleteById(Integer id);

	boolean login(LoginDto dto);

	List<Todo> findAllByUserId(Integer id, FindTodoDto findTodoDto);

}
