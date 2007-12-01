package ${package}.action;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

import ${package}.service.HelloService;

public class HelloAction extends Action {

	public ValidationRules validation = new DefaultValidationRules() {
		public void initialize() {
			add("name", new RequiredValidator());
		}
	};

	public HelloService helloService;

	public String name;

	public String message;

	public ActionResult index() {
		return new Forward("index.jsp");
	}

	@Validation(rules = "validation", errorPage = "index.jsp")
	public ActionResult message() {
		this.message = this.name + " " + helloService.getMessage();
		return new Forward("hello.jsp");
	}

	public ActionResult back() {
		flash.put("notice", "Redirect OK!(this message is flash message)");
		return new Redirect("/hello/");
	}

}