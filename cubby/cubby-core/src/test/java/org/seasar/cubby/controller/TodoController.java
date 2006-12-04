package org.seasar.cubby.controller;

import org.seasar.cubby.annotation.Url;

public class TodoController extends Controller {

	@Url("/{name}/todo/{id}")
	public String show() {
		return null;
	}	

	@Url("/{name}/todo/edit/{id}")
	public String edit() {
		return "edit.jsp";
	}	

}
