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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Cubby を <a href="http://www.springsource.org/">Spring Framework</a>
 * に統合するためのプラグインです。
 * 
 * <p>
 * アプリケーションが使用する XML ファイルを WEB 配備記述子の初期化パラメータ
 * {@value ContextLoader#CONFIG_LOCATION_PARAM} に指定してください。 また、
 * {@link ContextLoaderListener} と {@link RequestContextListener} をリスナに設定してください。
 * </p>
 * 
 * <p>
 * 例
 * 
 * <pre>
 * &lt;context-param&gt;
 *   &lt;param-name&gt;{@value ContextLoader#CONFIG_LOCATION_PARAM}&lt;/paanm-name&gt;
 *   &lt;param-value&gt;/WEB-INF/classes/applicationContext.xml&lt;/param-value&gt;
 * &lt;/context-param&gt;
 * 
 * &lt;listener&gt;
 *   &lt;listener-class&gt;org.springframework.web.context.ContextLoaderListener&lt;listener-class&gt;
 * &lt;/listener&gt;
 * 
 * &lt;listener&gt;
 *   &lt;listener-class&gt;org.springframework.web.context.request.RequestContextListener&lt;listener-class&gt;
 * &lt;/listener&gt;
 * </pre>
 * 
 * </p>
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
 * @see <a
 *      href="http://static.springframework.org/spring/docs/2.5.x/reference/beans.html#context-create">Convenient
 *      ApplicationContext instantiation for web applications</a>
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
	public void initialize(final ServletContext servletContext) {
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
			return service.cast(applicationContext.getBean(names[0]));
		} else {
			return null;
		}
	}

}
