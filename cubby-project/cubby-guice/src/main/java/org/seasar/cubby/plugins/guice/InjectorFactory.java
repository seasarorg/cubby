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

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class InjectorFactory {

	private static Injector injector;

	public synchronized static void setModuleClassName(
			final String moduleClassName) {
		final ClassLoader loader = Thread.currentThread()
				.getContextClassLoader();
		try {
			final Class<?> clazz = Class.forName(moduleClassName, true, loader);
			final Module module = Module.class.cast(clazz.newInstance());
			injector = Guice.createInjector(module);
		} catch (final ClassNotFoundException e) {
			throw new IllegalModuleException(e);
		} catch (InstantiationException e) {
			throw new IllegalModuleException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalModuleException(e);
		}
	}

	public static Injector getInjector() {
		return injector;
	}

}
