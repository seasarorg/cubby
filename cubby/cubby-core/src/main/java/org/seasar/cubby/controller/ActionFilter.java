package org.seasar.cubby.controller;

import org.seasar.cubby.action.ActionResult;


public interface ActionFilter {
	ActionResult doFilter(ActionContext action, ActionFilterChain chain)
			throws Throwable;
}
