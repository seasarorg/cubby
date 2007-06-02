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
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.DateFormatValidator;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

@Form("todo")
public class TodoAction extends Action {
	
	// ----------------------------------------------[DI Filed]

	TodoLogic todoLogic;
	TodoTypeLogic todoTypeLogic;;
	
	// ----------------------------------------------[Validation]
	
	public static ValidationRules VALIDATION = new DefaultValidationRules() {
		public void initialize() {
			add("text", new RequiredValidator(), new MaxLengthValidator(10));
			add("memo", new RequiredValidator(), new MaxLengthValidator(100));
			add("typeId", "type",new RequiredValidator());
			add("limitDate", new DateFormatValidator("yyyy-MM-dd"));
		}
	};
	
	// ----------------------------------------------[Attribute]

	public Todo todo = new Todo();

	// ----------------------------------------------[Action Method]
	
	@Url("{id,[0-9]+}")
	public ActionResult show() {
		todo = todoLogic.findById(todo.getId());
		return new Forward("show.jsp");
	}

	@Url("add")
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