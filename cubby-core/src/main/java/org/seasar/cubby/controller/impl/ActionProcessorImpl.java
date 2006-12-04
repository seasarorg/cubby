package org.seasar.cubby.controller.impl;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.controller.ActionResult;

public class ActionProcessorImpl implements ActionProcessor {

	public ActionResult processAction(ActionContext action) throws Throwable {
		ActionFilterChain actionFilterChain = action.getActionHolder().getFilterChain();
		ActionResult result = actionFilterChain.doFilter(action);
		if (result != null) {
			result.execute(action);
		}
		return result;
	}
}
