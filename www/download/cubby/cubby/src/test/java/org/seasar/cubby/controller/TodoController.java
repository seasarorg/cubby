package org.seasar.cubby.controller;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Url;

public class TodoController extends Action {

	@Url("/{name}/todo/{id}")
	public String show() {
		return null;
	}	

	@Url("/{name}/todo/edit/{id}")
	public String edit() {
		return "edit.jsp";
	}	

}
