package org.seasar.cubby.action.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.seasar.cubby.action.ActionErrors;

public class ActionErrorsImpl implements ActionErrors {

	private List<String> actionErrors;

	private Map<String, List<String>> fieldErrors;

	private List<String> allErrors;

	public List<String> getActionErrors() {
		return actionErrors;
	}

	public void setActionErrors(final List<String> actionErrors) {
		this.actionErrors = actionErrors;
	}

	public Map<String, List<String>> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(final Map<String, List<String>> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public List<String> getAllErrors() {
		return allErrors;
	}

	public void setAllErrors(final List<String> allErrors) {
		this.allErrors = allErrors;
	}

	public boolean isEmpty() {
		return actionErrors.isEmpty() && fieldErrors.isEmpty();
	}

	public void addActionError(final String message) {
		actionErrors.add(message);

		allErrors.add(message);
	}

	public void addFieldError(final String name, final String message) {
		if (!fieldErrors.containsKey(name)) {
			fieldErrors.put(name, new ArrayList<String>());
		}
		fieldErrors.get(name).add(message);

		allErrors.add(message);
	}

	public boolean hasFieldError(final String name) {
		return fieldErrors.containsKey(name);
	}

}
