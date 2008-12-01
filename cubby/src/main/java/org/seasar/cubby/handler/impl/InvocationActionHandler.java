package org.seasar.cubby.handler.impl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;

public class InvocationActionHandler implements ActionHandler {

	public ActionResult handle(final HttpServletRequest request,
			final HttpServletResponse response,
			final ActionContext actionContext,
			final ActionHandlerChain actionInvocationChain) throws Exception {
		final Object action = actionContext.getAction();
		final Method actionMethod = actionContext.getActionMethod();
		final ActionResult actionResult = ActionResult.class.cast(actionMethod
				.invoke(action));
		return actionResult;
	}

}
