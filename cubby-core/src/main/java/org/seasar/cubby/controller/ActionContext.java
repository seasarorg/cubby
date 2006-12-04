package org.seasar.cubby.controller;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.annotation.Form;
import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.validator.Validators;

public interface ActionContext {
	Controller getController();
	Method getMethod();
	HttpServletRequest getRequest();
	HttpServletResponse getResponse();
	Form getForm();
	Validation getValidation();
	Validators getValidators();
	Object getFormBean();
	String getControllerName();
	ActionHolder getActionHolder();
	ActionFilter getCurrentFilter();
	void setCurrentFilter(ActionFilter filter);
	Map<String, Object> getUriParams();
}
