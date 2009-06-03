/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.cubby.plugins.spring.spi;

import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * {@link ApplicationContext} による {@link Container} の実装を提供します。
 * 
 * @author suzuki-kei
 * @author someda
 * @since 2.0.0
 */
public class SpringContainerProvider implements ContainerProvider,
		ApplicationContextAware {

	private Container container;

	public Container getContainer() {
		return container;
	}

	public void setApplicationContext(
			final ApplicationContext applicationContext) throws BeansException {
		this.container = new SpringContainerImpl(applicationContext);
	}

	/**
	 * {@link ApplicationContext} による {@link Container} の実装です。
	 */
	private static class SpringContainerImpl implements Container {

		private final ApplicationContext applicationContext;

		public SpringContainerImpl(final ApplicationContext applicationContext) {
			this.applicationContext = applicationContext;
		}

		public <T> T lookup(final Class<T> type) throws LookupException {
			String[] names = BeanFactoryUtils
					.beanNamesForTypeIncludingAncestors(applicationContext,
							type);
			if (names == null || names.length < 1) {
				throw new LookupException(type + " component not found.");
			}
			return type.cast(applicationContext.getBean(names[0], type));
		}

	}

}
