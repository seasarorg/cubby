package org.seasar.cubby.exmaple.gae;

import org.seasar.cubby.exmaple.gae.action.HelloAction;
import org.seasar.cubby.exmaple.gae.action.IndexAction;
import org.seasar.cubby.plugins.guice.AbstractCubbyModule;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.impl.PathResolverImpl;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;

public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new AbstractCubbyModule() {

			@Override
			protected PathResolver getPathResolver() {
				final PathResolver pathResolver = new PathResolverImpl();
				pathResolver.add(IndexAction.class);
				pathResolver.add(HelloAction.class);
				return pathResolver;
			}

		});
		install(new ServletModule());

	}

}
