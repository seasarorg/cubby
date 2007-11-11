package org.seasar.cubby.validator.impl;

import java.util.Map;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
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
		ValidationContext context = createValidContext(action, params, form,
				rule, values);

		validator.validate(context);
		if (context.hasError()) {
			final ActionErrors errors = action.getErrors();
			for (final String message : context.getMessages()) {
				errors.addFieldError(propRule.getPropertyName(), message);
			}
		}
	}

	private ValidationContext createValidContext(final Action action,
			final Map<String, Object[]> params, final Object form,
			final ValidationRule rule, Object[] value) {
		// TODO PropertyValidationRule以外の実装を認めていないので、そのうち修正
		PropertyValidationRule propRule = (PropertyValidationRule) rule;
		String name = propRule.getPropertyNameKey();
		ValidationContext ctx = new ValidationContext(name, value, params,
				cubbyConfiguration.getFormatPattern());
		return ctx;
	}

}
