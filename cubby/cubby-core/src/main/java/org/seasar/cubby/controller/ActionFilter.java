package org.seasar.cubby.controller;

import org.seasar.cubby.action.ActionResult;


public interface ActionFilter {
	ActionResult doFilter(ActionContext context, ActionFilterChain chain)
			throws Throwable;
}
