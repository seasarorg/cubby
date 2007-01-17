package org.seasar.cubby.validator.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.ActionValidator;
import org.seasar.cubby.validator.LabelKey;
import org.seasar.cubby.validator.PropertyValidators;
import org.seasar.cubby.validator.ValidContext;
import org.seasar.cubby.validator.Validator;
import org.seasar.cubby.validator.Validators;

public class ActionValidatorImpl implements ActionValidator {
	
	public boolean processValidation(Validation valid, Action controller, Object form, Validators validators) {
		if (valid == null) {
			return true;
		}
		validateAction(controller, form, validators);
		return controller.getErrors().isEmpty();
	}

	@SuppressWarnings("unchecked")
	void validateAction(Action controller, Object form, Validators validators) {
		for (PropertyValidators propValids : validators.getValidators()) {
			for (Validator v : propValids.getValidators()) {
				validate(controller, form, v, propValids);
			}
		}
	}

	void validate(Action controller, Object form, Validator v,
			PropertyValidators propValids) {
		ValidContext context = createValidContext(controller, form, propValids);
		Object value = getPropertyValue(controller.getParams(), propValids.getPropertyName());
		String error = v.validate(context, value);
		if (error != null) {
			controller.getErrors().addFieldError(propValids.getPropertyName(),
					error);
		}
	}

	private ValidContext createValidContext(Action controller, Object form, PropertyValidators propValids) {
		String name = getLabelKey(form, propValids.getLabelKey());
		Map params = controller.getParams().getOriginalParameter();
		ValidContext context = new ValidContext(name, params);
		return context;
	}

	Object getPropertyValue(Map params, String propertyName) {
		String[] props = StringUtils.split(propertyName, ".", 2);
		Object value = params.get(props[0]);
		if (props.length == 1) {
			return value;
		} else {
			try {
				return BeanUtils.getNestedProperty(value, props[1]);
			} catch (Exception e) {
				return null;
			}
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
