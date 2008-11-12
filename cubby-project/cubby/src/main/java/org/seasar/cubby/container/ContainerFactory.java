package org.seasar.cubby.container;

import org.seasar.cubby.spi.ContainerProvider;

public class ContainerFactory {

	public static Container getContainer() {
		final ContainerProvider provider = ContainerProvider.Factory.get();
		return provider.getContainer();
	}

}
