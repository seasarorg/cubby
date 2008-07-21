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

import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.controller.FormWrapper;
import org.seasar.cubby.controller.FormWrapperFactory;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.Converter;
import org.seasar.extension.dxo.converter.ConverterFactory;
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
	public FormWrapper create(final Object form) {
		final ConversionContext context = new ConversionContextImpl(
				converterFactory, cubbyConfiguration);
		final FormWrapper formObject = new FormWrapperImpl(form, context);
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

		/** 変換中のコンテキスト。 */
		private ConversionContext context;

		/**
		 * インスタンス化します。
		 * 
		 * @param form
		 *            フォームオブジェクト
		 * @param context
		 *            変換中のコンテキスト
		 */
		private FormWrapperImpl(Object form, final ConversionContext context) {
			this.form = form;
			this.context = context;
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
			if (value == null || value instanceof String[]) {
				return (String[]) value;
			} else {
				final ConverterFactory converterFactory = context
						.getConverterFactory();
				if (value.getClass().isArray() || value instanceof Collection) {
					final Converter converter = converterFactory.getConverter(
							value.getClass(), String[].class);
					final String[] convertedValue = (String[]) converter
							.convert(value, String[].class, context);
					return convertedValue;
				} else {
					final Converter converter = converterFactory.getConverter(
							value.getClass(), String.class);
					final String[] array = (String[]) Array.newInstance(
							String.class, 1);
					Array.set(array, 0, converter.convert(value, String.class,
							context));
					return array;
				}
			}
		}

	}
}
