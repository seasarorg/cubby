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
