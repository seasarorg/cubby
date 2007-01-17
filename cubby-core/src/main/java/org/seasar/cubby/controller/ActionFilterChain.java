package org.seasar.cubby.controller;

import java.util.ArrayList;
import java.util.List;

import org.seasar.cubby.action.ActionResult;


public class ActionFilterChain {

	private static final String ATTR_CURRENT_ACTION_FILTER = "__cubby_current_action_filter__";
	private List<ActionFilter> interceptors = new ArrayList<ActionFilter>();

	public void add(ActionFilter interceptor) {
		interceptors.add(interceptor);
	}

	public ActionResult doFilter(ActionContext context) throws Throwable {
		ActionFilter next = findNextFilter(context);
		context.getRequest().setAttribute(ATTR_CURRENT_ACTION_FILTER, next);
		ActionResult result = next.doFilter(context, this);
		return result;
	}
	
	private ActionFilter findNextFilter(ActionContext context) {
		ActionFilter filter = (ActionFilter) context.getRequest().getAttribute(ATTR_CURRENT_ACTION_FILTER);
		if (filter == null) {
			return interceptors.get(0);
		} else {
			int index = interceptors.indexOf(filter);
			return interceptors.get(index + 1);
		}
	}
}