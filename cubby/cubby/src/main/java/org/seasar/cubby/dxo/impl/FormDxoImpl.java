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
import java.util.Map;

import org.seasar.cubby.dxo.FormDxo;
import org.seasar.extension.dxo.annotation.AnnotationReader;
import org.seasar.extension.dxo.annotation.AnnotationReaderFactory;
import org.seasar.extension.dxo.command.DxoCommand;
import org.seasar.extension.dxo.converter.ConverterFactory;
import org.seasar.framework.container.annotation.tiger.InitMethod;
import org.seasar.framework.util.ClassUtil;

/**
 * 
 * @author baba
 *
 */
public class FormDxoImpl implements FormDxo {

	private ConverterFactory converterFactory;

	private AnnotationReaderFactory annotationReaderFactory;

	private DxoCommand objectArrayMapToBeanDxoCommand;

	private DxoCommand beanToStringArrayMapDxoCommand;

	public void setConverterFactory(final ConverterFactory converterFactory) {
		this.converterFactory = converterFactory;
	}

	public void setAnnotationReaderFactory(
			final AnnotationReaderFactory annotationReaderFactory) {
		this.annotationReaderFactory = annotationReaderFactory;
	}

	@InitMethod
	public void initialize() {
		final Class<?> targetClass = this.getClass();

		final Method objectArrayMapToBeanConvertMethod = ClassUtil.getMethod(
				targetClass, "convert", new Class<?>[] { Map.class,
						Object.class });
		this.objectArrayMapToBeanDxoCommand = createObjectArrayMapToBeanDxoCommand(
				targetClass, objectArrayMapToBeanConvertMethod);

		final Method beanToStringArrayMapConvertMethod = ClassUtil.getMethod(
				targetClass, "convert", new Class<?>[] { Object.class,
						Map.class });
		this.beanToStringArrayMapDxoCommand = createBeanToStringArrayMapDxoCommand(
				targetClass, beanToStringArrayMapConvertMethod);
	}

	public void convert(final Map<String, Object[]> src, final Object dest) {
		if (src != null) {
			objectArrayMapToBeanDxoCommand.execute(new Object[] { src, dest });
		}
	}

	public void convert(final Object src, final Map<String, String[]> dest) {
		if (src != null) {
			beanToStringArrayMapDxoCommand.execute(new Object[] { src, dest });
		}
	}

	@SuppressWarnings("unchecked")
	public DxoCommand createObjectArrayMapToBeanDxoCommand(
			final Class dxoClass, final Method method) {
		final AnnotationReader annotationReader = annotationReaderFactory
				.getAnnotationReader();
		return new ObjectArrayMapToBeanDxoCommand(dxoClass, method,
				converterFactory, annotationReader, Object.class);
	}

	@SuppressWarnings("unchecked")
	public DxoCommand createBeanToStringArrayMapDxoCommand(
			final Class dxoClass, final Method method) {
		final AnnotationReader annotationReader = annotationReaderFactory
				.getAnnotationReader();
		final String expression = annotationReader.getConversionRule(dxoClass,
				method);
		return new BeanToStringArrayMapDxoCommand(dxoClass, method,
				converterFactory, annotationReader, expression);
	}

}