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
package org.seasar.cubby.internal.controller.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.converter.impl.ConversionHelperImpl;
import org.seasar.cubby.internal.controller.RequestParameterBinder;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.BeanDescFactory;
import org.seasar.cubby.spi.beans.ParameterizedClassDesc;
import org.seasar.cubby.spi.beans.PropertyDesc;

/**
 * リクエストパラメータをオブジェクトへバインドするクラスの実装です。
 * 
 * @author baba
 * @since 1.1.0
 */
public class RequestParameterBinderImpl implements RequestParameterBinder {

	/** 変換のヘルパクラス。 */
	private final ConversionHelper conversionHelper = new ConversionHelperImpl();

	/**
	 * {@inheritDoc}
	 */
	public void bind(final Map<String, Object[]> parameterMap,
			final Object dest, final ActionContext actionContext) {
		if (parameterMap == null) {
			return;
		}
		final ConverterProvider converterProvider = ProviderFactory
				.get(ConverterProvider.class);
		final BeanDesc destBeanDesc = BeanDescFactory.getBeanDesc(dest
				.getClass());
		for (final Entry<String, Object[]> entry : parameterMap.entrySet()) {
			final String sourceName = entry.getKey();
			if (destBeanDesc.hasPropertyDesc(sourceName)) {
				final PropertyDesc destPropertyDesc = destBeanDesc
						.getPropertyDesc(sourceName);
				if (destPropertyDesc.isReadable()
						&& destPropertyDesc.isWritable()) {
					if (!actionContext.isBindRequestParameterToAllProperties()) {
						final RequestParameter requestParameter = destPropertyDesc
								.getAnnotation(RequestParameter.class);
						if (requestParameter == null) {
							continue;
						}
					}

					try {
						final Object value = convert(converterProvider, entry
								.getValue(), destPropertyDesc);
						destPropertyDesc.setValue(dest, value);
					} catch (final Exception e) {
						destPropertyDesc.setValue(dest, null);
					}
				}
			}
		}
	}

	/**
	 * 指定されたリクエストパラメータの値を出力先のプロパティの型に変換します。
	 * 
	 * @param converterProvider
	 *            コンバータプロバイダ
	 * @param values
	 *            リクエストパラメータの値
	 * @param destPropertyDesc
	 *            出力先のプロパティの定義
	 * @return 変換された値
	 */
	private Object convert(final ConverterProvider converterProvider,
			final Object[] values, final PropertyDesc destPropertyDesc) {
		final Class<?> destClass = destPropertyDesc.getPropertyType();

		final Converter converter = converterProvider.getConverter(values[0]
				.getClass(), destClass);
		if (converter != null) {
			return converter.convertToObject(values[0], destClass,
					conversionHelper);
		}

		if (destClass.isArray()) {
			return convertToArray(converterProvider, values, destClass
					.getComponentType());
		}
		if (List.class.isAssignableFrom(destClass)) {
			final List<Object> list = new ArrayList<Object>();
			convertToCollection(converterProvider, values, list,
					destPropertyDesc);
			return list;
		}
		if (Set.class.isAssignableFrom(destClass)) {
			final Set<Object> set = new LinkedHashSet<Object>();
			convertToCollection(converterProvider, values, set,
					destPropertyDesc);
			return set;
		}
		return convertToScalar(converterProvider, values[0], destClass);
	}

	/**
	 * 指定された値を指定された要素の型の配列に変換します。
	 * 
	 * @param converterFactory
	 *            コンバータプロバイダ
	 * @param values
	 *            変換する値
	 * @param componentType
	 *            要素の型
	 * @return 変換後の値
	 */
	private Object convertToArray(final ConverterProvider converterProvider,
			final Object[] values, final Class<?> componentType) {
		final Object dest = Array.newInstance(componentType, values.length);
		for (int i = 0; i < values.length; i++) {
			final Object convertedValue = convertToScalar(converterProvider,
					values[i], componentType);
			Array.set(dest, i, convertedValue);
		}
		return dest;
	}

	/**
	 * 指定された値を変換してコレクションに追加します。
	 * 
	 * @param converterProvider
	 *            コンバータプロバイダ
	 * @param values
	 *            変換する値
	 * @param collection
	 *            コレクション
	 * @param propertyDesc
	 *            プロパティの定義
	 */
	private void convertToCollection(final ConverterProvider converterProvider,
			final Object[] values, final Collection<Object> collection,
			final PropertyDesc propertyDesc) {
		if (propertyDesc.isParameterized()) {
			final ParameterizedClassDesc parameterizedClassDesc = propertyDesc
					.getParameterizedClassDesc();
			final Class<?> destElementType = parameterizedClassDesc
					.getArguments()[0].getRawClass();
			for (final Object value : values) {
				final Object convertedValue = convertToScalar(
						converterProvider, value, destElementType);
				collection.add(convertedValue);
			}
		} else {
			for (final Object value : values) {
				collection.add(value);
			}
		}
	}

	/**
	 * 指定された値を指定された型に変換します。
	 * 
	 * @param converterProvider
	 *            コンバータプロバイダ
	 * @param value
	 *            変換する値
	 * @param destClass
	 *            変換する型
	 * @return 変換後の値
	 */
	private Object convertToScalar(final ConverterProvider converterProvider,
			final Object value, final Class<?> destClass) {
		if (value == null) {
			return null;
		}
		if (destClass.isAssignableFrom(value.getClass())) {
			return value;
		}
		final Converter converter = converterProvider.getConverter(value
				.getClass(), destClass);
		if (converter == null) {
			return null;
		}
		return converter.convertToObject(value, destClass, conversionHelper);
	}

}
