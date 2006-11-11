package org.seasar.cubby.controller.filters;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;

public abstract class AroundFilter implements ActionFilter {

	public String doFilter(ActionContext action, ActionFilterChain chain)
			throws Throwable {
		doBeforeFilter(action);
		String result = chain.doFilter(action);
		doAfterFilter(action);
		return result;
	}

	protected void doBeforeFilter(ActionContext action) {}

	protected void doAfterFilter(ActionContext action) {}

}
