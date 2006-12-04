package org.seasar.cubby.controller;


public interface ActionFilter {
	ActionResult doFilter(ActionContext action, ActionFilterChain filter)
			throws Throwable;
}
