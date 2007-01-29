package org.seasar.cubby.examples.hello;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;

public class HelloAction extends Action {

	private String name;
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ActionResult index() {
		return new Forward("input.jsp");
	}

	@Form
	public ActionResult message() {
		return new Forward("result.jsp");
	}
}
