package org.seasar.cubby.dxo.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.Converter;
import org.seasar.extension.dxo.converter.ConverterFactory;
import org.seasar.extension.dxo.converter.impl.AbstractConverter;
import org.seasar.framework.util.StringUtil;

public class RemoveEmptyElementConverterImpl extends AbstractConverter {

	public RemoveEmptyElementConverterImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	public Class[] getSourceClasses() {
		return new Class[] { Object[].class };
	}

	public Class<?> getDestClass() {
		return Object[].class;
	}

	@SuppressWarnings("unchecked")
	public Object convert(final Object source, final Class destClass,
			final ConversionContext context) {

		final ConverterFactory converterFactory = context.getConverterFactory();
		final Converter converter = converterFactory.getConverter(source
				.getClass(), destClass);
		final Object[] converted = (Object[]) converter.convert(source,
				destClass, context);

		final List<Object> removed = new ArrayList<Object>();
		Class<?> elementType = null;
		for (final Object value : converted) {
			if (!isEmpty(value)) {
				removed.add(value);
				elementType = value.getClass();
			}
		}

		if (removed.isEmpty()) {
			return null;
		}

		final Object[] array = (Object[]) Array.newInstance(elementType,
				removed.size());
		return removed.toArray(array);
	}

	private static boolean isEmpty(final Object value) {
		if (value instanceof String) {
			if (StringUtil.isEmpty((String) value)) {
				return true;
			}
		} else {
			if (value == null) {
				return true;
			}
		}
		return false;
	}
}
