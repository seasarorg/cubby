package org.seasar.cubby.examples.todo.controller;

import java.util.List;

import org.seasar.cubby.annotation.Filter;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.results.Forward;
import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.examples.todo.logic.UserLogic;


@Filter({AuthActionFilter.class})
public class UserController extends Controller {
	
	// ----------------------------------------------[DI Filed]

	UserLogic userLogic;

	// ----------------------------------------------[Attribute]

	public List<User> items;
	public User item;

	// ----------------------------------------------[Action Method]

	public ActionResult list() {
		if (items == null) {
			items = userLogic.findAll();
		}
		return new Forward("list.jsp");
	}
	
	public ActionResult show() {
		item = userLogic.findById(params.toInt("id"));
		return new Forward("show.jsp");
	}

	// ----------------------------------------------[Helper Method]

}