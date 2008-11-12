package org.seasar.cubby.controller.chain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;

public interface ActionHandler {

	ActionResult handle(HttpServletRequest request,
			HttpServletResponse response, ActionContext actionContext,
			ActionHandlerChain actionInvocationChain) throws Exception;

}
