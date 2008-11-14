package org.seasar.cubby.plugins.guice.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.factory.impl.AbstractCachedConverterFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class GuiceConverterFactory extends AbstractCachedConverterFactory {

	private final Collection<Converter> converters;

	@Inject
	public GuiceConverterFactory(final Injector injector, final ConverterClassesFactory converterClassesFactory) {
		final List<Converter> converters = new ArrayList<Converter>();
		for (Class<? extends Converter> converterClass : converterClassesFactory.getConverterClasses()) {
			Converter converter = injector.getInstance(converterClass);
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
