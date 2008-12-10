package org.seasar.cubby.handler.impl;

import java.lang.reflect.InvocationTargetException;
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
		try {
			final Object action = actionContext.getAction();
			final Method actionMethod = actionContext.getActionMethod();
			final ActionResult actionResult = (ActionResult) actionMethod
					.invoke(action);
			return actionResult;
		} catch (final InvocationTargetException e) {
			final Throwable target = e.getTargetException();
			if (target instanceof Error) {
				throw (Error) target;
			} else if (target instanceof RuntimeException) {
				throw (RuntimeException) target;
			} else {
				throw (Exception) target;
			}
		}
	}

}
