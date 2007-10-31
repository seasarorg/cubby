package org.seasar.cubby.validator;

import java.util.Map;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Validation;

public interface ActionValidator {
	
	boolean processValidation(Validation valid, Action action, Map<String,Object> params, Object form, ValidationRules rules);

}
