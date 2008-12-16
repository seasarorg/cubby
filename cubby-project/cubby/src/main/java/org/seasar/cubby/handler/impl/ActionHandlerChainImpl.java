package org.seasar.cubby.handler.impl;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;

public class ActionHandlerChainImpl implements ActionHandlerChain {

	private final Iterator<ActionHandler> iterator;

	public ActionHandlerChainImpl(final Collection<ActionHandler> actionHandlers) {
		this.iterator = actionHandlers.iterator();
	}

	public ActionResult chain(final HttpServletRequest request,
			final HttpServletResponse response,
			final ActionContext actionContext) throws Exception {
		final ActionHandler handler = iterator.next();
		final ActionResult actionResult = handler.handle(request, response,
				actionContext, this);
		return actionResult;
	}

}
