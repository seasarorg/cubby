package org.seasar.cubby.controller;

import java.lang.reflect.Method;

import org.seasar.cubby.annotation.Form;
import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.util.CubbyUtils;


public class ActionHolder {
	private final Method actionMethod;
	private final ActionFilterChain filterChain;
	private final String[] uriConvertNames;
	
	public ActionHolder(Method actionMethod, ActionFilterChain chain, String[] uriConvertNames) {
		this.actionMethod = actionMethod;
		this.filterChain = chain;
		this.uriConvertNames = uriConvertNames;
	}
	
	public Method getActionMethod() {
		return actionMethod;
	}

	public ActionFilterChain getFilterChain() {
		return filterChain;
	}

	public String[] getUriConvertNames() {
		return uriConvertNames;
	}
	
	public Form getForm() {
		Form formInfo = (Form) getActionMethod().getAnnotation(Form.class);
		if (formInfo != null) {
			return formInfo;
		}
		formInfo = (Form) getControllerClass().getAnnotation(Form.class);
		return formInfo;
	}

	private Class<?> getControllerClass() {
		return getActionMethod().getDeclaringClass();
	}

	public Validation getValidation() {
		return getActionMethod().getAnnotation(Validation.class);
	}
	
	public String getControllerName() {
		return CubbyUtils.getControllerName(getControllerClass());
	}

}
