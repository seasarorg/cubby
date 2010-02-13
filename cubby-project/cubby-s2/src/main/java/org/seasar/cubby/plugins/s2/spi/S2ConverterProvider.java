/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.plugins.s2.spi;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.plugins.s2.detector.ClassDetector;
import org.seasar.cubby.plugins.s2.detector.DetectClassProcessor;
import org.seasar.cubby.spi.impl.AbstractCachedConverterProvider;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;

/**
 * S2Container 向けの {@link Converter} のプロバイダです。
 * 
 * @author baba
 */
public class S2ConverterProvider extends AbstractCachedConverterProvider
		implements DetectClassProcessor, Disposable {

	public static final String s2Container_BINDING = "bindingType=must";

	public static final String namingConvention_BINDING = "bindingType=must";

	public static final String classDetector_BINDING = "bindingType=must";

	/** S2 コンテナ。 */
	private S2Container s2Container;

	/** 命名規約。 */
	private NamingConvention namingConvention;

	/** クラスパスを走査してクラスを検出するクラス。 */
	private ClassDetector classDetector;

	/** インスタンスが初期化済みであることを示します。 */
	private boolean initialized;

	/** コンバータのセットです。 */
	private final Set<Converter> converters = new LinkedHashSet<Converter>();

	/**
	 * S2 コンテナを設定します。
	 * 
	 * @param s2Container
	 *            S2 コンテナ
	 */
	public void setS2Container(final S2Container s2Container) {
		this.s2Container = s2Container;
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
	 * {@inheritDoc}
	 */
	public Collection<Converter> getConverters() {
		initialize();
		return converters;
	}

	/**
	 * インスタンスを初期化します。
	 */
	public void initialize() {
		if (initialized) {
			return;
		}
		classDetector.detect();

		final Converter[] converters = (Converter[]) s2Container.getRoot()
				.findAllComponents(Converter.class);
		this.converters.addAll(Arrays.asList(converters));
		DisposableUtil.add(this);
		initialized = true;
	}

	/**
	 * キャッシュ情報等を破棄し、インスタンスを未初期化状態に戻します。
	 * 
	 */
	public void dispose() {
		this.converters.clear();
		super.clear();
		initialized = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Converter getConverter(final Class<?> parameterType,
			final Class<?> objectType) {
		initialize();
		return super.getConverter(parameterType, objectType);
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
		final Converter converter = (Converter) s2Container.getRoot()
				.getComponent(clazz);
		this.converters.add(converter);
	}

}
