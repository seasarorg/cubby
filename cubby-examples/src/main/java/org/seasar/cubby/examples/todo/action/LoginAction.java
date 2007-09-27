package org.seasar.cubby.examples.todo.action;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

@Url("todo/login")
public class LoginAction extends Action {

	// ----------------------------------------------[Validation]

	public ValidationRules loginValidation = new DefaultValidationRules(
			"login.") {
		@Override
		public void initialize() {
			add("userId", new RequiredValidator());
			add("password", new RequiredValidator());
		}
	};

	// ----------------------------------------------[DI Filed]

	public HttpServletRequest request;

	// ----------------------------------------------[Attribute]

	public String userId;

	public String password;

	// ----------------------------------------------[Action Method]

	public ActionResult index() {
		return new Forward("/todo/login.jsp");
	}

	@Form
	@Validation(rulesField="loginValidation", errorPage = "/todo/login.jsp")
	public ActionResult process() {
		User user = login(userId, password);
		if (user != null) {
			request.getSession().setAttribute("user", user);
			return new Redirect("/todo/");
		} else {
			errors.addActionError("ユーザIDかパスワードが違います。");
			return new Forward("/todo/login.jsp");
		}
	}

	private User login(String userId, String password) {
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

	@Url("/todo/logout")
	public ActionResult logout() {
		request.getSession().invalidate();
		return new Redirect("/todo/login/");
	}

	// ----------------------------------------------[Helper Method]

}