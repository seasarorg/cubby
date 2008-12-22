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
package org.seasar.cubby.plugins.guice.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.plugins.guice.InjectorFactory;
import org.seasar.cubby.spi.impl.AbstractCachedConverterProvider;

import com.google.inject.Injector;

public class GuiceConverterProvider extends AbstractCachedConverterProvider {

	private Collection<Converter> converters;

	public GuiceConverterProvider() {
		final Injector injector = InjectorFactory.getInjector();
		final ConverterClassesFactory converterClassesFactory = injector
				.getInstance(ConverterClassesFactory.class);
		final List<Converter> converters = new ArrayList<Converter>();
		for (final Class<? extends Converter> converterClass : converterClassesFactory
				.getConverterClasses()) {
			final Converter converter = injector.getInstance(converterClass);
			converters.add(converter);
		}
		this.converters = Collections.unmodifiableCollection(converters);
	}

	@Override
	protected Collection<Converter> getConverters() {
		return converters;
	}

	public static interface ConverterClassesFactory {
		Collection<Class<? extends Converter>> getConverterClasses();
	}

}
