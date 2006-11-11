package org.seasar.cubby.controller;


public interface ActionFilter {
	String doFilter(ActionContext action, ActionFilterChain filter)
			throws Throwable;
}
