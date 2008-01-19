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

import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.controller.ThreadContext;
import org.seasar.extension.dxo.annotation.AnnotationReader;
import org.seasar.extension.dxo.annotation.AnnotationReaderFactory;
import org.seasar.framework.util.StringUtil;

/**
 * 
 * @author baba
 * 
 */
public class RequestParameterAnnotationReaderFactoryWrapper implements
		AnnotationReaderFactory {

	private AnnotationReaderFactory annotationReaderFactory;

	public void setAnnotationReaderFactory(
			final AnnotationReaderFactory annotationReaderFactory) {
		this.annotationReaderFactory = annotationReaderFactory;
	}

	public AnnotationReader getAnnotationReader() {
		final AnnotationReader annotationReader = annotationReaderFactory
				.getAnnotationReader();
		final CubbyConfiguration configuration = ThreadContext
				.getConfiguration();
		return new CubbyAnnotationReaderWrapper(annotationReader, configuration
				.getFormatPattern());
	}

	static class CubbyAnnotationReaderWrapper implements AnnotationReader {

		private final AnnotationReader annotationReader;

		private final FormatPattern formatPattern;

		public CubbyAnnotationReaderWrapper(
				final AnnotationReader annotationReader,
				final FormatPattern formatPattern) {
			this.annotationReader = annotationReader;
			this.formatPattern = formatPattern;
		}

		@SuppressWarnings("unchecked")
		public String getDatePattern(final Class dxoClass, final Method method) {
			String datePattern = annotationReader.getDatePattern(dxoClass,
					method);
			if (StringUtil.isEmpty(datePattern) && formatPattern != null) {
				datePattern = formatPattern.getDatePattern();
			}
			return datePattern;
		}

		@SuppressWarnings("unchecked")
		public String getTimePattern(final Class dxoClass, final Method method) {
			String timePattern = annotationReader.getTimePattern(dxoClass,
					method);
			if (StringUtil.isEmpty(timePattern) && formatPattern != null) {
				timePattern = formatPattern.getTimePattern();
			}
			return timePattern;
		}

		@SuppressWarnings("unchecked")
		public String getTimestampPattern(final Class dxoClass,
				final Method method) {
			String timestampPattern = annotationReader.getTimestampPattern(
					dxoClass, method);
			if (StringUtil.isEmpty(timestampPattern) && formatPattern != null) {
				timestampPattern = formatPattern.getTimestampPattern();
			}
			return timestampPattern;
		}

		@SuppressWarnings("unchecked")
		public String getConversionRule(final Class dxoClass,
				final Method method) {
			return annotationReader.getConversionRule(dxoClass, method);
		}

		@SuppressWarnings("unchecked")
		public boolean isExcludeNull(final Class dxoClass, final Method method) {
			return annotationReader.isExcludeNull(dxoClass, method);
		}

		@SuppressWarnings("unchecked")
		public Map getConverters(final Class destClass) {
			return annotationReader.getConverters(destClass);
		}

		@SuppressWarnings("unchecked")
		public String getSourcePrefix(final Class dxoClass, final Method method) {
			return annotationReader.getSourcePrefix(dxoClass, method);
		}

		@SuppressWarnings("unchecked")
		public String getDestPrefix(final Class dxoClass, final Method method) {
			return annotationReader.getDestPrefix(dxoClass, method);
		}

	}

}
