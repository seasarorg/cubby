package org.seasar.cubby.examples.todo.action;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Session;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.examples.todo.logic.TodoLogic;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

@Url("todo")
public class LoginAction extends Action {

	public static ValidationRules VALIDATION = new DefaultValidationRules("login.") {
		public void initialize() {
			add("userId", new RequiredValidator());
			add("password", new RequiredValidator());
		}
	};

	// ----------------------------------------------[DI Filed]

	private TodoLogic todoLogic;
	private HttpServletRequest request;
	
	// ----------------------------------------------[Property]

	private String userId;
	private String password;
	@Session
	public User user;
	
	// ----------------------------------------------[Setter&Getter]

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public TodoLogic getTodoLogic() {
		return todoLogic;
	}

	public void setTodoLogic(TodoLogic todoLogic) {
		this.todoLogic = todoLogic;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// ----------------------------------------------[Action Method]

	public ActionResult login() {
		return new Forward("login.jsp");
	}
	
	@Form
	@Validation(errorPage = "login.jsp")
	public ActionResult login_process() {
		if(todoLogic.login(userId, password)) {
			user = new User(1, "Cubby");
			return new Redirect("list");
		} else {
			errors.addActionError("ユーザIDかパスワードが違います。");
			return new Forward("login.jsp");
		}
	}

	public ActionResult logout() {
		request.getSession().invalidate();
		return new Redirect("login");
	}
	
	// ----------------------------------------------[Helper Method]

}