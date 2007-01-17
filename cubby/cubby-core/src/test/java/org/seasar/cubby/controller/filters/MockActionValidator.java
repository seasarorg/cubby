package org.seasar.cubby.controller.filters;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.ActionValidator;
import org.seasar.cubby.validator.Validators;

public class MockActionValidator implements ActionValidator {

	public boolean processValidationResult = false;
	
	public boolean processValidation(Validation valid, Action controller,
			Object form, Validators validators) {
		return processValidationResult;
	}

}
