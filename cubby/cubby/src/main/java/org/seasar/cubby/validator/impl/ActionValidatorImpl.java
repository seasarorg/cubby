package org.seasar.cubby.validator.impl;

import java.util.Map;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.validator.ActionValidator;
import org.seasar.cubby.validator.PropertyValidationRule;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.Validator;

public class ActionValidatorImpl implements ActionValidator {

	private CubbyConfiguration cubbyConfiguration;

	public void setCubbyConfiguration(
			final CubbyConfiguration cubbyConfiguration) {
		this.cubbyConfiguration = cubbyConfiguration;
	}

	public boolean processValidation(final Validation valid,
			final Action action, final Map<String, Object[]> params,
			final Object form, final ValidationRules rules) {
		if (valid == null) {
			return true;
		}
		validateAction(action, params, form, rules);
		return action.getErrors().isEmpty();
	}

	@SuppressWarnings("unchecked")
	void validateAction(final Action action, final Map<String, Object[]> params,
			final Object form, final ValidationRules rules) {
		for (ValidationRule rule : rules.getRules()) {
			for (Validator validator : rule.getValidators()) {
				validate(action, params, form, validator, rule);
			}
		}
	}

	void validate(final Action action, final Map<String, Object[]> params,
			final Object form, final Validator validator,
			final ValidationRule rule) {
		// TODO PropertyValidationRule以外の実装を認めていないので、そのうち修正
		final PropertyValidationRule propRule = (PropertyValidationRule) rule;
		Object[] values = params.get(propRule.getPropertyName());
		if (values == null) {
			values = new Object[] { "" };
		}
		if (values != null) {
			for (Object value : values) {
				ValidationContext context = createValidContext(action, params,
						form, rule, value);
				String error = validator.validate(context);
				if (error != null) {
					action.getErrors().addFieldError(
							propRule.getPropertyName(), error);
				}
			}
		}
	}

	private ValidationContext createValidContext(final Action action,
			final Map<String, Object[]> params, final Object form,
			final ValidationRule rule, Object value) {
		// TODO PropertyValidationRule以外の実装を認めていないので、そのうち修正
		PropertyValidationRule propRule = (PropertyValidationRule) rule;
		String name = propRule.getPropertyNameKey();
		ValidationContext ctx = new ValidationContext(name, value, params,
				cubbyConfiguration.getFormatPattern());
		return ctx;
	}

//	String[] names(final Map<String, Object[]> params, final String name) {
//		String[] names = name.split("\\.");
//		return names;
//	}
//
//	Object getPropertyValue(final Map<String, Object[]> params,
//			final String propertyName) {
//		String[] props = propertyName.split("\\.");
//		Object[] values = params.get(props[0]);
//		for (int i = 1; i < props.length; i++) {
//			BeanDesc beanDesc = BeanDescFactory.getBeanDesc(value.getClass());
//			PropertyDesc propertyDesc = beanDesc.getPropertyDesc(props[i]);
//			value = propertyDesc.getValue(value);
//		}
//		return value;
//	}

}
