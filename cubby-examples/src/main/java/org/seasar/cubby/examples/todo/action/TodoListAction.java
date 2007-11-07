package org.seasar.cubby.examples.todo.action;

import java.text.DateFormat;
import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.examples.todo.dao.TodoDao;
import org.seasar.cubby.examples.todo.dao.TodoTypeDao;
import org.seasar.cubby.examples.todo.dto.TodoConditionDto;
import org.seasar.cubby.examples.todo.entity.Todo;
import org.seasar.cubby.examples.todo.entity.TodoType;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.DateFormatValidator;

/**
 * 一覧画面
 * @author agata
 * @author baba
 */
@Url("todo")
public class TodoListAction extends Action {

	// ----------------------------------------------[Validation]

	public ValidationRules validation = new DefaultValidationRules() {
		@Override
		public void initialize() {
			add("limitDate", new DateFormatValidator());
		}
	};

	// ----------------------------------------------[DI Filed]

	public FormatPattern formatPattern;

	public TodoDao todoDao;

	public TodoTypeDao todoTypeDao;

	// ----------------------------------------------[Attribute]

	public TodoConditionDto todoConditionDto;

	public List<Todo> todoList;

	// ----------------------------------------------[Action Method]

	/**
	 * 一覧の表示(/todo/)
	 */
	@Form("todoConditionDto")
	@Validation(rules="validation", errorPage="list.jsp")
	public ActionResult index() {
		this.todoList = todoDao.selectByCondition(todoConditionDto);
		return new Forward("list.jsp");
	}

	// ----------------------------------------------[Helper Method]

	/**
	 * 種別一覧の取得
	 */
	public List<TodoType> getTodoTypes() {
		List<TodoType> todoTypes = todoTypeDao.seletAll();
		return todoTypes;
	}

	// ----------------------------------------------[Helper Method]

	/**
	 * 検索条件の文字列を取得
	 */
	public String getQueryString() {
		StringBuilder sb = new StringBuilder();
		if (todoConditionDto.hasKeyword()) {
			sb.append("キーワード:").append(todoConditionDto.getKeyword()).append(
					" ");
		}
		if (todoConditionDto.hasTypeId()) {
			sb.append("種別:").append(
					todoTypeDao.selectById(todoConditionDto.getTypeId())
							.getName()).append(" ");
		}
		if (todoConditionDto.hasLimitDate()) {
			DateFormat dateFormat = formatPattern.getDateFormat();
			sb.append("期限日<=").append(
					dateFormat.format(todoConditionDto.getLimitDate()));
		}
		return sb.toString();
	}

}