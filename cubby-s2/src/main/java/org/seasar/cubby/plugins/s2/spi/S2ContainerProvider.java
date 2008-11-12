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

import org.seasar.cubby.container.Container;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * {@link S2Container} による {@link Container} の実装を提供します。
 * 
 * @author baba
 * @since 2.0.0
 */
public class S2ContainerProvider implements ContainerProvider {

	/** {@link Container} */
	private Container container;

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
		 */
		@SuppressWarnings("unchecked")
		public <T> T lookup(Class<T> type) {
			try {
				return (T) container().getComponent(type);
			} catch (final ComponentNotFoundRuntimeException e) {
				return null;
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean has(Class<?> type) {
			return container().hasComponentDef(type);
		}

		/**
		 * {@link S2Container} を取得します。
		 * 
		 * @return {@link S2Container}
		 */
		private S2Container container() {
			return SingletonS2ContainerFactory.getContainer();
		}

	}

}
