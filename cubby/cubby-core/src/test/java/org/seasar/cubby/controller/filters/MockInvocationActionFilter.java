package org.seasar.cubby.controller.filters;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;

public class MockInvocationActionFilter implements ActionFilter {

	private ActionResult result;

	public ActionResult doFilter(ActionContext action, ActionFilterChain chain)
			throws Throwable {
		return result;
	}

	public void setResult(ActionResult result) {
		this.result = result;
	}

}
