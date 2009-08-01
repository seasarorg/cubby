package org.seasar.cubby.examples.guice;

import org.seasar.cubby.examples.guice.action.HelloAction;
import org.seasar.cubby.examples.guice.action.IndexAction;
import org.seasar.cubby.examples.guice.service.HelloService;
import org.seasar.cubby.examples.guice.service.impl.HelloServiceImpl;
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
