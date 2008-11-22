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
package org.seasar.cubby.plugins.guice.spi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.container.LookupException;
import org.seasar.cubby.internal.spi.ContainerProvider;
import org.seasar.cubby.plugins.guice.ModuleFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Guice の {@link Injector} による {@link Container} の実装を提供します。
 * 
 * @author baba
 * @since 2.0.0
 */
public class GuiceContainerProvider implements ContainerProvider {

	/** ルックアップに失敗したことを表す例外クラス名。 */
	private static final Set<String> LOOKUP_FAILURE_EXCEPTION_CLASS_NAMES;
	static {
		final Set<String> set = new HashSet<String>();
		set.add("com.google.inject.ConfigurationException");
		LOOKUP_FAILURE_EXCEPTION_CLASS_NAMES = Collections.unmodifiableSet(set);
	}

	/** {@link Container} */
	private final Container container;

	/**
	 * インスタンス化します。
	 */
	public GuiceContainerProvider() {
		final Module module = ModuleFactory.getModule();
		final Injector injector = Guice.createInjector(module);
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
	 * @since 2.0.0
	 */
	private class GuiceContainerImpl implements Container {

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
			} catch (final RuntimeException e) {
				if (LOOKUP_FAILURE_EXCEPTION_CLASS_NAMES.contains(e.getClass()
						.getName())) {
					throw new LookupException(e);
				} else {
					throw e;
				}
			}
		}

	}

}
