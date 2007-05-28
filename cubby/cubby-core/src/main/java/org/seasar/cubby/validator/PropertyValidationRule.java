package org.seasar.cubby.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PropertyValidationRule implements ValidationRule {

	private final String propertyName;
	private String propertyNameKey;
	private final List<Validator> validators = new ArrayList<Validator>();

	public PropertyValidationRule(final String propertyName, final Validator... validators) {
		this(propertyName, propertyName, validators);
	}

	public PropertyValidationRule(final String propertyName, final String properyNameKey, final Validator... validators) {
		this.propertyName = propertyName;
		this.propertyNameKey = properyNameKey;
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

	public Collection<Validator> getValidators() {
		return validators;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
