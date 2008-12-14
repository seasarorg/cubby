package org.seasar.cubby.mock;

import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.spi.ContainerProvider;

public class MockContainerProvider implements ContainerProvider {

	private final Container container;

	public MockContainerProvider(Container container) {
		this.container = container;
	}

	public Container getContainer() {
		return container;
	}

}
