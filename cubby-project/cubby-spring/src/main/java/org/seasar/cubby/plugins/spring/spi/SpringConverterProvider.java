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
