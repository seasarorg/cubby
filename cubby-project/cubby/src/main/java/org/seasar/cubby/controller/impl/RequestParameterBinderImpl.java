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

import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.RequestParameterBindingType;
import org.seasar.cubby.controller.RequestParameterBinder;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.converter.ConverterFactory;
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
	 * {@inheritDoc}
	 */
	public void bind(final Map<String, Object[]> parameterMap,
			final Object dest, final Method method) {
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
					if (!isBindToAllProperties(method)) {
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

	private Object convert(final Object[] values,
			final PropertyDesc destPropertyDesc) {
		final Class<?> destClass = destPropertyDesc.getPropertyType();

		final Converter converter = converterFactory.getConverter(destClass);
		if (converter != null) {
			return converter.convertToObject(values[0]);
		}

		if (destClass.isArray()) {
			return convertToArray(values, destClass);
		}
		if (List.class.isAssignableFrom(destClass)) {
			final List<Object> list = new ArrayList<Object>();
			convertToCollection(values, destClass, list, destPropertyDesc
					.getParameterizedClassDesc());
			return list;
		}
		if (Set.class.isAssignableFrom(destClass)) {
			final Set<Object> set = new LinkedHashSet<Object>();
			convertToCollection(values, destClass, set, destPropertyDesc
					.getParameterizedClassDesc());
			return set;
		}
		return convertToScalar(values[0], destClass);
	}

	private Object convertToArray(final Object[] source,
			final Class<?> destClass) {
		final Class<?> destComponentType = destClass.getComponentType();
		final Object dest = Array.newInstance(destComponentType, source.length);
		for (int i = 0; i < source.length; i++) {
			final Object convertedValue = convertToScalar(source[i],
					destComponentType);
			Array.set(dest, i, convertedValue);
		}
		return dest;
	}

	private void convertToCollection(final Object[] source,
			final Class<?> destClass, final Collection<Object> dest,
			final ParameterizedClassDesc parameterizedClassDesc) {
		if (parameterizedClassDesc == null) {
			for (final Object value : source) {
				dest.add(value);
			}
		} else {
			final Class<?> destElementType = parameterizedClassDesc
					.getArguments()[0].getRawClass();
			for (final Object value : source) {
				final Object convertedValue = convertToScalar(value,
						destElementType);
				dest.add(convertedValue);
			}
		}
	}

	private Object convertToScalar(final Object source, final Class<?> destClass) {
		if (source == null) {
			return null;
		}
		if (destClass.isAssignableFrom(source.getClass())) {
			return source;
		}
		final Converter converter = converterFactory.getConverter(destClass);
		if (converter == null) {
			return null;
		}
		return converter.convertToObject(source);
	}

	/**
	 * 指定されたアクションメソッドを実行する際に、オブジェクトのすべてのプロパティにリクエストパラメータをバインドするかを示します。
	 * 
	 * @param method
	 *            アクションメソッド
	 * @return オブジェクトのすべてのプロパティにリクエストパラメータをバインドする場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	private static boolean isBindToAllProperties(final Method method) {
		final boolean allProperties;
		if (method.isAnnotationPresent(Form.class)) {
			final Form form = method.getAnnotation(Form.class);
			final RequestParameterBindingType type = form.type();
			switch (type) {
			case ALL_PROPERTIES:
				allProperties = true;
				break;
			case ONLY_SPECIFIED_PROPERTIES:
				allProperties = false;
				break;
			default:
				throw new IllegalStateException(type.toString());
			}
		} else {
			allProperties = false;
		}
		return allProperties;
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
