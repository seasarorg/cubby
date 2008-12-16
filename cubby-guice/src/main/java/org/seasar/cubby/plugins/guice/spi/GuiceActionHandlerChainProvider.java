package org.seasar.cubby.plugins.guice.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.handler.impl.ActionHandlerChainImpl;
import org.seasar.cubby.plugins.guice.InjectorFactory;
import org.seasar.cubby.spi.ActionHandlerChainProvider;

import com.google.inject.Injector;

public class GuiceActionHandlerChainProvider implements
		ActionHandlerChainProvider {

	private Collection<ActionHandler> actionHandlers;

	public GuiceActionHandlerChainProvider() {
		final Injector injector = InjectorFactory.getInjector();
		final List<ActionHandler> actionHandlers = new ArrayList<ActionHandler>();
		final ActionHandlerClassesFactory actionHandlerClassesFactory = injector
				.getInstance(ActionHandlerClassesFactory.class);
		for (final Class<? extends ActionHandler> actionHandlerClass : actionHandlerClassesFactory
				.getActionHandlerClasses()) {
			final ActionHandler actionHandler = injector
					.getInstance(actionHandlerClass);
			actionHandlers.add(actionHandler);
		}
		this.actionHandlers = actionHandlers;
	}

	public ActionHandlerChain getActionHandlerChain() {
		return new ActionHandlerChainImpl(actionHandlers);
	}

	public interface ActionHandlerClassesFactory {
		Collection<Class<? extends ActionHandler>> getActionHandlerClasses();
	}

}
