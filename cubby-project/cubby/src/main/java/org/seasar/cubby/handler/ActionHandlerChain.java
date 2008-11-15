package org.seasar.cubby.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;

public interface ActionHandlerChain {

	ActionResult chain(HttpServletRequest request,
			HttpServletResponse response, ActionContext actionContext)
			throws Exception;

}
