package org.seasar.cubby.plugins.guice.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.internal.spi.impl.AbstractCachedConverterProvider;
import org.seasar.cubby.plugins.guice.InjectorFactory;

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
