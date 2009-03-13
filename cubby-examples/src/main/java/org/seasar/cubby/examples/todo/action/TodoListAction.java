/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.examples.todo.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.FormatPattern;
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
 * 
 * @author agata
 * @author baba
 */
@Path("todo")
public class TodoListAction {

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
	@Validation(rules = "validation", errorPage = "list.jsp")
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
			DateFormat dateFormat = new SimpleDateFormat(formatPattern
					.getDatePattern());
			sb.append("期限日<=").append(
					dateFormat.format(todoConditionDto.getLimitDate()));
		}
		return sb.toString();
	}

}
