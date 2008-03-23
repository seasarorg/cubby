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

import java.util.Map;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.examples.todo.dao.UserDao;
import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.seasar.framework.aop.annotation.InvalidateSession;

/**
 * ログイン画面
 * 
 * @author agata
 * @author baba
 */
@Path("todo/login")
public class LoginAction extends Action {

	// ----------------------------------------------[Validation]

	public ValidationRules loginValidation = new DefaultValidationRules(
			"login.") {
		@Override
		public void initialize() {
			add("userId", new RequiredValidator());
			add("password", new RequiredValidator());
			add(DATA_CONSTRAINT, new UserValidationRule());
		}
	};

	// ----------------------------------------------[DI Filed]

	public Map<String, Object> sessionScope;

	public UserDao userDao;

	// ----------------------------------------------[Attribute]

	public String userId;

	public String password;

	// ----------------------------------------------[Action Method]

	/**
	 * ログイン画面表示処理(/todo/login/)
	 */
	public ActionResult index() {
		return new Forward("/todo/login.jsp");
	}

	/**
	 * ログイン処理(/todo/login/process)
	 */
	@Validation(rules = "loginValidation", errorPage = "/todo/login.jsp")
	public ActionResult process() {
		return new Redirect("/todo/");
	}

	private class UserValidationRule implements ValidationRule {

		public void apply(Map<String, Object[]> params, Object form,
				ActionErrors errors) {
			User user = userDao.findByIdAndPassword(userId, password);
			if (user == null) {
				throw new ValidationException("ユーザIDかパスワードが違います。", "userId",
						"password");
			}
			sessionScope.put("user", user);
		}

	}

	@Path("/todo/logout")
	@InvalidateSession
	public ActionResult logout() {
		return new Redirect("/todo/login/");
	}

	// ----------------------------------------------[Helper Method]

}