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
package org.seasar.cubby.controller.impl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.RequestParameterBindingType;
import org.seasar.cubby.controller.RequestParameterBinder;
import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.converter.ConverterFactory;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.ParameterizedClassDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * リクエストパラメータをオブジェクトへバインドするクラスの実装です。
 * 
 * @author baba
 * @since 1.1.0
 */
public class RequestParameterBinderImpl implements RequestParameterBinder {

	/** コンバータのファクトリクラス。 */
	private ConverterFactory converterFactory;

	/** 変換のヘルパクラス。 */
	private ConversionHelper conversionHelper;

	/**
	 * コンバータのファクトリクラスを設定します。
	 * 
	 * @param converterFactory
	 *            コンバータのファクトリクラス
	 */
	public void setConverterFactory(final ConverterFactory converterFactory) {
		this.converterFactory = converterFactory;
	}

	/**
	 * 変換のヘルパクラスを設定します。
	 * 
	 * @param conversionHelper
	 *            変換のヘルパクラス
	 */
	public void setConversionHelper(final ConversionHelper conversionHelper) {
		this.conversionHelper = conversionHelper;
	}

	/**
	 * {@inheritDoc}
	 */
	public void bind(final Map<String, Object[]> parameterMap,
			final Object dest, final Class<? extends Action> actionClass,
			final Method method) {
		if (parameterMap == null) {
			return;
		}
		final BeanDesc destBeanDesc = BeanDescFactory.getBeanDesc(dest
				.getClass());
		for (final Entry<String, Object[]> entry : parameterMap.entrySet()) {
			final String sourceName = entry.getKey();
			if (destBeanDesc.hasPropertyDesc(sourceName)) {
				final PropertyDesc destPropertyDesc = destBeanDesc
						.getPropertyDesc(sourceName);
				if (destPropertyDesc.isReadable()
						&& destPropertyDesc.isWritable()) {
					if (!isBindToAllProperties(actionClass, method)) {
						final RequestParameter requestParameter = getRequestParameterAnnotation(destPropertyDesc);
						if (requestParameter == null) {
							continue;
						}
					}

					try {
						final Object value = convert(entry.getValue(),
								destPropertyDesc);
						destPropertyDesc.setValue(dest, value);
					} catch (Exception e) {
						destPropertyDesc.setValue(dest, null);
					}
				}
			}
		}
	}

	/**
	 * 指定されたリクエストパラメータの値を出力先のプロパティの型に変換します。
	 * 
	 * @param values
	 *            リクエストパラメータの値
	 * @param destPropertyDesc
	 *            出力先のプロパティの定義
	 * @return 変換された値
	 */
	private Object convert(final Object[] values,
			final PropertyDesc destPropertyDesc) {
		final Class<?> destClass = destPropertyDesc.getPropertyType();

		final Converter converter = converterFactory.getConverter(values[0]
				.getClass(), destClass);
		if (converter != null) {
			return converter.convertToObject(values[0], destClass,
					conversionHelper);
		}

		if (destClass.isArray()) {
			return convertToArray(values, destClass.getComponentType());
		}
		if (List.class.isAssignableFrom(destClass)) {
			final List<Object> list = new ArrayList<Object>();
			convertToCollection(values, list, destPropertyDesc
					.getParameterizedClassDesc());
			return list;
		}
		if (Set.class.isAssignableFrom(destClass)) {
			final Set<Object> set = new LinkedHashSet<Object>();
			convertToCollection(values, set, destPropertyDesc
					.getParameterizedClassDesc());
			return set;
		}
		return convertToScalar(values[0], destClass);
	}

	/**
	 * 指定された値を指定された要素の型の配列に変換します。
	 * 
	 * @param values
	 *            変換する値
	 * @param componentType
	 *            要素の型
	 * @return 変換後の値
	 */
	private Object convertToArray(final Object[] values,
			final Class<?> componentType) {
		final Object dest = Array.newInstance(componentType, values.length);
		for (int i = 0; i < values.length; i++) {
			final Object convertedValue = convertToScalar(values[i],
					componentType);
			Array.set(dest, i, convertedValue);
		}
		return dest;
	}

	/**
	 * 指定された値を変換してコレクションに追加します。
	 * 
	 * @param values
	 *            変換する値
	 * @param collection
	 *            コレクション
	 * @param parameterizedClassDesc
	 *            パラメタ化された要素の定義
	 */
	private void convertToCollection(final Object[] values,
			final Collection<Object> collection,
			final ParameterizedClassDesc parameterizedClassDesc) {
		if (parameterizedClassDesc == null) {
			for (final Object value : values) {
				collection.add(value);
			}
		} else {
			final Class<?> destElementType = parameterizedClassDesc
					.getArguments()[0].getRawClass();
			for (final Object value : values) {
				final Object convertedValue = convertToScalar(value,
						destElementType);
				collection.add(convertedValue);
			}
		}
	}

	/**
	 * 指定された値を指定された型に変換します。
	 * 
	 * @param value
	 *            変換する値
	 * @param destClass
	 *            変換する型
	 * @return 変換後の値
	 */
	private Object convertToScalar(final Object value, final Class<?> destClass) {
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

	/**
	 * 指定されたアクションメソッドを実行する際に、オブジェクトのすべてのプロパティにリクエストパラメータをバインドするかを示します。
	 * 
	 * @param method
	 *            アクションメソッド
	 * @return オブジェクトのすべてのプロパティにリクエストパラメータをバインドする場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	private static boolean isBindToAllProperties(
			final Class<? extends Action> actionClass, final Method method) {
		final Form form = CubbyUtils.getForm(actionClass, method);
		if (form == null) {
			return false;
		}

		final RequestParameterBindingType type = form.type();
		switch (type) {
		case ALL_PROPERTIES:
			return true;
		case ONLY_SPECIFIED_PROPERTIES:
			return false;
		default:
			throw new IllegalStateException(type.toString());
		}
	}

	/**
	 * 指定されたプロパティから {@link RequestParameter} アノテーションを取得します。
	 * 
	 * @param propertyDesc
	 *            プロパティの定義
	 * @return {@link RequestParameter} アノテーション、指定されたプロパティが装飾されていない場合は
	 *         <code>null</code>
	 */
	private static RequestParameter getRequestParameterAnnotation(
			final PropertyDesc propertyDesc) {
		final RequestParameter request;
		if (propertyDesc.hasWriteMethod()) {
			final Method method = propertyDesc.getWriteMethod();
			if (method.isAnnotationPresent(RequestParameter.class)) {
				request = method.getAnnotation(RequestParameter.class);
			} else {
				final Field field = propertyDesc.getField();
				request = field.getAnnotation(RequestParameter.class);
			}
		} else {
			final Field field = propertyDesc.getField();
			request = field.getAnnotation(RequestParameter.class);
		}
		return request;
	}

}
