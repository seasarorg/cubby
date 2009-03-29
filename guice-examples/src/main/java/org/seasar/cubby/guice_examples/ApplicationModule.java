package org.seasar.cubby.guice_examples;

import org.seasar.cubby.guice_examples.action.HelloAction;
import org.seasar.cubby.guice_examples.action.IndexAction;
import org.seasar.cubby.guice_examples.service.HelloService;
import org.seasar.cubby.guice_examples.service.impl.HelloServiceImpl;
import org.seasar.cubby.plugins.guice.AbstractCubbyModule;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.PathTemplateParser;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.routing.impl.PathTemplateParserImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new AbstractCubbyModule() {

			@Override
			protected PathResolver getPathResolver() {
				final PathTemplateParser pathTemplateParser = new PathTemplateParserImpl();
				final PathResolver pathResolver = new PathResolverImpl(pathTemplateParser);
				pathResolver.add(IndexAction.class);
				pathResolver.add(HelloAction.class);
				return pathResolver;
			}

		});

		bind(HelloService.class).to(HelloServiceImpl.class).in(Singleton.class);
	}

}
