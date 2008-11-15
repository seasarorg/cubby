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
package org.seasar.cubby.internal.spi;

import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.util.ServiceLoader;

/**
 * コンテナのプロバイダです。
 * 
 * @author baba
 * @since 2.0.0
 */
public interface ContainerProvider {

	/**
	 * コンテナを取得します。
	 * 
	 * @return コンテナ
	 */
	Container getContainer();

	/**
	 * {@link ContainerProvider} のファクトリです。
	 * 
	 * @author baba
	 * @since 2.0.0
	 */
	class Factory {

		/** プロバイダ。 */
		private static final ContainerProvider PROVIDER;
		static {
			final ServiceLoader<ContainerProvider> serviceLoader = ServiceLoader
					.load(ContainerProvider.class);
			PROVIDER = serviceLoader.getProvider();
		}

		/**
		 * インスタンス化を禁止します。
		 */
		private Factory() {
		}

		/**
		 * {@link ContainerProvider} を取得します。
		 * 
		 * @return {@link ContainerProvider}
		 */
		public static ContainerProvider get() {
			return PROVIDER;
		}

	}

}
