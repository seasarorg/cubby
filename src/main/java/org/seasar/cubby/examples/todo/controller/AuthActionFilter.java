package org.seasar.cubby.examples.todo.controller;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;

public class AuthActionFilter implements ActionFilter {	
	public String doFilter(ActionContext action, ActionFilterChain chian) throws Throwable {
		if(action.getRequest().getSession().getAttribute("user") == null) {
			action.getController().getFlash().put("notice", "ログインしていません。");
			return "@/login";
		} else {
			return chian.doFilter(action);
		}
	}
}
