package org.seasar.cubby.internal.util;

import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

public class ServiceFactory {

	/**
	 * コンテナに登録してあるコンポーネントを優先する。
	 * 
	 * @param <S>
	 * @param service
	 * @return
	 */
	public static <S> S getProvider(final Class<S> service) {
		final Container container = ProviderFactory
				.get(ContainerProvider.class).getContainer();
		try {
			return container.lookup(service);
		} catch (final LookupException e) {
			final ServiceLoader<S> serviceLoader = ServiceLoader.load(service);
			return serviceLoader.getProvider();
		}
	}

}
