package org.seasar.cubby.validator.impl;

import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

import org.apache.commons.lang.StringUtils;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.validator.ActionValidator;
import org.seasar.cubby.validator.PropertyValidationRule;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.Validator;
import org.seasar.framework.exception.OgnlRuntimeException;

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
		String[] props = StringUtils.split(propertyName, ".", 2);
		Object value = CubbyUtils.getParamsValue(params, props[0]);
		if (props.length == 1) {
			return value;
		} else {
			try {
				Object nestedValue = Ognl.getValue(props[0], value);
				return nestedValue;
			} catch (OgnlException e) {
				throw new OgnlRuntimeException(e);
			}
		}
	}
}
