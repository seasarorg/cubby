package org.seasar.cubby.exmaple.gae.action;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.ActionClass;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.FlashMap;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.exmaple.gae.service.HelloService;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
@ActionClass
public class HelloAction {

	ValidationRules validation = new DefaultValidationRules() {
		@Override
		public void initialize() {
			add("name", new RequiredValidator());
		}
	};

	@Inject
	private FlashMap flashMap;

	@Inject
	private HelloService helloService;

	@RequestParameter
	private String name;

	private String message;

	public String getMessage() {
		return message;
	}

	@Accept(RequestMethod.GET)
	public ActionResult index() {
		return new Forward("index.jsp");
	}

	@Accept(RequestMethod.POST)
	@Validation(rules = "validation", errorPage = "index.jsp")
	public ActionResult message() {
		this.message = this.name + " " + helloService.getMessage();
		return new Forward("hello.jsp");
	}

	public ActionResult back() {
		flashMap.put("notice", "Redirect OK!(this message is flash message)");
		return new Redirect("/hello/");
	}
}
