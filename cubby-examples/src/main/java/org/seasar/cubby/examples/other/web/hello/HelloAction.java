package org.seasar.cubby.examples.other.web.hello;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;

public class HelloAction extends Action {

	public String name;

	public ActionResult index() {
		System.out.println("call HelloAction#index()");
		return new Forward("input.jsp");
	}

	public ActionResult message() {
		System.out.println("call HelloAction#message()");
		return new Forward("result.jsp");
	}

}
