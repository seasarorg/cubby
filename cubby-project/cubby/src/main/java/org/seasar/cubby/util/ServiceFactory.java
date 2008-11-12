package org.seasar.cubby.util;

import org.seasar.cubby.container.Container;
import org.seasar.cubby.container.ContainerFactory;

public class ServiceFactory {

	/**
	 * コンテナに登録してあるコンポーネントを優先する。
	 * 
	 * @param <S>
	 * @param service
	 * @return
	 */
	public static <S> S getProvider(Class<S> service) {
		final Container container = ContainerFactory.getContainer();
		final S providerFromContainer = container.lookup(service);
		if (providerFromContainer != null) {
			return providerFromContainer;
		}
		final ServiceLoader<S> serviceLoader = ServiceLoader.load(service);
		final S providerFromServiceLoader = serviceLoader.getProvider();
		return providerFromServiceLoader;
	}

}
