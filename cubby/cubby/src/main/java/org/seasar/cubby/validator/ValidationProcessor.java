package org.seasar.cubby.validator;

import java.util.Map;

import org.seasar.cubby.action.ActionErrors;

/**
 * 
 * @author baba
 * 
 */
public interface ValidationProcessor {

	boolean process(ActionErrors errors, Map<String, Object[]> params,
			Object form, ValidationRules rules);

}
