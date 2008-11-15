package org.seasar.cubby.internal.factory.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.internal.converter.Converter;
import org.seasar.cubby.internal.util.ClassUtils;

public abstract class AbstractCachedConverterFactory extends AbstractConverterFactory {

	/** コンバータのキャッシュです。 */
	private Map<String, Converter> converterCache = new HashMap<String, Converter>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Converter getConverter(final Class<?> parameterType,
			final Class<?> objectType) {
		final Class<?> destType = ClassUtils
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
