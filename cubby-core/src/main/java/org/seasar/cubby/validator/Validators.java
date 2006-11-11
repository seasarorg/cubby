package org.seasar.cubby.validator;

import java.util.Collection;
import java.util.LinkedHashMap;

public class Validators {

	private LinkedHashMap<String, PropertyValidators> validators = new LinkedHashMap<String, PropertyValidators>();

	public Validators add(String propertyName, Validator... validator) {
		if (!validators.containsKey(propertyName)) {
			validators.put(propertyName, new PropertyValidators(propertyName));
		}
		PropertyValidators propValidators = validators.get(propertyName);
		propValidators.add(validator);
		return this;
	}

	public Validators add(String propName, String labelKey,
			Validator... validator) {
		if (!validators.containsKey(propName)) {
			PropertyValidators propValids = new PropertyValidators(propName,
					labelKey);
			validators.put(propName, propValids);
		}
		PropertyValidators propValids = validators.get(propName);
		propValids.add(validator);
		return this;
	}

	public PropertyValidators getValidators(String propertyName) {
		return validators.get(propertyName);
	}

	public Collection<PropertyValidators> getValidators() {
		return validators.values();
	}

}
