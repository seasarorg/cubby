package org.seasar.cubby.spi.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.converter.Converter;

public abstract class AbstractCachedConverterProvider extends
		AbstractConverterProvider {

	/** コンバータのキャッシュです。 */
	private final Map<String, Converter> converterCache = new HashMap<String, Converter>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Converter getConverter(final Class<?> parameterType,
			final Class<?> objectType) {
		final Class<?> destType = ConversionUtils
				.getWrapperClassIfPrimitive(objectType);
		final String cacheKey = cacheKey(parameterType, destType);
		final Converter converter = converterCache.get(cacheKey);
		if (converter != null) {
			return converter;
		}
		return detectConverter(parameterType, destType);
	}

	private Converter detectConverter(final Class<?> parameterType,
			final Class<?> objectType) {
		final Converter converter = super.getConverter(parameterType,
				objectType);
		final String cacheKey = cacheKey(parameterType, objectType);
		converterCache.put(cacheKey, converter);
		return converter;
	}

	private static String cacheKey(final Class<?> parameterType,
			final Class<?> objectType) {
		if (parameterType == null) {
			return objectType.getName();
		}
		return parameterType.getName() + objectType.getName();
	}

	/**
	 * キャッシュをクリアします。
	 */
	protected void clear() {
		this.converterCache.clear();
	}

}
