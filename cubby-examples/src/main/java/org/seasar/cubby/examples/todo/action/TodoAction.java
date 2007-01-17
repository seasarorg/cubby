package org.seasar.cubby.examples.todo.action;

import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.examples.todo.entity.Todo;
import org.seasar.cubby.examples.todo.entity.TodoType;
import org.seasar.cubby.examples.todo.logic.TodoLogic;
import org.seasar.cubby.examples.todo.logic.TodoTypeLogic;

@Form("todo")
public class TodoAction extends Action {
	
	// ----------------------------------------------[DI Filed]

	TodoLogic todoLogic;
	TodoTypeLogic todoTypeLogic;;
	
	// ----------------------------------------------[Attribute]

	public Todo todo = new Todo();

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
	
	// ----------------------------------------------[Helper Method]

	public List<TodoType> getTodoTypes() {
		return todoTypeLogic.findAll();
	}
}