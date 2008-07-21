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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.RequestParameterBindingType;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.controller.RequestParameterBinder;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.Converter;
import org.seasar.extension.dxo.converter.ConverterFactory;
import org.seasar.framework.beans.BeanDesc;
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

	/** Cubby の全体的な設定情報。 */
	private CubbyConfiguration cubbyConfiguration;

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
	 * Cubby の全体的な設定情報を設定します。
	 * 
	 * @param cubbyConfiguration
	 *            Cubby の全体的な設定情報
	 */
	public void setCubbyConfiguration(
			final CubbyConfiguration cubbyConfiguration) {
		this.cubbyConfiguration = cubbyConfiguration;
	}

	/**
	 * {@inheritDoc}
	 */
	public void bind(final Map<String, Object[]> parameterMap,
			final Object dest, final Method method) {
		if (parameterMap == null) {
			return;
		}
		final ConversionContext context = new ConversionContextImpl(
				converterFactory, cubbyConfiguration);
		final BeanDesc destBeanDesc = BeanDescFactory.getBeanDesc(dest
				.getClass());
		for (final Entry<String, Object[]> entry : parameterMap.entrySet()) {
			final String sourceName = entry.getKey();
			if (destBeanDesc.hasPropertyDesc(sourceName)) {
				final PropertyDesc destPropertyDesc = destBeanDesc
						.getPropertyDesc(sourceName);
				if (destPropertyDesc.isReadable()
						&& destPropertyDesc.isWritable()) {
					final Object[] values = entry.getValue();
					final Class<?> destClass = destPropertyDesc
							.getPropertyType();
					if (!isBindToAllProperties(method)) {
						final RequestParameter requestParameter = getRequestParameterAnnotation(destPropertyDesc);
						if (requestParameter == null) {
							continue;
						}
					}
					if (destClass.isArray()
							|| Collection.class.isAssignableFrom(destClass)) {
						final Class<?> sourceClass = values.getClass();
						final Converter converter = converterFactory
								.getConverter(sourceClass, destClass);
						final Object convertedValue = converter.convert(values,
								destClass, context);
						destPropertyDesc.setValue(dest, convertedValue);
					} else {
						final Object value = values[0];
						final Class<?> sourceClass = value.getClass();
						final Converter converter = converterFactory
								.getConverter(sourceClass, destClass);
						final Object convertedValue = converter.convert(value,
								destClass, context);
						destPropertyDesc.setValue(dest, convertedValue);
					}
				}
			}
		}
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
			allProperties = true;
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
