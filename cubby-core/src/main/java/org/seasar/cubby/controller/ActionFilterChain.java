package org.seasar.cubby.controller;

import java.util.ArrayList;
import java.util.List;


public class ActionFilterChain {

	private List<ActionFilter> interceptors = new ArrayList<ActionFilter>();

	public void add(ActionFilter interceptor) {
		interceptors.add(interceptor);
	}

	public ActionResult doFilter(ActionContext context) throws Throwable {
		ActionFilter next = findNextFilter(context);
		System.out.println("filter=" + next);
		context.setCurrentFilter(next);
		ActionResult result = next.doFilter(context, this);
		return result;
	}
	
	private ActionFilter findNextFilter(ActionContext context) {
		ActionFilter filter = context.getCurrentFilter();
		if (filter == null) {
			return interceptors.get(0);
		} else {
			int index = interceptors.indexOf(filter);
			return interceptors.get(index + 1);
		}
	}
}