package org.seasar.cubby.examples.todo.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.seasar.cubby.examples.todo.dto.FindTodoDto;
import org.seasar.cubby.examples.todo.dto.LoginDto;
import org.seasar.cubby.examples.todo.entity.Todo;
import org.seasar.cubby.examples.todo.logic.TodoLogic;
import org.seasar.cubby.examples.todo.logic.TodoTypeLogic;
import org.seasar.cubby.util.StringUtils;


public class TodoLogicImpl implements TodoLogic {
	
	protected TodoTypeLogic todoTypeLogic;
	
	private List<Todo> todoList = null;

	private int id = 6;

	public TodoLogicImpl() {
	}
	
	public void addTodo(Todo todo) {
		todo.setId(id++);
		todo.setType(todoTypeLogic.findById(todo.getTypeId()));
		getTodoList().add(todo);
	}

	private List<Todo> getTodoList() {
		if (todoList == null) {
			todoList = new ArrayList<Todo>();
			todoList.add(makeTodo(1, "title1", 1, "memo1"));
			todoList.add(makeTodo(2, "title2", 1, "memo2"));
			todoList.add(makeTodo(3, "title3", 2, "memo3"));
			todoList.add(makeTodo(4, "title4", 2, "memo4"));
			todoList.add(makeTodo(5, "title5", 3, "memo5"));
		}
		return todoList;
	}

	public void deleteById(Integer id) {
		getTodoList().remove(findById(id));
	}

	public List<Todo> findAllByUserId(Integer id, FindTodoDto dto) {
		List<Todo> result = new ArrayList<Todo>();
		for (Todo todo : getTodoList()) {
			boolean match = true;
			if (match && StringUtils.isNotEmpty(dto.getKeyword())) {
				match = todo.getText().contains(dto.getKeyword()) || todo.getMemo().contains(dto.getKeyword());
			}
			if (match && dto.getTypeId() != null) {
				match = todo.getTypeId().equals(dto.getTypeId());
			}
			if (match) {
				result.add(todo);
			}
		}
		return result;
	}

	private Todo makeTodo(int id, String text, int typeId, String memo) {
		Todo todo = new Todo();
		todo.setId(id);
		todo.setText(text);
		todo.setTypeId(typeId);
		todo.setMemo(memo);
		todo.setLimitDate(new Date());
		todo.setType(todoTypeLogic.findById(todo.getTypeId()));
		return todo;
	}

	public Todo findById(Integer id) {
		for (Todo target : getTodoList()) {
			if(target.getId().equals(id)) {
				return target;
			}
		}
		return null;
	}

	public void updateTodo(Todo todo) {
		Todo target = findById(todo.getId());
		target.setText(todo.getText());
		target.setTypeId(todo.getTypeId());
		target.setMemo(todo.getMemo());
	}

	public boolean login(LoginDto dto) {
		return "test".equals(dto.getUserId()) && "test".equals(dto.getPassword());
	}

	
}
