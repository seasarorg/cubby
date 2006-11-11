package org.seasar.cubby.controller.filters;

import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.validator.ActionValidator;

public class ValidationFilter implements ActionFilter {

	private ActionValidator actionValidator;
	public ValidationFilter(ActionValidator actionValidator) {
		this.actionValidator = actionValidator;
	}

	public String doFilter(ActionContext action, ActionFilterChain chain)
			throws Throwable {
		boolean success = actionValidator.processValidation(action
				.getValidation(), action.getController(), action.getFormBean(),
				action.getValidators());
		if (success) {
			return chain.doFilter(action);
		} else {
			action.getRequest().setAttribute(ATTR_VALIDATION_FAIL, true);
			Validation valid = action.getValidation();
			return valid.errorPage();
		}
	}
	
}
