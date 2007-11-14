package ${package}.action;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

import ${package}.service.HelloService;

public class HelloAction extends Action {

	public ValidationRules validation = new DefaultValidationRules() {
		public void initialize() {
			add("message", new RequiredValidator());
		}
	};

	public HelloService helloService;

	public String message;

	@Validation(rulesField = "validation", errorPage = "/index.jsp")
	public ActionResult hello() {
		this.message = helloService.getMessage();
		flash.put("notice", getText("msg.dummy"));
		return new Forward("hello.jsp");
	}
}