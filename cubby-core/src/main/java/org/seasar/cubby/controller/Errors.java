package org.seasar.cubby.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Errors {

	private List<String> actionErrors = new ArrayList<String>();

	private Map<String, List<String>> fieldErrors = new LinkedHashMap<String, List<String>>();

	public boolean isEmpty() {
		return actionErrors.isEmpty() && fieldErrors.isEmpty();
	}

	public void addActionErrors(String message) {
		actionErrors.add(message);
	}

	public List<String> getErrors() {
		List<String> errors = new ArrayList<String>();
		errors.addAll(actionErrors);
		for (List<String> errorList : fieldErrors.values()) {
			errors.addAll(errorList);
		}
		return errors;
	}

	public Map<String, List<String>> getFieldErrors() {
		return fieldErrors;
	}

	public void addFieldError(String name, String message) {
		if (!fieldErrors.containsKey(name)) {
			fieldErrors.put(name, new ArrayList<String>());
		}
		fieldErrors.get(name).add(message);
	}

	public boolean hasFieldError(String name) {
		return fieldErrors.containsKey(name);
	}

}