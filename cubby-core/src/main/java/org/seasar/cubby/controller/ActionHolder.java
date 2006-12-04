package org.seasar.cubby.controller;

import java.lang.reflect.Method;


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
}
