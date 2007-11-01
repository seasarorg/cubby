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

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.extension.dxo.annotation.AnnotationReader;
import org.seasar.extension.dxo.command.impl.MapToBeanDxoCommand;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.ConverterFactory;
import org.seasar.extension.dxo.converter.impl.ConversionContextImpl;

public class ObjectArrayMapToBeanDxoCommand extends MapToBeanDxoCommand {

	@SuppressWarnings("unchecked")
	public ObjectArrayMapToBeanDxoCommand(Class dxoClass, Method method,
			ConverterFactory converterFactory,
			AnnotationReader annotationReader, Class destClass) {
		super(dxoClass, method, converterFactory, annotationReader, destClass);
	}

	@Override
	protected ConversionContext createContext(final Object source) {
		final ConversionContext context = new ConversionContextImpl(dxoClass,
				method, converterFactory, annotationReader, source);

		final Map<String, Object[]> map = castToObjectArrayMap(source);
		for (final Entry<String, Object[]> entry : map.entrySet()) {
			final String name = entry.getKey();
			if (!context.hasEvalueatedValue(name)) {
				final Object[] value = entry.getValue();
				if (value.length == 1) {
					context.addEvaluatedValue(name, value[0]);
				} else if (value.length == 0) {
					context.addEvaluatedValue(name, null);
				} else {
					context.addEvaluatedValue(name, value);
				}
			}
		}
		return context;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object[]> castToObjectArrayMap(final Object source) {
		return (Map<String, Object[]>) source;
	}

}
