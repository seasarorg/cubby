package org.seasar.cubby.controller.chain;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;

public class InvocationActionHandler implements ActionHandler {

	public ActionResult handle(final HttpServletRequest request,
			final HttpServletResponse response,
			final ActionContext actionContext,
			final ActionHandlerChain actionInvocationChain) throws Exception {
		final Action action = actionContext.getAction();
		final Method actionMethod = actionContext.getActionMethod();
		final ActionResult actionResult = ActionResult.class.cast(actionMethod
				.invoke(action));
		return actionResult;
	}

}
