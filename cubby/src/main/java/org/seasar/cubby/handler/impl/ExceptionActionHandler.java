package org.seasar.cubby.handler.impl;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;

public class ExceptionActionHandler implements ActionHandler {

	public ActionResult handle(final HttpServletRequest request,
			final HttpServletResponse response,
			final ActionContext actionContext,
			final ActionHandlerChain actionHandlerChain) throws Exception {
		try {
			return actionHandlerChain.chain(request, response, actionContext);
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
