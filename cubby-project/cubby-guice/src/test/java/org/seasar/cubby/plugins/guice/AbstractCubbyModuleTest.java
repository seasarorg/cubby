package org.seasar.cubby.plugins.guice;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.internal.factory.ConverterFactory;
import org.seasar.cubby.plugins.guice.factory.GuicePathResolverFactory.ActionClassesFactory;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

public class AbstractCubbyModuleTest {

	@Before
	public void setup() {
		ModuleFactory.setModuleClassName(TestModule.class
				.getName());
	}

	@Test
	public void configure() {
		Module module = ModuleFactory.getModule();
		Injector injector = Guice.createInjector(module);
		System.out.println(injector);
		Foo foo = injector.getInstance(Foo.class);
		System.out.println(foo.factory);
		ConverterFactory converterFactory = injector.getInstance(ConverterFactory.class);
		System.out.println(converterFactory);
	}

	public static class TestModule extends AbstractCubbyModule {
		@Override
		protected Collection<Class<? extends Action>> getActionClasses() {
			return new ArrayList<Class<? extends Action>>();
		}
	}

	public static class Foo {
		@Inject
		public ActionClassesFactory factory;
	}

}
