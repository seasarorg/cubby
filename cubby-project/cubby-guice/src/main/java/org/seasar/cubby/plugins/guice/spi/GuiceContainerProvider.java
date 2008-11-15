package org.seasar.cubby.plugins.guice.spi;

import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.container.LookupException;
import org.seasar.cubby.internal.spi.ContainerProvider;
import org.seasar.cubby.plugins.guice.ModuleFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class GuiceContainerProvider implements ContainerProvider {

	public GuiceContainerProvider() {
	}

	public Container getContainer() {
		final Module module = ModuleFactory.getModule();
		final Injector injector = Guice.createInjector(module);
		return new GuiceContainerImpl(injector);
	}

	static class GuiceContainerImpl implements Container {

		private Injector injector;

		public GuiceContainerImpl(Injector injector) {
			this.injector = injector;
		}

		public <T> T lookup(Class<T> type) throws LookupException {
			try {
				return injector.getInstance(type);
			} catch (RuntimeException e) {	//TODO
				System.out.println("************************");
				e.printStackTrace();
				System.out.println("************************");
				throw new LookupException(e);
			}
		}

	}

}
