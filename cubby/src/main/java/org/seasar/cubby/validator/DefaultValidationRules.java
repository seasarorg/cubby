package org.seasar.cubby.validator;

import java.util.ArrayList;
import java.util.List;

public class DefaultValidationRules implements ValidationRules {

	public final List<ValidationRule> rules = new ArrayList<ValidationRule>();
	private final String resourceKeyPrefix;

	public DefaultValidationRules() {
		this(null);
	}

	public DefaultValidationRules(final String resourceKeyPrefix) {
		this.resourceKeyPrefix = resourceKeyPrefix;
		initialize();
	}
	
	public void initialize() {
	}
	
	protected void add(final ValidationRule rule) {
		rules.add(rule);
	}

	public void add(final String propertyName, final Validator... validators) {
		add(propertyName, propertyName, validators);
	}
	
	public void add(final String propertyName, final String propertyNameKey, final Validator... validators) {
		add(new PropertyValidationRule(propertyName, makePropertyNameKey(propertyName, propertyNameKey), validators));
	}

	private String makePropertyNameKey(final String propertyName, final String propertyNameKey) {
		if (resourceKeyPrefix == null) {
			return propertyNameKey;
		} else {
			return resourceKeyPrefix + propertyNameKey;
		}
	}
	
	public List<ValidationRule> getRules() {
		return rules;
	}

}
