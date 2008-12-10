package org.seasar.cubby.plugins.guice.spi;

import org.seasar.cubby.internal.routing.PathResolver;
import org.seasar.cubby.internal.spi.PathResolverProvider;
import org.seasar.cubby.plugins.guice.InjectorFactory;

import com.google.inject.Injector;

public class GuicePathResolverProvider implements PathResolverProvider {

	public PathResolver getPathResolver() {
		final Injector injector = InjectorFactory.getInjector();
		final PathResolver pathResolver = injector
				.getInstance(PathResolver.class);
		return pathResolver;
	}

}
