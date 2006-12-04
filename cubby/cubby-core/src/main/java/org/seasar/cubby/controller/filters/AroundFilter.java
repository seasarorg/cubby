package org.seasar.cubby.controller.filters;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionResult;

public abstract class AroundFilter implements ActionFilter {

	public ActionResult doFilter(ActionContext action, ActionFilterChain chain)
			throws Throwable {
		doBeforeFilter(action);
		ActionResult result = chain.doFilter(action);
		doAfterFilter(action, result);
		return result;
	}

	protected void doBeforeFilter(ActionContext action) {}

	protected void doAfterFilter(ActionContext action, ActionResult result) {}

}
