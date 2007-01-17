package org.seasar.cubby.examples.todo.action;

import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.examples.todo.logic.UserLogic;


public class UserAction extends Action {
	
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