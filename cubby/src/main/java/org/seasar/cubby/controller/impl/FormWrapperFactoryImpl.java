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
import java.util.Collection;

import org.seasar.cubby.controller.FormWrapper;
import org.seasar.cubby.controller.FormWrapperFactory;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.converter.ConverterFactory;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * フォームオブジェクトのラッパーファクトリの実装です。
 * 
 * @author baba
 * @since 1.1.0
 */
public class FormWrapperFactoryImpl implements FormWrapperFactory {

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
		public String[] getValues(final String name) {
			if (this.form == null) {
				return null;
			}
			final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(this.form
					.getClass());
			if (!beanDesc.hasPropertyDesc(name)) {
				return null;
			}
			final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(name);
			final Object value = propertyDesc.getValue(this.form);
			if (value == null) {
				return null;
			} else if (value instanceof String[]) {
				return (String[]) value;
			} else {
				if (value.getClass().isArray()) {
					final int length = Array.getLength(value);
					final String[] array = (String[]) Array.newInstance(
							String.class, length);
					for (int i = 0; i < length; i++) {
						final Object element = Array.get(value, i);
						final String converted = convert(element);
						Array.set(array, i, converted);
					}
					return array;
				} else if (value instanceof Collection) {
					final Collection<?> collection = (Collection<?>) value;
					final String[] array = (String[]) Array.newInstance(
							String.class, collection.size());
					int i = 0;
					for (final Object element : collection) {
						final String converted = convert(element);
						Array.set(array, i++, converted);
					}
					return array;
				} else {
					final String[] array = (String[]) Array.newInstance(
							String.class, 1);
					final String converted = convert(value);
					Array.set(array, 0, converted);
					return array;
				}
			}
		}

		/**
		 * 指定されたオブジェクトを文字列に変換します。
		 * 
		 * @param value
		 *            値
		 * @return <code>value</code>を変換した文字列
		 */
		private String convert(final Object value) {
			final Converter converter = converterFactory.getConverter(
					null, value.getClass());
			if (converter == null) {
				return value.toString();
			} else {
				return converter.convertToString(value);
			}
		}

	}
}
