package org.seasar.cubby.mock;

import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.container.Container;

public class MockContainerProvider implements ContainerProvider {

	private final Container container;

	public MockContainerProvider(Container container) {
		this.container = container;
	}

	public Container getContainer() {
		return container;
	}

}
