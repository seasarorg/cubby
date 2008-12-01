package org.seasar.cubby.guice_examples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seasar.cubby.guice_examples.action.HelloAction;
import org.seasar.cubby.guice_examples.action.IndexAction;
import org.seasar.cubby.guice_examples.service.HelloService;
import org.seasar.cubby.guice_examples.service.impl.HelloServiceImpl;
import org.seasar.cubby.plugins.guice.AbstractCubbyModule;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class ExampleModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new AbstractCubbyModule() {

			@Override
			protected Collection<Class<?>> getActionClasses() {
				List<Class<?>> actionClasses = new ArrayList<Class<?>>();
				actionClasses.add(IndexAction.class);
				actionClasses.add(HelloAction.class);
				return actionClasses;
			}

		});
		// install(new ServletModule());
		setUpServletModule();

		bind(HelloService.class).to(HelloServiceImpl.class).in(Singleton.class);
	}

	protected void setUpServletModule() {
		install(new ServletModule());
	}

}
