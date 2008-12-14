package org.seasar.cubby.mock;

import org.seasar.cubby.internal.routing.PathResolver;
import org.seasar.cubby.internal.spi.PathResolverProvider;

public class MockPathResolverProvider implements PathResolverProvider {

	private final PathResolver pathResolver;

	public MockPathResolverProvider(final PathResolver pathResolver) {
		this.pathResolver = pathResolver;
	}

	public PathResolver getPathResolver() {
		return pathResolver;
	}

}
