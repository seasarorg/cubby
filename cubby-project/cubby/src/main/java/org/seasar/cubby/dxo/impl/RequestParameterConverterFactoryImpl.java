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
package org.seasar.cubby.dxo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.Converter;
import org.seasar.extension.dxo.converter.impl.ConverterFactoryImpl;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.DisposableUtil;
import org.seasar.framework.util.ResourceUtil;

/**
 * リクエストのパラメータとアクションのプロパティとの変換に使用する{@link org.seasar.extension.dxo.converter.Converter コンバータ}のファクトリクラスです。
 * 
 * @author baba
 * @since 1.0.0
 */
public class RequestParameterConverterFactoryImpl extends ConverterFactoryImpl {

	/** ロガー。 */
	private static final Logger logger = Logger
			.getLogger(RequestParameterConverterFactoryImpl.class);

	/** 空のコンバータの配列。 */
	private static final Converter[] EMPTY_CONVERTER_ARRAY = new Converter[0];

	/** コンバータのラッパのキャッシュ。 */
	private static final Map<Converter, Converter> wrapperCache = new HashMap<Converter, Converter>();

	/** コンバータが定義されたコンテナのパス。 */
	private final List<String> converterContainerPaths = new ArrayList<String>();

	/** コンバータが定義されたコンテナ。 */
	private final List<S2Container> containers = new ArrayList<S2Container>();

	/** アプリケーションで定義したコンバータを使用するかどうかを示します。 */
	private boolean includeApplicationConverters = true;

	/** 初期化されたかどうかを示します。 */
	private boolean initialized = false;

	/**
	 * コンバータが定義されたコンテナのパスを追加します。
	 * 
	 * @param converterContainerPath
	 *            コンバータが定義されたコンテナのパス
	 */
	public void addConverterContainerPath(final String converterContainerPath) {
		this.converterContainerPaths.add(converterContainerPath);
	}

	/**
	 * アプリケーションで定義したコンバータを使用するかどうかを設定します。
	 * 
	 * @param includeApplicationConverters
	 *            アプリケーションで定義したコンバータを使用する場合は <code>true</code>、そうでない場合は
	 *            <code>false</code>
	 */
	public void setIncludeApplicationConverters(
			final boolean includeApplicationConverters) {
		this.includeApplicationConverters = includeApplicationConverters;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize() {
		if (initialized) {
			return;
		}

		super.initialize();

		final Map<Class<? extends Converter>, Converter> converterMap = new HashMap<Class<? extends Converter>, Converter>();
		for (final String path : this.converterContainerPaths) {
			if (ResourceUtil.isExist(path)) {
				if (logger.isDebugEnabled()) {
					logger.log("DCUB0011", new Object[] { path });
				}
				final S2Container container = S2ContainerFactory.create(path);
				containers.add(container);
				for (final ComponentDef componentDef : container
						.findAllComponentDefs(Converter.class)) {
					final Converter converter = (Converter) componentDef
							.getComponent();
					converterMap.put(converter.getClass(), converter);
					if (logger.isDebugEnabled()) {
						logger.log("DCUB0010", new Object[] { converter });
					}
				}
				if (logger.isDebugEnabled()) {
					logger
							.log("DCUB0012",
									new Object[] { container.getPath() });
				}
			}
		}

		if (includeApplicationConverters) {
			final List<Converter> converterList = new ArrayList<Converter>(
					converterMap.values());
			if (logger.isDebugEnabled()) {
				logger.log("DCUB0013", null);
			}
			for (final Converter converter : converters) {
				if (!converterMap.containsKey(converter.getClass())) {
					converterList.add(converter);
					if (logger.isDebugEnabled()) {
						logger.log("DCUB0010", new Object[] { converter });
					}
				}
			}
			if (logger.isDebugEnabled()) {
				logger.log("DCUB0014", null);
			}
			converters = converterList.toArray(EMPTY_CONVERTER_ARRAY);
		} else {
			converters = converterMap.values().toArray(EMPTY_CONVERTER_ARRAY);
		}

		DisposableUtil.add(this);
		initialized = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		wrapperCache.clear();
		for (final S2Container container : this.containers) {
			container.destroy();
		}
		containers.clear();
		super.dispose();
		initialized = false;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * ここで生成されるコンバータは、{@link Converter#convert(Object, Class, ConversionContext)}の実行時に例外が発生した場合は
	 * <code>null</code> を返すようにラップされます。
	 * </p>
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Converter getConverter(final Class sourceClass, final Class destClass) {
		initialize();
		final Converter converter = super.getConverter(sourceClass, destClass);
		final Converter wrapper;
		if (wrapperCache.containsKey(converter)) {
			wrapper = wrapperCache.get(converter);
		} else {
			wrapper = new DisregardExceptionConverterWrapper(converter);
			wrapperCache.put(converter, wrapper);
		}
		return wrapper;
	}

	/**
	 * 変換時の例外を無視するように{@link Converter}をラップするクラスです。
	 * 
	 * @author baba
	 */
	private static class DisregardExceptionConverterWrapper implements
			Converter {

		/** ラップするコンバータ。 */
		private final Converter converter;

		/**
		 * インスタンス化します。
		 * 
		 * @param converter
		 *            ラップするコンバータ
		 */
		private DisregardExceptionConverterWrapper(final Converter converter) {
			this.converter = converter;
		}

		/**
		 * {@inheritDoc}
		 * <p>
		 * 変換時に例外が発生した場合は <code>null</code> を返します。
		 * </p>
		 */
		@SuppressWarnings("unchecked")
		public Object convert(final Object source, final Class destClass,
				final ConversionContext context) {
			try {
				return converter.convert(source, destClass, context);
			} catch (final Exception e) {
				return null;
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see #convert(Object, Class, ConversionContext)
		 */
		public void convert(final Object source, final Object dest,
				final ConversionContext context) {
			converter.convert(source, dest, context);
		}

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("unchecked")
		public Class getDestClass() {
			return converter.getDestClass();
		}

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("unchecked")
		public Class[] getSourceClasses() {
			return converter.getSourceClasses();
		}

	}

}
