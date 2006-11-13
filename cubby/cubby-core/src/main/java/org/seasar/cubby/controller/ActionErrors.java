package org.seasar.cubby.controller;

import java.util.List;
import java.util.Map;

public interface ActionErrors {
	boolean isEmpty();
	boolean hasFieldError(String name);
	void addActionError(String message);
	void addFieldError(String name, String message);
	List<String> getAllErrors();
	List<String> getActionErrors();
	Map<String, List<String>> getFieldErrors();
}