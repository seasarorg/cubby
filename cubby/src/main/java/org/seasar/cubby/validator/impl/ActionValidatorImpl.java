package org.seasar.cubby.validator.impl;

import java.util.Map;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.validator.ActionValidator;
import org.seasar.cubby.validator.PropertyValidationRule;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.Validator;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class ActionValidatorImpl implements ActionValidator {
	
	public boolean processValidation(final Validation valid, 
			final Action action, final Map<String,Object> params, 
			final Object form, final ValidationRules rules) 
	{
		if (valid == null) {
			return true;
		}
		validateAction(action, params, form, rules);
		return action.getErrors().isEmpty();
	}

	@SuppressWarnings("unchecked")
	void validateAction(final Action action, final Map<String,Object> params, 
			final Object form, final ValidationRules rules) 
	{
		for (ValidationRule rule : rules.getRules()) {
			for (Validator v : rule.getValidators()) {
				validate(action, params, form, v, rule);
			}
		}
	}

	void validate(final Action action, final Map<String, Object> params, 
			final Object form, final Validator validator, final ValidationRule rule) 
	{
		// TODO PropertyValidationRule以外の実装を認めていないので、そのうち修正
		PropertyValidationRule propRule = (PropertyValidationRule)rule;
		Object value = getPropertyValue(params, propRule.getPropertyName());
		ValidationContext ctx = createValidContext(action, params, form, rule, value);
		String error = validator.validate(ctx);
		if (error != null) {
			action.getErrors().addFieldError(propRule.getPropertyName(),
					error);
		}
	}

	private ValidationContext createValidContext(final Action action, 
			final Map<String, Object> params, final Object form, final ValidationRule rule, Object value) 
	{
		// TODO PropertyValidationRule以外の実装を認めていないので、そのうち修正
		PropertyValidationRule propRule = (PropertyValidationRule)rule;
		String name = propRule.getPropertyNameKey();
		ValidationContext ctx = new ValidationContext(name, value, params);
		return ctx;
	}

	Object getPropertyValue(final Map<String, Object> params, final String propertyName) {
		String[] props = propertyName.split("\\.");
		Object value = CubbyUtils.getParamsValue(params, props[0]);
		for (int i = 1; i < props.length; i++) {
			BeanDesc beanDesc = BeanDescFactory.getBeanDesc(value.getClass());
			PropertyDesc propertyDesc = beanDesc.getPropertyDesc(props[i]);
			value = propertyDesc.getValue(value);
		}
		return value;
	}
}
