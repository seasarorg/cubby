package org.seasar.cubby.controller;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Path;

public class TodoController extends Action {

	@Path("/{name}/todo/{id}")
	public String show() {
		return null;
	}	

	@Path("/{name}/todo/edit/{id}")
	public String edit() {
		return "edit.jsp";
	}	

}
