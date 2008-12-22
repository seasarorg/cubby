/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
