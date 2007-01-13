package org.seasar.cubby.controller;

import java.lang.reflect.Method;

import org.seasar.cubby.annotation.Form;
import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.util.CubbyUtils;


public class ActionMethod {
	private final Method actionMethod;
	private final ActionFilterChain filterChain;
	private final String[] uriConvertNames;
	
	public ActionMethod(Method method, ActionFilterChain chain, String[] uriConvertNames) {
		this.actionMethod = method;
		this.filterChain = chain;
		this.uriConvertNames = uriConvertNames;
	}
	
	public Method getMethod() {
		return actionMethod;
	}

	public ActionFilterChain getFilterChain() {
		return filterChain;
	}

	public String[] getUriConvertNames() {
		return uriConvertNames;
	}
	
	public Form getForm() {
		Form formInfo = (Form) getMethod().getAnnotation(Form.class);
		if (formInfo != null) {
			return formInfo;
		}
		formInfo = (Form) getControllerClass().getAnnotation(Form.class);
		return formInfo;
	}

	private Class<?> getControllerClass() {
		return getMethod().getDeclaringClass();
	}

	public Validation getValidation() {
		return getMethod().getAnnotation(Validation.class);
	}
	
	public String getControllerName() {
		return CubbyUtils.getControllerName(getControllerClass());
	}

}
