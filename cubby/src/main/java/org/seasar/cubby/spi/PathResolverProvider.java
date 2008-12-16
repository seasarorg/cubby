package org.seasar.cubby.spi;

import org.seasar.cubby.routing.PathResolver;

public interface PathResolverProvider {

	PathResolver getPathResolver();

}
