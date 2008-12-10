package org.seasar.cubby.mock;

import org.junit.Assert;
import org.seasar.cubby.internal.routing.PathResolver;
import org.seasar.cubby.internal.spi.PathResolverProvider;

public class MockPathResolverProvider implements PathResolverProvider {

	private static PathResolver pathResolver = null;

	public static void setPathResolver(final PathResolver pathResolver) {
		MockPathResolverProvider.pathResolver = pathResolver;
	}

	public PathResolver getPathResolver() {
		Assert
				.assertNotNull(
						"please setup MockPathResolver, to use MockPathResolverProvider#setPathResolver()",
						pathResolver);
		return pathResolver;
	}

}
