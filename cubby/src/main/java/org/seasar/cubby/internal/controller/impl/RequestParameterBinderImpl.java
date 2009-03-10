/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.FieldInfo;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.converter.ConversionException;
import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.converter.impl.ConversionHelperImpl;
import org.seasar.cubby.internal.controller.RequestParameterBinder;
import org.seasar.cubby.internal.util.StringUtils;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.BeanDescFactory;
import org.seasar.cubby.spi.beans.ParameterizedClassDesc;
import org.seasar.cubby.spi.beans.PropertyDesc;
import org.seasar.cubby.validator.MessageInfo;

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
			final Object dest, final ActionContext actionContext,
			final ActionErrors errors) {
		if (parameterMap == null || parameterMap.isEmpty()) {
			return;
		}
		final ConverterProvider converterProvider = ProviderFactory
				.get(ConverterProvider.class);
		final BeanDesc destBeanDesc = BeanDescFactory.getBeanDesc(dest
				.getClass());

		for (final PropertyDesc destPropertyDesc : destBeanDesc
				.getPropertyDescs()) {
			final RequestParameter requestParameter = destPropertyDesc
					.getAnnotation(RequestParameter.class);
			if (!actionContext.isBindRequestParameterToAllProperties()
					&& requestParameter == null) {
				continue;
			}

			final String parameterName;
			if (requestParameter != null
					&& !StringUtils.isEmpty(requestParameter.name())) {
				parameterName = requestParameter.name();
			} else {
				parameterName = destPropertyDesc.getPropertyName();
			}

			if (!parameterMap.containsKey(parameterName)) {
				continue;
			}
			final Object[] parameterValue = parameterMap.get(parameterName);

			final Class<? extends Converter> converterType;
			if (requestParameter != null) {
				converterType = requestParameter.converter();
			} else {
				converterType = null;
			}

			final Object value = convert(converterProvider, parameterValue,
					destPropertyDesc, converterType, parameterName, errors);
			destPropertyDesc.setValue(dest, value);
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
	 * @param converterType
	 *            コンバータの型
	 * @param errors
	 * @param parameterName
	 * @return 変換された値
	 */
	private Object convert(final ConverterProvider converterProvider,
			final Object[] values, final PropertyDesc destPropertyDesc,
			final Class<? extends Converter> converterType,
			final String parameterName, final ActionErrors errors) {
		final Class<?> destClass = destPropertyDesc.getPropertyType();

		final Converter converter;
		if (converterType != null && !converterType.equals(Converter.class)) {
			converter = converterProvider.getConverter(converterType);
		} else if (values[0] != null) {
			converter = converterProvider.getConverter(values[0].getClass(),
					destClass);
		} else {
			converter = null;
		}
		if (converter != null) {
			try {
				return converter.convertToObject(values[0], destClass,
						conversionHelper);
			} catch (final ConversionException e) {
				final FieldInfo fieldInfo = new FieldInfo(parameterName);
				final MessageInfo messageInfo = e.getMessageInfo();
				errors.add(messageInfo.builder().fieldNameKey(parameterName)
						.toString(), fieldInfo);
				return null;
			}
		}

		if (destClass.isArray()) {
			return convertToArray(converterProvider, values, destClass
					.getComponentType(), parameterName, errors);
		}
		if (List.class.isAssignableFrom(destClass)) {
			final List<Object> list = new ArrayList<Object>();
			convertToCollection(converterProvider, values, list,
					destPropertyDesc, parameterName, errors);
			return list;
		}
		if (Set.class.isAssignableFrom(destClass)) {
			final Set<Object> set = new LinkedHashSet<Object>();
			convertToCollection(converterProvider, values, set,
					destPropertyDesc, parameterName, errors);
			return set;
		}

		try {
			return convertToScalar(converterProvider, values[0], destClass);
		} catch (final ConversionException e) {
			final FieldInfo fieldInfo = new FieldInfo(parameterName);
			final MessageInfo messageInfo = e.getMessageInfo();
			errors.add(messageInfo.builder().fieldNameKey(parameterName)
					.toString(), fieldInfo);
			return null;
		}
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
			final Object[] values, final Class<?> componentType,
			final String parameterName, final ActionErrors errors) {
		final Object dest = Array.newInstance(componentType, values.length);
		for (int i = 0; i < values.length; i++) {
			try {
				final Object convertedValue = convertToScalar(
						converterProvider, values[i], componentType);
				Array.set(dest, i, convertedValue);
			} catch (final ConversionException e) {
				final FieldInfo fieldInfo = new FieldInfo(parameterName, i);
				final MessageInfo messageInfo = e.getMessageInfo();
				errors.add(messageInfo.builder().fieldNameKey(parameterName)
						.toString(), fieldInfo);
			}
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
			final PropertyDesc propertyDesc, final String parameterName,
			final ActionErrors errors) {
		if (propertyDesc.isParameterized()) {
			final ParameterizedClassDesc parameterizedClassDesc = propertyDesc
					.getParameterizedClassDesc();
			final Class<?> destElementType = parameterizedClassDesc
					.getArguments()[0].getRawClass();
			for (int i = 0; i < values.length; i++) {
				final Object value = values[i];
				try {
					final Object convertedValue = convertToScalar(
							converterProvider, value, destElementType);
					collection.add(convertedValue);
				} catch (final ConversionException e) {
					collection.add(null);
					final FieldInfo fieldInfo = new FieldInfo(parameterName, i);
					final MessageInfo messageInfo = e.getMessageInfo();
					errors.add(messageInfo.builder()
							.fieldNameKey(parameterName).toString(), fieldInfo);
				}
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
	 * @throws ConversionException
	 *             型変換に失敗した場合
	 */
	private Object convertToScalar(final ConverterProvider converterProvider,
			final Object value, final Class<?> destClass)
			throws ConversionException {
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
