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
