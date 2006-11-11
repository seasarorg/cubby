package org.seasar.cubby.validator;

import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.Controller;

public interface ActionValidator {
	
	boolean processValidation(Validation valid, Controller controller, Object form, Validators validators);

}
