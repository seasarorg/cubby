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

	public static void clear() {
		PROVIDERS.clear();
	}

	// --- for test

	public static <S> Binder<S> bind(Class<S> service) {
		return new Binder<S>(service);
	}

	public static class Binder<S> {

		private final Class<S> key;

		private Binder(final Class<S> key) {
			this.key = key;
		}

		public void toInstance(final S instance) {
			PROVIDERS.put(key, instance);
		}

	}

}
