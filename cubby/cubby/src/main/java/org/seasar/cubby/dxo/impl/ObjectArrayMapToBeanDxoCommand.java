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
package org.seasar.cubby.dxo.impl;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.extension.dxo.annotation.AnnotationReader;
import org.seasar.extension.dxo.command.impl.MapToBeanDxoCommand;
import org.seasar.extension.dxo.converter.ConverterFactory;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.StringUtil;

/**
 * 
 * @author baba
 * 
 */
class ObjectArrayMapToBeanDxoCommand extends MapToBeanDxoCommand {

	@SuppressWarnings("unchecked")
	public ObjectArrayMapToBeanDxoCommand(final Class dxoClass,
			final Method method, final ConverterFactory converterFactory,
			final AnnotationReader annotationReader, final Class destClass) {
		super(dxoClass, method, converterFactory, annotationReader, destClass);
	}

	private Map<String, Object> normalize(
			final Map<String, Object[]> parameters, final Class<?> formType) {
		final Map<String, Object> normalized = new HashMap<String, Object>();
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(formType);
		for (final Entry<String, Object[]> entry : parameters.entrySet()) {
			final String name = entry.getKey();
			if (beanDesc.hasPropertyDesc(name)) {
				final PropertyDesc propertyDesc = beanDesc
						.getPropertyDesc(name);
				if (propertyDesc.isReadable() && propertyDesc.isWritable()) {
					final Object[] values = entry.getValue();
					final Class<?> propertyType = propertyDesc
							.getPropertyType();
					if (propertyType.isArray()) {
						normalized.put(name, values);
					} else if (Collection.class.isAssignableFrom(propertyType)) {
						normalized.put(name, values);
					} else if (String.class.isAssignableFrom(propertyType)) {
						final String value = (String) values[0];
						if (!StringUtil.isEmpty(value)) {
							normalized.put(name, value);
						} else {
							normalized.put(name, null);
						}
					} else {
						normalized.put(name, values[0]);
					}
				}
			}
		}
		return normalized;
	}

	@Override
	protected void convertScalar(final Object source, final Object dest) {
		final Map<String, Object> normalized = normalize(
				castToObjectArrayMap(source), dest.getClass());
		super.convertScalar(normalized, dest);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object[]> castToObjectArrayMap(final Object source) {
		return (Map<String, Object[]>) source;
	}

}
