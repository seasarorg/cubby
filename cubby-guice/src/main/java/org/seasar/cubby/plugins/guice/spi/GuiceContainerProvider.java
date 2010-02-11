/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.plugins.guice.spi;

import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

import com.google.inject.ConfigurationException;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Guice の {@link Injector} による {@link Container} の実装を提供します。
 * 
 * @author baba
 */
public class GuiceContainerProvider implements ContainerProvider {

	/** {@link Container} */
	private final Container container;

	/**
	 * インスタンス化します。
	 * 
	 * @param injector
	 *            インジェクタ
	 */
	@Inject
	public GuiceContainerProvider(final Injector injector) {
		this.container = new GuiceContainerImpl(injector);
	}

	/**
	 * {@inheritDoc}
	 */
	public Container getContainer() {
		return container;
	}

	/**
	 * {@link Injector} による {@link Container} の実装です。
	 * 
	 * @author baba
	 */
	private static class GuiceContainerImpl implements Container {

		/** インスタンスをルックアップするための {@link Injector} */
		private final Injector injector;

		/**
		 * インスタンス化します。
		 * 
		 * @param injector
		 *            インスタンスをルックアップするための {@link Injector}
		 */
		public GuiceContainerImpl(final Injector injector) {
			this.injector = injector;
		}

		/**
		 * {@inheritDoc}
		 */
		public <T> T lookup(final Class<T> type) throws LookupException {
			try {
				return injector.getInstance(type);
			} catch (final ConfigurationException e) {
				throw new LookupException(e);
			}
		}

	}

}
