package org.seasar.cubby.internal.spi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.seasar.cubby.internal.util.ServiceLoader;

public class ProviderFactory {

	private static Map<Class<?>, Object> PROVIDERS = new ConcurrentHashMap<Class<?>, Object>();

	public static <S> S get(final Class<S> service) {
		synchronized (service) {
			final S provider;
			if (PROVIDERS.containsKey(service)) {
				provider = service.cast(PROVIDERS.get(service));
			} else {
				provider = ServiceLoader.load(service).getProvider();
				PROVIDERS.put(service, provider);
			}
			return provider;
		}
	}

}
