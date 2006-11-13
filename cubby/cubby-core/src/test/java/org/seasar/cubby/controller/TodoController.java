package org.seasar.cubby.controller;

import org.seasar.cubby.annotation.Url;

public class TodoController extends Controller {

	@Url(alias="/{name}/todo/{id}")
	public String show() {
		return null;
	}	

	@Url(alias="/{name}/todo/edit/{id}")
	public String edit() {
		return "edit.jsp";
	}	

}
