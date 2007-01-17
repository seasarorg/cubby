package org.seasar.cubby.examples.todo.action;

import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Session;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.examples.todo.dto.FindTodoDto;
import org.seasar.cubby.examples.todo.entity.Todo;
import org.seasar.cubby.examples.todo.entity.TodoType;
import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.examples.todo.logic.TodoLogic;
import org.seasar.cubby.examples.todo.logic.TodoTypeLogic;
import org.seasar.cubby.util.StringUtils;

@Url("todo")
public class TodoListAction extends Action {
	
	// ----------------------------------------------[DI Filed]

	TodoLogic todoLogic;
	TodoTypeLogic todoTypeLogic;;
	
	// ----------------------------------------------[Attribute]

	@Session
	public FindTodoDto findTodoDto = new FindTodoDto();
	public List<Todo> todoList;
	@Session
	public User user;
	public Todo todo = new Todo();

	// ----------------------------------------------[Action Method]
	
	@Form("findTodoDto")
	public ActionResult list() {
		todoList = todoLogic.findAllByUserId(user.getId(), findTodoDto);
		return new Forward("list.jsp");
	}

	@Form("todo")
	public ActionResult delete() {
		todo = todoLogic.findById(todo.getId());
		todoLogic.deleteById(todo.getId());
		flash.put("notice", todo.getText() + "を削除しました。");
		return new Redirect("list");
	}

	// ----------------------------------------------[Helper Method]

	public List<TodoType> getTodoTypes() {
		return todoTypeLogic.findAll();
	}
	
	public String getQueryString() {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(findTodoDto.getKeyword())) {
			sb.append("キーワード=").append(findTodoDto.getKeyword()).append(" ");
		}
		if (findTodoDto.getTypeId() != null) {
			sb.append("種別=").append(todoTypeLogic.findById(findTodoDto.getTypeId()).getName()).append(" ");
		}
		return sb.toString();
	}
}