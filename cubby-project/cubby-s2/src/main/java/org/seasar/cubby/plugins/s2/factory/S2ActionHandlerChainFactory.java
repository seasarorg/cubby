package org.seasar.cubby.plugins.s2.factory;

import java.util.List;

import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.handler.ActionHandlerChainFactory;
import org.seasar.cubby.internal.handler.impl.ActionHandlerChainImpl;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class S2ActionHandlerChainFactory implements ActionHandlerChainFactory {

	public ActionHandlerChain getActionHandlerChain() {
		final S2Container container = SingletonS2ContainerFactory
				.getContainer().getRoot();
		final ActionHandlersFactory actionHandlersFactory = (ActionHandlersFactory) container
				.getComponent(ActionHandlersFactory.class);
		final List<ActionHandler> actionHandlers = actionHandlersFactory
				.getActionHandlers();
		return new ActionHandlerChainImpl(actionHandlers.iterator());
	}

	public interface ActionHandlersFactory {
		List<ActionHandler> getActionHandlers();
	}

	public static class ActionHandlersFactoryImpl implements
			ActionHandlersFactory {

		private List<ActionHandler> actionHandlers;

		public ActionHandlersFactoryImpl(List<ActionHandler> actionHandlers) {
			this.actionHandlers = actionHandlers;
		}

		public List<ActionHandler> getActionHandlers() {
			return actionHandlers;
		}

	}
}
