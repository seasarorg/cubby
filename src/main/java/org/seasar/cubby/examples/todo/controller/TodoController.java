package org.seasar.cubby.examples.todo.controller;

import java.util.List;

import org.seasar.cubby.annotation.Filter;
import org.seasar.cubby.annotation.Form;
import org.seasar.cubby.annotation.Session;
import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.examples.todo.dto.FindTodoDto;
import org.seasar.cubby.examples.todo.entity.Todo;
import org.seasar.cubby.examples.todo.entity.TodoType;
import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.examples.todo.logic.TodoLogic;
import org.seasar.cubby.examples.todo.logic.TodoTypeLogic;
import org.seasar.cubby.util.Assertion;
import org.seasar.cubby.util.StringUtils;


@Form("todo")
@Filter({AuthActionFilter.class})
public class TodoController extends Controller {
	
	// ----------------------------------------------[DI Filed]

	TodoLogic todoLogic;
	TodoTypeLogic todoTypeLogic;;
	
	// ----------------------------------------------[Attribute]

	public Todo todo = new Todo();
	@Session
	public FindTodoDto findTodoDto = new FindTodoDto();
	public List<Todo> todoList;

	// ----------------------------------------------[Action Method]
	
	public String show() {
		Assertion.notNull(todo.getId());
		todo = todoLogic.findById(todo.getId());
		return "show.jsp";
	}

	public String create() {
		return "edit.jsp";
	}

	public String edit() {
		Assertion.notNull(todo.getId());
		todo = todoLogic.findById(todo.getId());
		return "edit.jsp";
	}

	@Validation(errorPage="edit.jsp")
	public String confirm() {
		todo.setType(todoTypeLogic.findById(todo.getTypeId()));
		return "confirm.jsp";
	}

	public String confirm_back() {
		return "edit.jsp";
	}

	@Validation(errorPage="confirm.jsp")
	public String save() {
		if (todo.getId() == null) {
			todoLogic.addTodo(todo);
			flash.put("notice", todo.getText() + "を追加しました。");
		} else {
			todoLogic.updateTodo(todo);
			flash.put("notice", todo.getText() + "を更新しました。");
		}
		return "@list";
	}
	
	public String delete() {
		Assertion.notNull(todo.getId());
		todo = todoLogic.findById(todo.getId());
		todoLogic.deleteById(todo.getId());
		flash.put("notice", todo.getText() + "を削除しました。");
		return "@list";
	}
	
	@Form("findTodoDto")
	public String list() {
		User user = (User) session.get("user");
		todoList = todoLogic.findAllByUserId(user.getId(), findTodoDto);
		return "list.jsp";
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