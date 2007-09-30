package org.seasar.cubby.examples.todo.action;

import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.examples.todo.dao.TodoDao;
import org.seasar.cubby.examples.todo.dao.TodoTypeDao;
import org.seasar.cubby.examples.todo.dxo.TodoDxo;
import org.seasar.cubby.examples.todo.entity.Todo;
import org.seasar.cubby.examples.todo.entity.TodoType;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.DateFormatValidator;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

/**
 * 詳細・追加・編集・確認・保存
 * @author agata
 */
public class TodoAction extends Action {

	// ----------------------------------------------[Validation]

	public ValidationRules validation = new DefaultValidationRules() {
		@Override
		public void initialize() {
			add("text", new RequiredValidator(), new MaxLengthValidator(10));
			add("memo", new RequiredValidator(), new MaxLengthValidator(100));
			add("typeId", "type", new RequiredValidator());
			add("limitDate", new DateFormatValidator());
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

	/**
	 * 詳細表示処理(/todo/{id})
	 */
	@Url("{id,[0-9]+}")
	@Form
	public ActionResult show() {
		Todo todo = todoDao.selectById(this.id);
		todoDxo.convert(todo, this);
		return new Forward("show.jsp");
	}

	/**
	 * 追加表示処理(/todo/create)
	 */
	@Form
	public ActionResult create() {
		return new Forward("edit.jsp");
	}

	/**
	 * 編集表示処理(/todo/edit)
	 */
	@Form
	public ActionResult edit() {
		Todo todo = todoDao.selectById(this.id);
		todoDxo.convert(todo, this);
		return new Forward("edit.jsp");
	}

	/**
	 * 確認表示処理(/todo/confirm)
	 */
	@Form
	@Validation(rulesField = "validation", errorPage = "edit.jsp")
	public ActionResult confirm() {
		TodoType todoType = todoTypeDao.selectById(this.typeId);
		this.todoType = todoType;
		return new Forward("confirm.jsp");
	}

	/**
	 * 編集画面に戻る処理(/todo/confirm_back)
	 */
	@Form
	public ActionResult confirm_back() {
		return new Forward("edit.jsp");
	}

	/**
	 * 保存処理(/todo/save)
	 */
	@Form
	@Validation(rulesField = "validation", errorPage = "confirm.jsp")
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
	
	/**
	 * 削除処理(/todo/delete)
	 */
	@Form
	public ActionResult delete() {
		Todo todo = todoDao.selectById(this.id);
		todoDao.delete(todo);
		flash.put("notice", todo.getText() + "を削除しました。");
		return new Redirect("/todo/");
	}

	// ----------------------------------------------[Helper Method]

	/**
	 * 種別一覧の取得
	 */
	public List<TodoType> getTodoTypes() {
		List<TodoType> todoTypes = todoTypeDao.seletAll();
		return todoTypes;
	}

}