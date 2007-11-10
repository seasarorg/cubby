package org.seasar.cubby.examples.other.web.token;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.TokenValidator;

public class TokenAction extends Action {


	// ----------------------------------------------[Validation]

	public ValidationRules validation = new DefaultValidationRules() {
		@Override
		public void initialize() {
			add("cubby.token", new TokenValidator());
		}
	};

	public String name;
	
	public ActionResult index() {
		return new Forward("token.jsp");
	}


	@Form
	@Validation(errorPage="token.jsp", rules = "validation")
	public ActionResult message() {
		return new Forward("result.jsp");
	}
	
}
