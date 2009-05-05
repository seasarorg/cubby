package org.seasar.cubby.plugins.spring;

import javax.servlet.ServletContext;

import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.Provider;
import org.seasar.cubby.spi.RequestParserProvider;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Cubby を <a href="http://www.springsource.org/">Spring Framework</a>
 * に統合するためのプラグインです。
 * 
 * <p>
 * このプラグインが提供するプロバイダは以下の通りです。
 * <ul>
 * <li>{@link BeanDescProvider}</li>
 * <li>{@link ContainerProvider}</li>
 * <li>{@link RequestParserProvider}</li>
 * <li>{@link PathResolverProvider}</li>
 * <li>{@link ConverterProvider}</li>
 * </ul>
 * </p>
 * 
 * @author suzuki-kei
 * @author someda
 * 
 * @since 2.0.0
 * 
 */
public class SpringPlugin extends AbstractPlugin {

	private ApplicationContext applicationContext;

	public SpringPlugin() {
		support(BeanDescProvider.class);
		support(ContainerProvider.class);
		support(RequestParserProvider.class);
		support(PathResolverProvider.class);
		support(ConverterProvider.class);
	}

	@Override
	public void initialize(ServletContext servletContext) {
		super.initialize(servletContext);
		this.applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
	}

	@Override
	public <S extends Provider> S getProvider(Class<S> service) {

		if (this.isSupport(service)) {
			String[] names = BeanFactoryUtils
					.beanNamesForTypeIncludingAncestors(applicationContext,
							service);
			if (names == null || names.length < 1) {
				// TODO
			}
			return service.cast(applicationContext.getBean(names[0]));
		} else {
			return null;
		}
	}

}
