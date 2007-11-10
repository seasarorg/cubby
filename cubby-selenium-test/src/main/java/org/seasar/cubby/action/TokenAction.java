package org.seasar.cubby.action;

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
