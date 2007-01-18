package org.seasar.cubby.controller;

import java.lang.reflect.Method;

import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.util.CubbyUtils;

/**
 * Actionメソッドを管理するクラスです。
 * @author agata
 */
public class ActionMethod {
	
	private final Method actionMethod;

	private final ActionFilterChain filterChain;

	private final String[] uriConvertNames;

	private final Form form;

	public ActionMethod(Method method, ActionFilterChain chain,
			String[] uriConvertNames) {
		this.actionMethod = method;
		this.filterChain = chain;
		this.uriConvertNames = uriConvertNames;
		this.form = findForm();
	}

	private Form findForm() {
		Form form = (Form) getMethod().getAnnotation(Form.class);
		if (form == null) {
			form = (Form) getControllerClass().getAnnotation(Form.class);
		}
		return form;
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
		return form;
	}

	private Class<?> getControllerClass() {
		return getMethod().getDeclaringClass();
	}

	public Validation getValidation() {
		return getMethod().getAnnotation(Validation.class);
	}

	public String getControllerName() {
		return CubbyUtils.getActionName(getControllerClass());
	}
}
