package org.seasar.cubby.guice_examples;

import org.seasar.cubby.guice_examples.action.HelloAction;
import org.seasar.cubby.guice_examples.action.IndexAction;
import org.seasar.cubby.guice_examples.service.HelloService;
import org.seasar.cubby.guice_examples.service.impl.HelloServiceImpl;
import org.seasar.cubby.plugins.guice.CubbyModule;
import org.seasar.cubby.plugins.guice.FileUploadModule;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new ServletModule());
		install(new CubbyModule());
		install(new FileUploadModule());

		bind(IndexAction.class);
		bind(HelloAction.class);
		bind(HelloService.class).to(HelloServiceImpl.class).in(Singleton.class);
	}

}