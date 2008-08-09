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

import static org.seasar.cubby.action.RequestParameterBindingType.NONE;

import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
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
 * 
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

	@RequestParameter
	public Integer id;

	@RequestParameter
	public String text;

	@RequestParameter
	public String memo;

	@RequestParameter
	public Integer typeId;

	@RequestParameter
	public String limitDate;

	public TodoType todoType;

	// ----------------------------------------------[Action Method]

	/**
	 * 詳細表示処理(/todo/{id})
	 */
	@Path("{id,[0-9]+}")
	public ActionResult show() {
		Todo todo = todoDao.selectById(this.id);
		todoDxo.convert(todo, this);
		return new Forward("show.jsp");
	}

	/**
	 * 追加表示処理(/todo/create)
	 */
	public ActionResult create() {
		return new Forward("edit.jsp");
	}

	/**
	 * 編集表示処理(/todo/edit)
	 */
	public ActionResult edit() {
		Todo todo = todoDao.selectById(this.id);
		todoDxo.convert(todo, this);
		return new Forward("edit.jsp");
	}

	/**
	 * 確認表示処理(/todo/confirm)
	 */
	@Validation(rules = "validation", errorPage = "edit.jsp")
	public ActionResult confirm() {
		TodoType todoType = todoTypeDao.selectById(this.typeId);
		this.todoType = todoType;
		return new Forward("confirm.jsp");
	}

	/**
	 * 編集画面に戻る処理(/todo/confirm_back)
	 */
	@Form(bindingType = NONE)
	public ActionResult confirm_back() {
		return new Forward("edit.jsp");
	}

	/**
	 * 保存処理(/todo/save)
	 */
	@Validation(rules = "validation", errorPage = "confirm.jsp")
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