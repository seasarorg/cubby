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
import java.util.Collection;

import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.converter.impl.ConversionHelperImpl;
import org.seasar.cubby.internal.controller.FormWrapper;
import org.seasar.cubby.internal.controller.FormWrapperFactory;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.BeanDescFactory;
import org.seasar.cubby.spi.beans.PropertyDesc;

/**
 * フォームオブジェクトのラッパーファクトリの実装です。
 * 
 * @author baba
 * @since 1.1.0
 */
public class FormWrapperFactoryImpl implements FormWrapperFactory {

	/** 変換のヘルパクラス。 */
	private final ConversionHelper conversionHelper = new ConversionHelperImpl();

	/**
	 * {@inheritDoc}
	 */
	public FormWrapper create(final Object form) {
		final FormWrapper formObject = new FormWrapperImpl(form);
		return formObject;
	}

	/**
	 * フォームオブジェクトのラッパーの実装です。
	 * 
	 * @author baba
	 * @since 1.1.0
	 */
	private class FormWrapperImpl implements FormWrapper {

		/** フォームオブジェクト */
		private final Object form;

		/**
		 * インスタンス化します。
		 * 
		 * @param form
		 *            フォームオブジェクト
		 * @param context
		 *            変換中のコンテキスト
		 */
		private FormWrapperImpl(final Object form) {
			this.form = form;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean hasValues(final String name) {
			if (this.form == null) {
				return false;
			}
			final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(this.form
					.getClass());
			final PropertyDesc propertyDesc = findPropertyDesc(beanDesc, name);
			return propertyDesc != null;
		}

		/**
		 * {@inheritDoc}
		 */
		public String[] getValues(final String name) {
			if (this.form == null) {
				return null;
			}
			final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(this.form
					.getClass());
			final PropertyDesc propertyDesc = findPropertyDesc(beanDesc, name);
			if (propertyDesc == null) {
				return null;
			}
			final Object value = propertyDesc.getValue(this.form);
			if (value == null) {
				return null;
			} else if (value instanceof String[]) {
				return (String[]) value;
			} else {
				final Class<? extends Converter> converterType;
				if (propertyDesc.isAnnotationPresent(RequestParameter.class)) {
					final RequestParameter requestParameter = propertyDesc
							.getAnnotation(RequestParameter.class);
					if (Converter.class.equals(requestParameter.converter())) {
						converterType = null;
					} else {
						converterType = requestParameter.converter();
					}
				} else {
					converterType = null;
				}
				if (value.getClass().isArray()) {
					final int length = Array.getLength(value);
					final String[] array = (String[]) Array.newInstance(
							String.class, length);
					for (int i = 0; i < length; i++) {
						final Object element = Array.get(value, i);
						final String converted = convert(element, converterType);
						Array.set(array, i, converted);
					}
					return array;
				} else if (value instanceof Collection) {
					final Collection<?> collection = (Collection<?>) value;
					final String[] array = (String[]) Array.newInstance(
							String.class, collection.size());
					int i = 0;
					for (final Object element : collection) {
						final String converted = convert(element, converterType);
						Array.set(array, i++, converted);
					}
					return array;
				} else {
					final String[] array = (String[]) Array.newInstance(
							String.class, 1);
					final String converted = convert(value, converterType);
					Array.set(array, 0, converted);
					return array;
				}
			}
		}

		/**
		 * 指定された名前に対応するプロパティを検索します。
		 * 
		 * @param beanDesc
		 *            Java Beans の定義
		 * @param name
		 *            名前
		 * @return プロパティの定義
		 */
		private PropertyDesc findPropertyDesc(final BeanDesc beanDesc,
				final String name) {
			for (final PropertyDesc propertyDesc : beanDesc.getPropertyDescs()) {
				if (propertyDesc.isAnnotationPresent(RequestParameter.class)) {
					final RequestParameter requestParameter = propertyDesc
							.getAnnotation(RequestParameter.class);
					final String parameterName = requestParameter.name();
					if (parameterName == null || parameterName.length() == 0) {
						if (name.equals(propertyDesc.getPropertyName())) {
							return propertyDesc;
						}
					} else {
						if (name.equals(parameterName)) {
							return propertyDesc;
						}
					}
				} else {
					if (name.equals(propertyDesc.getPropertyName())) {
						return propertyDesc;
					}
				}
			}
			return null;
		}

		/**
		 * 指定されたオブジェクトを文字列に変換します。
		 * 
		 * @param value
		 *            値
		 * @param converterType
		 *            コンバータの型
		 * @return <code>value</code>を変換した文字列
		 */
		private String convert(final Object value,
				final Class<? extends Converter> converterType) {
			if (value == null) {
				return null;
			}
			final ConverterProvider converterProvider = ProviderFactory
					.get(ConverterProvider.class);
			final Converter converter;
			if (converterType == null) {
				converter = converterProvider.getConverter(null, value
						.getClass());
			} else {
				converter = converterProvider.getConverter(converterType);
			}
			if (converter == null) {
				return value.toString();
			} else {
				return converter.convertToString(value, conversionHelper);
			}
		}

	}
}
