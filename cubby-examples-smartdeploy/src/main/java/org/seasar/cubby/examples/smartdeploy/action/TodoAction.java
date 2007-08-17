package org.seasar.cubby.examples.smartdeploy.action;

import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.examples.smartdeploy.dao.TodoDao;
import org.seasar.cubby.examples.smartdeploy.dao.TodoTypeDao;
import org.seasar.cubby.examples.smartdeploy.dxo.TodoDxo;
import org.seasar.cubby.examples.smartdeploy.entity.Todo;
import org.seasar.cubby.examples.smartdeploy.entity.TodoType;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.DateFormatValidator;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

//@Form("todo")
public class TodoAction extends Action {

	// ----------------------------------------------[Validation]

	public static ValidationRules VALIDATION = new DefaultValidationRules() {
		public void initialize() {
			add("text", new RequiredValidator(), new MaxLengthValidator(10));
			add("memo", new RequiredValidator(), new MaxLengthValidator(100));
			add("typeId", "todoType",new RequiredValidator());
			add("limitDate", new DateFormatValidator("yyyy-MM-dd"));
		}
	};

	// ----------------------------------------------[DI Filed]

	public TodoDao todoDao;

	public TodoDxo todoDxo;

	public TodoTypeDao todoTypeDao;

	// ----------------------------------------------[Attribute]

	public Integer id;

	public String text;

	public String memo;

	public Integer typeId;

	public String limitDate;

	public TodoType todoType;

	// ----------------------------------------------[Action Method]

	@Url("{id,[0-9]+}")
	@Form("this")
	public ActionResult show() {
		Todo todo = todoDao.selectById(this.id);
		todoDxo.convert(todo, this);
		return new Forward("show.jsp");
	}

//	@Url("add")
	@Form
	public ActionResult create() {
		return new Forward("edit.jsp");
	}

	@Form
	public ActionResult edit() {
		Todo todo = todoDao.selectById(this.id);
		todoDxo.convert(todo, this);
		return new Forward("edit.jsp");
	}

	@Form
	@Validation(errorPage="edit.jsp")
	public ActionResult confirm() {
		TodoType todoType = todoTypeDao.selectById(this.typeId);
		this.todoType = todoType;
		return new Forward("confirm.jsp");
	}

	@Form
	public ActionResult confirm_back() {
		return new Forward("edit.jsp");
	}

	@Form
	@Validation(errorPage = "confirm.jsp")
	public ActionResult save() {
		if (this.id == null) {
			Todo todo = todoDxo.convert(this);
			todoDao.insert(todo);
			flash.put("notice", todo.getText() + "を追加しました。");
		} else {
			Todo todo = todoDxo.convert(this);
			todoDao.update(todo);
			flash.put("notice", todo.getText() + "を更新しました。");
		}
		return new Redirect("/todo/");
	}
	
	@Form
	public ActionResult delete() {
		Todo todo = todoDao.selectById(this.id);
		todoDao.delete(todo);
		flash.put("notice", todo.getText() + "を削除しました。");
		return new Redirect("/todo/");
	}

	// ----------------------------------------------[Helper Method]

	public List<TodoType> getTodoTypes() {
		List<TodoType> todoTypes = todoTypeDao.seletAll();
		return todoTypes;
	}

}