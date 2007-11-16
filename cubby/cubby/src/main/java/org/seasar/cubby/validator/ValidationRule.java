package org.seasar.cubby.validator;

import java.util.Map;

import org.seasar.cubby.action.ActionErrors;

public interface ValidationRule {

	void apply(Map<String, Object[]> params, Object form, ActionErrors errors);

}
