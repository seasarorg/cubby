package org.seasar.cubby.plugins.guice;

import org.seasar.cubby.util.ClassUtils;

import com.google.inject.Module;

public class ModuleFactory {

	private static Module module;

	public synchronized static void setModuleClassName(
			final String moduleClassName) {
		final Class<? extends Module> moduleClass = ClassUtils
				.forName(moduleClassName);
		try {
			module = moduleClass.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalModuleException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalModuleException(e);
		}
	}

	public static Module getModule() {
		return module;
	}

}
