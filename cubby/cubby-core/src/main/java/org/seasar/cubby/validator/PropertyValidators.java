package org.seasar.cubby.validator;

import java.util.ArrayList;
import java.util.List;

public class PropertyValidators {

	private final String propertyName;

	private final String labelKey;

	private List<Validator> validators = new ArrayList<Validator>();

	public PropertyValidators(String propertyName) {
		this(propertyName, propertyName);
	}

	public PropertyValidators(String propertyName, String labelKey) {
		this.propertyName = propertyName;
		this.labelKey = labelKey;
	}

	public void add(Validator[] validator) {
		for (Validator v : validator) {
			validators.add(v);
		}
	}

	public String getLabelKey() {
		return labelKey;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public List<Validator> getValidators() {
		return validators;
	}
}