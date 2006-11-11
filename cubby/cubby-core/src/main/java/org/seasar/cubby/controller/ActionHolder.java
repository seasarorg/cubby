package org.seasar.cubby.controller;

import java.lang.reflect.Method;


public class ActionHolder {
	private final Method actionMethod;
	private final ActionFilterChain filterChain;
	
	public ActionHolder(Method actionMethod, ActionFilterChain chain) {
		this.actionMethod = actionMethod;
		this.filterChain = chain;	
	}
	
	public Method getActionMethod() {
		return actionMethod;
	}

	public ActionFilterChain getFilterChain() {
		return filterChain;
	}
}
