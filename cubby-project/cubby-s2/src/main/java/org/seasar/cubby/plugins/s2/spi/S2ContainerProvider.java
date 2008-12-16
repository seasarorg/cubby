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
package org.seasar.cubby.plugins.s2.spi;

import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.CyclicReferenceRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.TooManyRegistrationRuntimeException;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * {@link S2Container} による {@link Container} の実装を提供します。
 * 
 * @author baba
 * @since 2.0.0
 */
public class S2ContainerProvider implements ContainerProvider {

	/** {@link Container} */
	private final Container container;

	/**
	 * インスタンス化します。
	 */
	public S2ContainerProvider() {
		this.container = new S2ContainerImpl();
	}

	/**
	 * {@inheritDoc}
	 */
	public Container getContainer() {
		return container;
	}

	/**
	 * {@link S2Container} による {@link Container} の実装です。
	 * 
	 * @author baba
	 * @since 2.0.0
	 */
	private class S2ContainerImpl implements Container {

		/**
		 * {@inheritDoc}
		 * 
		 * @throws LookupException
		 */
		public <T> T lookup(final Class<T> type) throws LookupException {
			final S2Container container = SingletonS2ContainerFactory
					.getContainer();
			try {
				final T component = type.cast(container.getComponent(type));
				if (component == null) {
					throw new LookupException();
				}
				return component;
			} catch (final ComponentNotFoundRuntimeException e) {
				throw new LookupException(e);
			} catch (final TooManyRegistrationRuntimeException e) {
				throw new LookupException(e);
			} catch (final CyclicReferenceRuntimeException e) {
				throw new LookupException(e);
			}
		}

	}

}
