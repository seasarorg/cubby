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
package org.seasar.cubby.plugins.guice.spyke;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

public class GuiceTest {

	private Injector injector = Guice.createInjector(new GuiceTestModule());

	@Test
	public void getInstance() {
		try {
			injector.getInstance(Foo.class);
		} catch (RuntimeException e) {
			if (e.getClass().getName().equals("com.google.inject.ConfigurationException")) {
				System.out.println(e);
			} else {
				throw e;
			}
		}
	}

	@Test
	public void getBinding1() {
		Key<Foo> key = Key.get(Foo.class);
		Binding<Foo> binding = injector.getBinding(key);
		assertNull(binding);
	}

	@Test
	public void getBinding2() {
		Key<Bar> key = Key.get(Bar.class);
		Binding<Bar> binding = injector.getBinding(key);
		assertNotNull(binding);
	}

	@Test
	public void getBinding3() {
		Key<Baz> key = Key.get(Baz.class);
		Binding<Baz> binding = injector.getBinding(key);
		assertNull(binding);
	}

	private static class GuiceTestModule extends AbstractModule {

		@Override
		protected void configure() {
			bind(Bar.class).to(BarImpl.class);
		}

	}

	private static interface Foo {
	}

	private static interface Bar {
	}

	private static class BarImpl implements Bar {
	}

	private static class Baz {
	}

}
