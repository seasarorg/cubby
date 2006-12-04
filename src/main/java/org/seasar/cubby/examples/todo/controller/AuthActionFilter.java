package org.seasar.cubby.examples.todo.controller;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.results.Redirect;

public class AuthActionFilter implements ActionFilter {
	public ActionResult doFilter(ActionContext action, ActionFilterChain chain) throws Throwable {
		if(action.getRequest().getSession().getAttribute("user") == null) {
			action.getController().getFlash().put("notice", "ログインしていません。");
			return new Redirect("/login");
		} else {
			return chain.doFilter(action);
		}
	}	
}
