package org.seasar.cubby.internal.spi;

import org.seasar.cubby.internal.routing.PathResolver;

public interface PathResolverProvider {

	PathResolver getPathResolver();

}
