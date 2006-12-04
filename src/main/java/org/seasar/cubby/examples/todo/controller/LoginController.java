package org.seasar.cubby.examples.todo.controller;

import org.seasar.cubby.annotation.Form;
import org.seasar.cubby.annotation.Session;
import org.seasar.cubby.annotation.Url;
import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.results.Forward;
import org.seasar.cubby.controller.results.Redirect;
import org.seasar.cubby.examples.todo.dto.LoginDto;
import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.examples.todo.logic.TodoLogic;


@Url("")
@Form("loginDto")
public class LoginController extends Controller {

	// ----------------------------------------------[DI Filed]

	TodoLogic todoLogic;

	// ----------------------------------------------[Attribute]

	public LoginDto loginDto = new LoginDto();
	@Session
	public User user;
	
	// ----------------------------------------------[Action Method]

	public ActionResult login() {
		return new Forward("login.jsp");
	}
	
	@Validation(errorPage = "login.jsp")
	public ActionResult login_process() {
		if(todoLogic.login(loginDto)) {
			user = new User(1, "Cubby");
			return new Redirect("/todo/list");
		} else {
			errors.addActionError("ユーザIDかパスワードが違います。");
			return new Forward("login.jsp");
		}
	}

	public ActionResult logout() {
		session.clear();
		return new Redirect("/login");
	}
	
	// ----------------------------------------------[Helper Method]

}