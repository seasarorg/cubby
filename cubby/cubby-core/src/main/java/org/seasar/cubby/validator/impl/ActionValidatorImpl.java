package org.seasar.cubby.validator.impl;

import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.validator.ActionValidator;
import org.seasar.cubby.validator.LabelKey;
import org.seasar.cubby.validator.PropertyValidators;
import org.seasar.cubby.validator.ValidContext;
import org.seasar.cubby.validator.Validator;
import org.seasar.cubby.validator.Validators;

public class ActionValidatorImpl implements ActionValidator {
	
	public boolean processValidation(Validation valid, Controller controller, Object form, Validators validators) {
		if (valid == null) {
			return true;
		}
		validateAction(controller, form, validators);
		return controller.getErrors().isEmpty();
	}

	@SuppressWarnings("unchecked")
	void validateAction(Controller controller, Object form, Validators validators) {
		for (PropertyValidators propValids : validators.getValidators()) {
			for (Validator v : propValids.getValidators()) {
				validate(controller, form, v, propValids);
			}
		}
	}

	void validate(Controller controller, Object form, Validator v,
			PropertyValidators propValids) {
		ValidContext context = new ValidContext();
		context.setName(getLabelKey(form, propValids.getLabelKey()));
		Object value = controller.getParams().get(propValids.getPropertyName());
		String error = v.validate(context, value);
		if (error != null) {
			controller.getErrors().addFieldError(propValids.getPropertyName(),
					error);
		}
	}

	String getLabelKey(Object form, String labelKey) {
		StringBuilder buf = new StringBuilder();
		LabelKey formResource = form.getClass().getAnnotation(LabelKey.class);
		String fieldResource = labelKey;
		if (formResource != null) {
			buf.append(formResource.value());
		}
		if (fieldResource != null) {
			buf.append(fieldResource);
		} else {
			buf.append(labelKey);
		}
		return buf.toString();
	}
}
