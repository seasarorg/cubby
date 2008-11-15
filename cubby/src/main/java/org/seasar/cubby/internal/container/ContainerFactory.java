package org.seasar.cubby.internal.container;

import org.seasar.cubby.internal.spi.ContainerProvider;

public class ContainerFactory {

	public static Container getContainer() {
		final ContainerProvider provider = ContainerProvider.Factory.get();
		return provider.getContainer();
	}

}
