package org.seasar.cubby.examples.todo.controller;

import java.util.List;

import org.seasar.cubby.annotation.Form;
import org.seasar.cubby.annotation.Session;
import org.seasar.cubby.annotation.Url;
import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.results.Forward;
import org.seasar.cubby.controller.results.Redirect;
import org.seasar.cubby.examples.todo.dto.FindTodoDto;
import org.seasar.cubby.examples.todo.entity.Todo;
import org.seasar.cubby.examples.todo.entity.TodoType;
import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.examples.todo.logic.TodoLogic;
import org.seasar.cubby.examples.todo.logic.TodoTypeLogic;
import org.seasar.cubby.util.StringUtils;


@Form("todo")
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
	
	@Url("{id,[0-9]+}")
	public ActionResult show() {
		todo = todoLogic.findById(todo.getId());
		return new Forward("show.jsp");
	}

	public ActionResult create() {
		return new Forward("edit.jsp");
	}

	public ActionResult edit() {
		todo = todoLogic.findById(todo.getId());
		return new Forward("edit.jsp");
	}

	@Validation(errorPage="edit.jsp")
	public ActionResult confirm() {
		todo.setType(todoTypeLogic.findById(todo.getTypeId()));
		return new Forward("confirm.jsp");
	}

	public ActionResult confirm_back() {
		return new Forward("edit.jsp");
	}

	@Validation(errorPage="confirm.jsp")
	public ActionResult save() {
		if (todo.getId() == null) {
			todoLogic.addTodo(todo);
			flash.put("notice", todo.getText() + "を追加しました。");
		} else {
			todoLogic.updateTodo(todo);
			flash.put("notice", todo.getText() + "を更新しました。");
		}
		return new Redirect("list");
	}
	
	public ActionResult delete() {
		todo = todoLogic.findById(todo.getId());
		todoLogic.deleteById(todo.getId());
		flash.put("notice", todo.getText() + "を削除しました。");
		return new Redirect("list");
	}
	
	@Form("findTodoDto")
	public ActionResult list() {
		User user = (User) session.get("user");
		todoList = todoLogic.findAllByUserId(user.getId(), findTodoDto);
		return new Forward("list.jsp");
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