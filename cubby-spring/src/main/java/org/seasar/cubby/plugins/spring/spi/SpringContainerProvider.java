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
