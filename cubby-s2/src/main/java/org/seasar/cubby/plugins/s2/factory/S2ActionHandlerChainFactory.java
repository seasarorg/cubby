package org.seasar.cubby.plugins.s2.factory;

import java.util.List;

import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.handler.ActionHandlerChainFactory;
import org.seasar.cubby.internal.handler.impl.ActionHandlerChainImpl;

public class S2ActionHandlerChainFactory implements ActionHandlerChainFactory {

	private List<ActionHandler> actionHandlers;

	public static final String actionHandlers_BINDING = "bindingType=must";

	public void setActionHandlers(List<ActionHandler> actionHandlers) {
		this.actionHandlers = actionHandlers;
	}

	public ActionHandlerChain getActionHandlerChain() {
		return new ActionHandlerChainImpl(actionHandlers.iterator());
	}

}
