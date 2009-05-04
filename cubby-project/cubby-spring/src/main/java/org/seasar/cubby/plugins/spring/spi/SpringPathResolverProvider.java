package org.seasar.cubby.plugins.spring.spi;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.spi.PathResolverProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * パスリゾルバプロバイダの実装クラスです。
 * 
 * @author suzuki-kei
 * @author someda
 * @since 2.0.0
 */
public class SpringPathResolverProvider implements PathResolverProvider,
		ApplicationContextAware {

	@Autowired
	private PathResolver pathResolver;

	private ApplicationContext applicationContext;

	private Class<Action> actionClass = Action.class;

	private boolean initialized = false;

	public void setApplicationContext(
			final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public PathResolver getPathResolver() {
		return pathResolver;
	}

	public void initialize() {
		if (initialized) {
			return;
		}
		String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
				applicationContext, actionClass);
		for (String name : names) {
			pathResolver.add(applicationContext.getType(name));
		}
		initialized = true;
	}

}
