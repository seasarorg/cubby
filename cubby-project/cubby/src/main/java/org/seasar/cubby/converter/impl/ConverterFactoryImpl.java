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

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.seasar.cubby.controller.ClassDetector;
import org.seasar.cubby.controller.DetectClassProcessor;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.converter.ConverterFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;

/**
 * コンバータファクトリの実装クラスです。
 * 
 * @author baba
 * @since 1.1.0
 */
public class ConverterFactoryImpl implements ConverterFactory, DetectClassProcessor,
		Disposable {

	/** インスタンスが初期化済みであることを示します。 */
	private boolean initialized;

	/** このファクトリを管理しているコンテナです。 */
	private S2Container container;

	/** 命名規約。 */
	private NamingConvention namingConvention;

	/** クラスパスを走査してクラスを検出するクラス。 */
	private ClassDetector classDetector;

	/** コンバータのセットです。 */
	private Set<Converter> converters = new LinkedHashSet<Converter>();

	/** コンバータのキャッシュです。 */
	private Map<String, Converter> converterCache = new HashMap<String, Converter>();

	/**
	 * <code>ConverterFactoryImpl</code>のインスタンスを構築します。
	 */
	public ConverterFactoryImpl() {
	}

	/**
	 * コンテナを設定します。
	 * 
	 * @param container
	 *            コンテナ
	 */
	public void setContainer(final S2Container container) {
		this.container = container.getRoot();
	}

	/**
	 * 命名規約を設定します。
	 * 
	 * @param namingConvention
	 *            命名規約
	 */
	public void setNamingConvention(final NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
	}

	/**
	 * クラスパスを走査してクラスを検出するクラスを設定します。
	 * 
	 * @param classDetector
	 *            クラスパスを走査してクラスを設定します。
	 */
	public void setClassDetector(final ClassDetector classDetector) {
		this.classDetector = classDetector;
	}

	/**
	 * インスタンスを初期化します。
	 */
	public void initialize() {
		if (initialized) {
			return;
		}
		classDetector.detect();

		for (final Converter converter : Converter[].class.cast(container
				.findAllComponents(Converter.class))) {
			converters.add(converter);
		}
		DisposableUtil.add(this);
		initialized = true;
	}

	/**
	 * キャッシュ情報等を破棄し、インスタンスを未初期化状態に戻します。
	 * 
	 */
	public void dispose() {
		converters.clear();
		converterCache.clear();
		initialized = false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Converter getConverter(final Class<?> parameterType,
			final Class<?> objectType) {
		initialize();
		final Class<?> destType = ClassUtil
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
		final Converter converter = getDistanceTable(parameterType, objectType);
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

	private Converter getDistanceTable(final Class<?> parameterType,
			final Class<?> objectType) {
		final Map<Integer, Converter> distanceTable = new TreeMap<Integer, Converter>();
		for (final Converter converter : converters) {
			if (!converter.canConvert(parameterType, objectType)) {
				continue;
			}
			final int distance = getDistance(converter.getObjectType(),
					objectType);
			distanceTable.put(distance, converter);
		}
		if (distanceTable.isEmpty()) {
			return null;
		}
		return distanceTable.values().iterator().next();
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

	/**
	 * {@inheritDoc}
	 * <p>
	 * 指定されたパッケージ名、クラス名から導出されるクラスがコンバータだった場合はファクトリにコンバータを登録します。
	 * </p>
	 */
	public void processClass(final String packageName,
			final String shortClassName) {
		if (shortClassName.indexOf('$') != -1) {
			return;
		}
		final String className = ClassUtil.concatName(packageName,
				shortClassName);
		if (!namingConvention.isTargetClassName(className)) {
			return;
		}
		if (!className.endsWith(namingConvention.getConverterSuffix())) {
			return;
		}
		final Class<?> clazz = ClassUtil.forName(className);
		if (namingConvention.isSkipClass(clazz)) {
			return;
		}
		if ((clazz.getModifiers() & Modifier.ABSTRACT) != 0) {
			return;
		}
		if (!Converter.class.isAssignableFrom(clazz)) {
			return;
		}
		final Converter converter = Converter.class.cast(container
				.getComponent(clazz));
		converters.add(converter);
	}

}
