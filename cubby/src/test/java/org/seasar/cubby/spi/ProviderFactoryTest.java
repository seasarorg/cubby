/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.spi;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;

public class ProviderFactoryTest {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	@Test
	public void getSingletonInstanceInMultiThread() throws Exception {
		try {
			ProviderFactory.get(FooProvider.class);
			fail();
		} catch (IllegalArgumentException e) {
			// ok
		}

		final BinderPlugin binderPlugin = new BinderPlugin();
		FooProviderImpl fooProviderImpl = new FooProviderImpl();
		binderPlugin.bind(FooProvider.class).toInstance(fooProviderImpl);
		pluginRegistry.register(binderPlugin);

		assertSame(fooProviderImpl, ProviderFactory.get(FooProvider.class));
		assertSame(fooProviderImpl, ProviderFactory.get(FooProvider.class)); // twice

		pluginRegistry.clear();

		try {
			ProviderFactory.get(FooProvider.class);
			fail();
		} catch (IllegalArgumentException e) {
			// ok
		}
	}

}
