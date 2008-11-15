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
import org.seasar.cubby.internal.beans.BeanDesc;
import org.seasar.cubby.internal.beans.BeanDescFactory;
import org.seasar.cubby.internal.beans.ParameterizedClassDesc;
import org.seasar.cubby.internal.beans.PropertyDesc;
import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.container.ContainerFactory;
import org.seasar.cubby.internal.controller.RequestParameterBinder;
import org.seasar.cubby.internal.converter.ConversionHelper;
import org.seasar.cubby.internal.converter.Converter;
import org.seasar.cubby.internal.converter.impl.ConversionHelperImpl;
import org.seasar.cubby.internal.factory.ConverterFactory;

/**
 * リクエストパラメータをオブジェクトへバインドするクラスの実装です。
 * 
 * @author baba
 * @since 1.1.0
 */
public class RequestParameterBinderImpl implements RequestParameterBinder {

	/** 変換のヘルパクラス。 */
	private ConversionHelper conversionHelper = new ConversionHelperImpl();

//	/**
//	 * 変換のヘルパクラスを設定します。
//	 * 
//	 * @param conversionHelper
//	 *            変換のヘルパクラス
//	 */
//	public void setConversionHelper(final ConversionHelper conversionHelper) {
//		this.conversionHelper = conversionHelper;
//	}

	/**
	 * {@inheritDoc}
	 */
	public void bind(final Map<String, Object[]> parameterMap,
			final Object dest, final ActionContext actionContext) {
		if (parameterMap == null) {
			return;
		}
		final Container container = ContainerFactory.getContainer();
		final ConverterFactory converterFactory = container
				.lookup(ConverterFactory.class);
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
						final Object value = convert(converterFactory, entry
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
	 * @param converterFacotry
	 *            コンバータファクトリ
	 * @param values
	 *            リクエストパラメータの値
	 * @param destPropertyDesc
	 *            出力先のプロパティの定義
	 * @return 変換された値
	 */
	private Object convert(final ConverterFactory converterFacotry,
			final Object[] values, final PropertyDesc destPropertyDesc) {
		final Class<?> destClass = destPropertyDesc.getPropertyType();

		final Converter converter = converterFacotry.getConverter(values[0]
				.getClass(), destClass);
		if (converter != null) {
			return converter.convertToObject(values[0], destClass,
					conversionHelper);
		}

		if (destClass.isArray()) {
			return convertToArray(converterFacotry, values, destClass
					.getComponentType());
		}
		if (List.class.isAssignableFrom(destClass)) {
			final List<Object> list = new ArrayList<Object>();
			convertToCollection(converterFacotry, values, list,
					destPropertyDesc);
			return list;
		}
		if (Set.class.isAssignableFrom(destClass)) {
			final Set<Object> set = new LinkedHashSet<Object>();
			convertToCollection(converterFacotry, values, set, destPropertyDesc);
			return set;
		}
		return convertToScalar(converterFacotry, values[0], destClass);
	}

	/**
	 * 指定された値を指定された要素の型の配列に変換します。
	 * 
	 * @param converterFactory
	 *            コンバータファクトリ
	 * @param values
	 *            変換する値
	 * @param componentType
	 *            要素の型
	 * @return 変換後の値
	 */
	private Object convertToArray(final ConverterFactory converterFactory,
			final Object[] values, final Class<?> componentType) {
		final Object dest = Array.newInstance(componentType, values.length);
		for (int i = 0; i < values.length; i++) {
			final Object convertedValue = convertToScalar(converterFactory,
					values[i], componentType);
			Array.set(dest, i, convertedValue);
		}
		return dest;
	}

	/**
	 * 指定された値を変換してコレクションに追加します。
	 * 
	 * @param converterFactory
	 *            コンバータファクトリ
	 * @param values
	 *            変換する値
	 * @param collection
	 *            コレクション
	 * @param propertyDesc
	 *            プロパティの定義
	 */
	private void convertToCollection(final ConverterFactory converterFactory,
			final Object[] values, final Collection<Object> collection,
			final PropertyDesc propertyDesc) {
		if (propertyDesc.isParameterized()) {
			final ParameterizedClassDesc parameterizedClassDesc = propertyDesc
					.getParameterizedClassDesc();
			final Class<?> destElementType = parameterizedClassDesc
					.getArguments()[0].getRawClass();
			for (final Object value : values) {
				final Object convertedValue = convertToScalar(converterFactory,
						value, destElementType);
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
	 * @param converterFactory
	 *            コンバータファクトリ
	 * @param value
	 *            変換する値
	 * @param destClass
	 *            変換する型
	 * @return 変換後の値
	 */
	private Object convertToScalar(final ConverterFactory converterFactory,
			final Object value, final Class<?> destClass) {
		if (value == null) {
			return null;
		}
		if (destClass.isAssignableFrom(value.getClass())) {
			return value;
		}
		final Converter converter = converterFactory.getConverter(value
				.getClass(), destClass);
		if (converter == null) {
			return null;
		}
		return converter.convertToObject(value, destClass, conversionHelper);
	}

}
