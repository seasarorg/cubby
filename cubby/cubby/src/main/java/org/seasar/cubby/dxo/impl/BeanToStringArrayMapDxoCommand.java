/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.extension.dxo.annotation.AnnotationReader;
import org.seasar.extension.dxo.command.impl.BeanToMapDxoCommand;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.Converter;
import org.seasar.extension.dxo.converter.ConverterFactory;

/**
 * 
 * @author baba
 *
 */
class BeanToStringArrayMapDxoCommand extends BeanToMapDxoCommand {

	/**
	 * インスタンスを構築します。
	 * 
	 * @param dxoClass
	 *            Dxoのインターフェースまたはクラス
	 * @param method
	 *            Dxoのメソッド
	 * @param converterFactory
	 *            {@link Converter}のファクトリ
	 * @param annotationReader
	 *            {@link AnnotationReader}のファクトリ
	 * @param expression
	 *            変換ルールを表すOGNL式
	 */
	@SuppressWarnings("unchecked")
	public BeanToStringArrayMapDxoCommand(final Class dxoClass,
			final Method method, final ConverterFactory converterFactory,
			final AnnotationReader annotationReader, final String expression) {
		super(dxoClass, method, converterFactory, annotationReader, expression);
		valueType = String[].class;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Map convertValueType(final Map from,
			final ConversionContext context) {
		final Map<String, String[]> to = new LinkedHashMap<String, String[]>();
		for (final Entry<String, Object> entry : castToObjectMap(from)
				.entrySet()) {
			final String key = entry.getKey();
			final Object value = entry.getValue();
			if (value == null || value instanceof String[]) {
				to.put(key, (String[]) value);
			} else {
				if (value.getClass().isArray()) {
					final Converter converter = converterFactory.getConverter(
							value.getClass(), String[].class);
					final String[] convertedValue = (String[]) converter
							.convert(value, String[].class, context);
					to.put(key, convertedValue);
				} else {
					final Converter converter = converterFactory.getConverter(
							value.getClass(), String.class);
					String[] array = (String[]) Array.newInstance(
							String.class, 1);
					Array.set(array, 0, converter.convert(value,
							String.class, context));
					to.put(key, array);
				}
			}
		}
		return to;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> castToObjectMap(final Map from) {
		return ((Map<String, Object>) from);
	}

}
