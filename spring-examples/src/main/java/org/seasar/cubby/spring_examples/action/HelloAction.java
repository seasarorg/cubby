package org.seasar.cubby.spring_examples.action;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.spring_examples.service.HelloService;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Scope(WebApplicationContext.SCOPE_REQUEST)
public class HelloAction extends Action {

	private ValidationRules validation = new DefaultValidationRules() {
		@Override
		public void initialize() {
			add("name", new RequiredValidator());
		}
	};

	@Autowired
	private HelloService helloService;

	private String name;

	private String message;

	public ValidationRules getValidation() {
		return validation;
	}

	public String getName() {
		return name;
	}

	@RequestParameter
	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

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