package org.seasar.cubby.guice_examples;

import org.seasar.cubby.guice_examples.action.HelloAction;
import org.seasar.cubby.guice_examples.action.IndexAction;
import org.seasar.cubby.guice_examples.service.HelloService;
import org.seasar.cubby.guice_examples.service.impl.HelloServiceImpl;
import org.seasar.cubby.plugins.guice.AbstractCubbyModule;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.impl.PathResolverImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class ExampleModule extends AbstractModule {

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

		bind(HelloService.class).to(HelloServiceImpl.class).in(Singleton.class);
	}

}
