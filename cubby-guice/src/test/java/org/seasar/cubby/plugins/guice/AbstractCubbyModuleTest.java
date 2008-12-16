package org.seasar.cubby.plugins.guice;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.ProviderFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class AbstractCubbyModuleTest {

	@Before
	public void setup() {
		InjectorFactory.setModuleClassName(TestModule.class.getName());
	}

	@Test
	public void configure() {
		Injector injector = InjectorFactory.getInjector();
		System.out.println(injector);
		Foo foo = injector.getInstance(Foo.class);
		System.out.println(foo.pathResolver);
		ConverterProvider converterProvider = ProviderFactory
				.get(ConverterProvider.class);
		System.out.println(converterProvider);
	}

	public static class TestModule extends AbstractCubbyModule {

		@Override
		protected PathResolver getPathResolver() {
			return new PathResolverImpl();
		}

	}

	public static class Foo {
		@Inject
		public PathResolver pathResolver;
	}

}
