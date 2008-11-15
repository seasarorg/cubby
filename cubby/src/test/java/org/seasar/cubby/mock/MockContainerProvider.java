package org.seasar.cubby.mock;

import org.junit.Assert;
import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.spi.ContainerProvider;

public class MockContainerProvider implements ContainerProvider {

	private static Container container;

	public static void setContainer(Container container) {
		MockContainerProvider.container = container;
	}

	public Container getContainer() {
		Assert
				.assertNotNull(
						"please setup MockContainer, to use MockContainerProvider#setContainer()",
						container);
		return container;
	}
}
