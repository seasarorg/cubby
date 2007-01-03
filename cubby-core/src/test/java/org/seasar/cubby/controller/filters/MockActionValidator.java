package org.seasar.cubby.controller.filters;

import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.validator.ActionValidator;
import org.seasar.cubby.validator.Validators;

public class MockActionValidator implements ActionValidator {

	public boolean processValidationResult = false;
	
	public boolean processValidation(Validation valid, Controller controller,
			Object form, Validators validators) {
		return processValidationResult;
	}

}
