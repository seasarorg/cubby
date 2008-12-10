package org.seasar.cubby.plugins.guice;

import org.seasar.cubby.internal.util.ClassUtils;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class InjectorFactory {

	private static Injector injector;

	public synchronized static void setModuleClassName(
			final String moduleClassName) {
		final Class<? extends Module> moduleClass = ClassUtils
				.forName(moduleClassName);
		try {
			final Module module = moduleClass.newInstance();
			injector = Guice.createInjector(module);
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
