package org.seasar.cubby.plugins.guice.factory;

import java.util.ArrayList;
import java.util.List;

import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.handler.ActionHandlerChainFactory;
import org.seasar.cubby.internal.handler.impl.ActionHandlerChainImpl;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class GuiceActionHandlerChainFactory implements
		ActionHandlerChainFactory {

	private final List<ActionHandler> actionHandlers;

	@Inject
	public GuiceActionHandlerChainFactory(final Injector injector,
			final ActionHandlerClassesFactory actionHandlerClassesFactory) {
		final List<ActionHandler> actionHandlers = new ArrayList<ActionHandler>();
		for (final Class<? extends ActionHandler> actionHandlerClass : actionHandlerClassesFactory
				.getActionHandlerClasses()) {
			final ActionHandler actionHandler = injector
					.getInstance(actionHandlerClass);
			actionHandlers.add(actionHandler);
		}
		this.actionHandlers = actionHandlers;
	}

	public ActionHandlerChain getActionHandlerChain() {
		final ActionHandlerChain actionHandlerChain = new ActionHandlerChainImpl(
				actionHandlers.iterator());
		return actionHandlerChain;
	}

	public interface ActionHandlerClassesFactory {
		List<Class<? extends ActionHandler>> getActionHandlerClasses();
	}

}
