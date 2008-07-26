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
package org.seasar.cubby.converter.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.converter.ConverterFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;

/**
 * コンバータファクトリの実装クラスです。
 * 
 * @author baba
 * @since 1.1.0
 */
public class ConverterFactoryImpl implements ConverterFactory, Disposable {

	/** プリミティブ型の配列クラスとラッパー型の配列クラスのマッピング */
	protected static Map<Class<?>, Class<?>> PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY = new HashMap<Class<?>, Class<?>>();
	static {
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(boolean[].class, Boolean[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(char[].class, Character[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(byte[].class, Byte[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(short[].class, Short[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(int[].class, Integer[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(long[].class, Long[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(float[].class, Float[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(double[].class, Double[].class);
	}

	/** インスタンスが初期化済みであることを示します。 */
	protected boolean initialized;

	/** このファクトリを管理しているS2コンテナです。 */
	protected S2Container container;

	/** S2コンテナに登録されているコンバータの配列です。 */
	protected Converter[] converters;

	/** コンバータのキャッシュです。 */
	protected Map<String, Converter> converterCache = new HashMap<String, Converter>();

	/**
	 * <code>ConverterFactoryImpl</code>のインスタンスを構築します。
	 * 
	 */
	public ConverterFactoryImpl() {
	}

	/**
	 * S2コンテナを設定します。
	 * 
	 * @param container
	 *            S2コンテナ
	 */
	public void setContainer(final S2Container container) {
		this.container = container.getRoot();
	}

	/**
	 * インスタンスを初期化します。
	 */
	public void initialize() {
		if (initialized) {
			return;
		}
		converters = (Converter[]) container.findAllComponents(Converter.class);
		DisposableUtil.add(this);
		initialized = true;
	}

	/**
	 * キャッシュ情報等を破棄し、インスタンスを未初期化状態に戻します。
	 * 
	 */
	public void dispose() {
		converters = null;
		converterCache.clear();
		initialized = false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Converter getConverter(final Class<?> conversionClass) {
		initialize();
		final Class<?> destType = ClassUtil
				.getWrapperClassIfPrimitive(conversionClass);
		final String cacheKey = destType.getName();
		final Converter converter = converterCache.get(cacheKey);
		if (converter != null) {
			return converter;
		}
		return detectConverter(destType);
	}

	private Converter detectConverter(final Class<?> conversionClass) {
		final Converter converter = getDistanceTable(conversionClass);
		final String cacheKey = conversionClass.getName();
		converterCache.put(cacheKey, converter);
		return converter;
	}

	private Converter getDistanceTable(final Class<?> conversionClass) {
		final Map<Integer, Converter> distanceTable = new TreeMap<Integer, Converter>();
		for (final Converter converter : converters) {
			if (!canConvert(conversionClass, converter)) {
				continue;
			}
			final int distance = getDistance(converter.getConversionClass(),
					conversionClass);
			distanceTable.put(distance, converter);
		}
		if (distanceTable.isEmpty()) {
			return null;
		}
		return distanceTable.values().iterator().next();
	}

	private boolean canConvert(final Class<?> conversionClass,
			final Converter converter) {
		if (!converter.getConversionClass().isAssignableFrom(conversionClass)) {
			final Class<?> wrapperArray = PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY
					.get(conversionClass);
			if (wrapperArray != null) {
				return canConvert(wrapperArray, converter);
			}
			return false;
		}
		return true;
	}

	private int getDistance(final Class<?> assigner, final Class<?> assignee) {
		return getDistance(assigner, assignee, 0);
	}

	private int getDistance(final Class<?> assigner, final Class<?> assignee,
			final int distance) {
		if (assignee.equals(assigner)) {
			return distance;
		}
		if (Enum.class.equals(assigner) && assignee.isEnum()) {
			return distance + 5;
		}
		if (isImplements(assigner, assignee)) {
			return distance + 5;
		}

		final Class<?> superClass = assigner.getSuperclass();
		if (superClass == null) {
			return distance + 10;
		}
		return getDistance(superClass, assignee, distance + 10);
	}

	private boolean isImplements(final Class<?> assigner,
			final Class<?> assignee) {
		return !assigner.isInterface() && assignee.isInterface()
				&& assignee.isAssignableFrom(assigner);
	}

}
