package org.seasar.cubby.controller.filters;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;

public abstract class AroundFilter implements ActionFilter {

	public ActionResult doFilter(ActionContext context, ActionFilterChain chain)
			throws Throwable {
		doBeforeFilter(context);
		ActionResult result = chain.doFilter(context);
		doAfterFilter(context, result);
		return result;
	}

	protected void doBeforeFilter(ActionContext context) {}

	protected void doAfterFilter(ActionContext context, ActionResult result) {}

}
