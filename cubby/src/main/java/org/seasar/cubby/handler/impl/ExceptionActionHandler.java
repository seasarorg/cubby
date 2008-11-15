package org.seasar.cubby.handler.impl;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;

public class ExceptionActionHandler implements ActionHandler {

	public ActionResult handle(HttpServletRequest request,
			HttpServletResponse response, ActionContext actionContext,
			ActionHandlerChain actionInvocationChain) throws Exception {
		try {
			return actionInvocationChain
					.chain(request, response, actionContext);
		} catch (final InvocationTargetException e) {
			final Throwable target = e.getTargetException();
			if (target instanceof Error) {
				throw Error.class.cast(target);
			} else if (target instanceof RuntimeException) {
				throw RuntimeException.class.cast(target);
			} else {
				throw Exception.class.cast(target);
			}
		}
	}

}
