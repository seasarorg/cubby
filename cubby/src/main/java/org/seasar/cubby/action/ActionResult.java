package org.seasar.cubby.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;

public interface ActionResult {

	void execute(ActionContext context, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	void prerender(ActionContext context, HttpServletRequest request);

}
