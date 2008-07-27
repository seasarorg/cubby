package org.seasar.cubby.examples.other.web.dispatch;

import static org.seasar.cubby.action.RequestParameterBindingType.ALL_PROPERTIES;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.OnSubmit;
import org.seasar.cubby.action.Path;

public class DispatchAction extends Action {

	public String button1;

	public String button2;

	public String message;

	public ActionResult index() {
		return new Forward("index.jsp");
	}

	@Path("execute")
	@Form(type = ALL_PROPERTIES)
	@OnSubmit("button1")
	public ActionResult method1() {
		this.message = "Invoke method1, @OnSubmit(\"button1\")";
		return new Forward("index.jsp");
	}

	@Path("execute")
	@Form(type = ALL_PROPERTIES)
	@OnSubmit("button2")
	public ActionResult method2() {
		this.message = "Invoke method2, @OnSubmit(\"button2\")";
		return new Forward("index.jsp");
	}

	@Path("execute")
	@Form(type = ALL_PROPERTIES)
	public ActionResult method3() {
		this.message = "Invoke method3, No @OnSubmit";
		return new Forward("index.jsp");
	}

}
