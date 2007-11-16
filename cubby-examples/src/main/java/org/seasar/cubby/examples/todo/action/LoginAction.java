package org.seasar.cubby.examples.todo.action;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

/**
 * ログイン画面
 * 
 * @author agata
 * @author baba
 */
@Url("todo/login")
public class LoginAction extends Action {

	// ----------------------------------------------[Validation]

	public ValidationRules loginValidation = new DefaultValidationRules(
			"login.") {
		@Override
		public void initialize() {
			add("userId", new RequiredValidator());
			add("password", new RequiredValidator());
			add(new UserValidationRule());
		}
	};

	// ----------------------------------------------[DI Filed]

	public HttpSession session;

	public Map<String, Object> sessionScope;

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
	@Form
	@Validation(rules = "loginValidation", errorPage = "/todo/login.jsp")
	public ActionResult process() {
		return new Redirect("/todo/");
	}

	private class UserValidationRule implements ValidationRule {

		public void apply(Map<String, Object[]> params, Object form,
				ActionErrors errors, CubbyConfiguration cubbyConfiguration) {
			if (userId == null || password == null) {
				return;
			}
			User user = findUser(userId, password);
			if (user != null) {
				sessionScope.put("user", user);
			} else {
				errors.add("ユーザIDかパスワードが違います。", "userId", "password");
			}
		}

		private User findUser(String userId, String password) {
			User user;
			if ("test".equals(userId) && "test".equals(password)) {
				user = new User();
				user.setId(1);
				user.setName("Cubby");
			} else {
				user = null;
			}
			return user;
		}
	}

	@Url("/todo/logout")
	// @InvalidateSession : s2-framework 2.4.18 から
	public ActionResult logout() {
		session.invalidate(); // s2-framework 2.4.17 まで
		return new Redirect("/todo/login/");
	}

	// ----------------------------------------------[Helper Method]

}