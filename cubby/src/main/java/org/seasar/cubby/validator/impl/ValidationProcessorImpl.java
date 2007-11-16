package org.seasar.cubby.validator.impl;

import java.util.Map;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.validator.ValidationProcessor;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;

public class ValidationProcessorImpl implements ValidationProcessor {

	private CubbyConfiguration cubbyConfiguration;

	public void setCubbyConfiguration(
			final CubbyConfiguration cubbyConfiguration) {
		this.cubbyConfiguration = cubbyConfiguration;
	}

	public boolean process(
			final ActionErrors errors, final Map<String, Object[]> params,
			final Object form, final ValidationRules rules) {
		for (final ValidationRule rule : rules.getRules()) {
			rule.apply(params, form, errors, cubbyConfiguration);
		}
		return errors.isEmpty();
	}

}
