package org.seasar.cubby.validator;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Validation;

public interface ActionValidator {
	
	boolean processValidation(Validation valid, Action controller, Object form, Validators validators);

}
