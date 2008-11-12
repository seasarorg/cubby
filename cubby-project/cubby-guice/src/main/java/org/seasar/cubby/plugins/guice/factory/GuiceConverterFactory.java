package org.seasar.cubby.plugins.guice.factory;

import java.util.Collection;

import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.factory.impl.AbstractCachedConverterFactory;

import com.google.inject.Inject;

public class GuiceConverterFactory extends AbstractCachedConverterFactory {

	private final Collection<Converter> converters;

	@Inject
	public GuiceConverterFactory(final Collection<Converter> converters) {
		this.converters = converters;
	}

	@Override
	protected Collection<Converter> getConverters() {
		return converters;
	}

}
