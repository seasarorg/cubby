package org.seasar.cubby.controller.filters;

import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.util.Map;

import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.results.Forward;
import org.seasar.cubby.convert.Populater;
import org.seasar.cubby.validator.ActionValidator;

public class ValidationFilter implements ActionFilter {

	private ActionValidator actionValidator;
	private final Populater populater;
	public ValidationFilter(ActionValidator actionValidator, Populater populater) {
		this.actionValidator = actionValidator;
		this.populater = populater;
	}
	
	public ActionResult doFilter(ActionContext action, ActionFilterChain chain)
			throws Throwable {
		boolean success = actionValidator.processValidation(action
				.getValidation(), action.getController(), action.getFormBean(),
				action.getValidators());
		if (success) {
			setupForm(action);
			return chain.doFilter(action);
		} else {
			action.getRequest().setAttribute(ATTR_VALIDATION_FAIL, true);
			Validation valid = action.getValidation();
			setupForm(action);
			return new Forward(valid.errorPage());
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	private void setupForm(ActionContext action) {
		Map<String, Object> params = action.getController().getParams().getOriginalParameter();
		Object formBean = action.getFormBean();
		if (formBean != null) {
			populater.populate(formBean, params);
		}
	}
}
