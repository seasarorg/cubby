package org.seasar.cubby.examples.todo.action;

import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.examples.todo.dao.TodoDao;
import org.seasar.cubby.examples.todo.dao.TodoTypeDao;
import org.seasar.cubby.examples.todo.dto.AuthenticationDto;
import org.seasar.cubby.examples.todo.dto.TodoConditionDto;
import org.seasar.cubby.examples.todo.entity.Todo;
import org.seasar.cubby.examples.todo.entity.TodoType;

@Url("todo")
public class TodoListAction extends Action {

	// ----------------------------------------------[DI Filed]

	public TodoDao todoDao;

	public TodoTypeDao todoTypeDao;

	// ----------------------------------------------[Attribute]

	public TodoConditionDto todoConditionDto;

	public List<Todo> todoList;

	public AuthenticationDto authenticationDto;

	// ----------------------------------------------[Action Method]

	@Form("todoConditionDto")
	public ActionResult index() {
		this.todoList = todoDao.selectByCondition(todoConditionDto);
		return new Forward("list.jsp");
	}

	// ----------------------------------------------[Helper Method]

	public List<TodoType> getTodoTypes() {
		List<TodoType> todoTypes = todoTypeDao.seletAll();
		return todoTypes;
	}

	public String getQueryString() {
		StringBuilder sb = new StringBuilder();
		if (todoConditionDto.hasKeyword()) {
			sb.append("キーワード=").append(todoConditionDto.getKeyword()).append(
					" ");
		}
		if (todoConditionDto.hasTypeId()) {
			sb.append("種別=").append(
					todoTypeDao.selectById(todoConditionDto.getTypeId())
							.getName()).append(" ");
		}
		return sb.toString();
	}

	// ----------------------------------------------[Helper Method]

}