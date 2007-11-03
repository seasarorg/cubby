package org.seasar.cubby.action;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;

import org.seasar.cubby.service.HelloService;

public class HelloAction extends Action {

	public HelloService helloService;

	public String message;

	public ActionResult hello() {
		this.message = helloService.getMessage();
		return new Forward("hello.jsp");
	}

}