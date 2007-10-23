package org.seasar.cubby.validator;

import java.util.ArrayList;
import java.util.List;

public class PropertyValidationRule implements ValidationRule {

	private final String propertyName;
	private String propertyNameKey;
	private final List<Validator> validators = new ArrayList<Validator>();

	public PropertyValidationRule(final String propertyName, final Validator... validators) {
		this(propertyName, propertyName, validators);
	}

	public PropertyValidationRule(final String propertyName, final String propertyNameKey, final Validator... validators) {
		this.propertyName = propertyName;
		this.propertyNameKey = propertyNameKey;
		for (Validator v : validators) {
			this.validators.add(v);
		}
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getPropertyNameKey() {
		return propertyNameKey;
	}

	public List<Validator> getValidators() {
		return validators;
	}

}
