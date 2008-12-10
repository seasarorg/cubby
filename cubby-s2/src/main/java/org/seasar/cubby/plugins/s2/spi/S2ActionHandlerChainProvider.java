package org.seasar.cubby.plugins.s2.spi;

import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.internal.spi.ActionHandlerChainProvider;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class S2ActionHandlerChainProvider implements ActionHandlerChainProvider {

	public ActionHandlerChain getActionHandlerChain() {
		final S2Container container = SingletonS2ContainerFactory
				.getContainer();
		final ActionHandlerChain actionHandlerChain = (ActionHandlerChain) container
				.getComponent(ActionHandlerChain.class);
		return actionHandlerChain;
	}

}
