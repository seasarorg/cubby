package org.seasar.cubby.plugins.spring.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.spi.impl.AbstractCachedConverterProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * コンバータプロバイダの実装クラスです。
 * 
 * @author suzuki-kei
 * @author someda
 * @since 2.0.0
 */
public class SpringConverterProvider extends AbstractCachedConverterProvider
		implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	private boolean initialized = false;

	private final Class<Converter> converterClass = Converter.class;

	private final Set<Converter> converters = new LinkedHashSet<Converter>();

	public void setApplicationContext(
			final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void initialize() {
		if (initialized) {
			return;
		}
		String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
				applicationContext, converterClass);
		for (String name : names) {
			Converter converter = converterClass.cast(applicationContext
					.getBean(name, converterClass));
			converters.add(converter);
		}
		initialized = true;
	}

	@Override
	protected Collection<Converter> getConverters() {
		return Collections.unmodifiableCollection(this.converters);
	}

}
