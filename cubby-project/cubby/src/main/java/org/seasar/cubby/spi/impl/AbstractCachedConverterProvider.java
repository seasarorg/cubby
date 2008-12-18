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
package org.seasar.cubby.spi.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.converter.Converter;

/**
 * コンバータのキャッシュを備えたコンバータプロバイダのスケルトン実装です。
 * 
 * @author baba
 * @since 2.0.0
 */
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
