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

import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.Converter;
import org.seasar.extension.dxo.converter.ConverterFactory;

/**
 * 
 * @author baba
 *
 */
public class RequestParameterConverterFactoryWrapper implements
		ConverterFactory {

	private ConverterFactory converterFactory;

	public void setConverterFactory(final ConverterFactory converterFactory) {
		this.converterFactory = converterFactory;
	}

	@SuppressWarnings("unchecked")
	public Converter getConverter(final Class sourceClass, final Class destClass) {
		final Converter converter = converterFactory.getConverter(sourceClass,
				destClass);
		return new DisregardExceptionConverterWrapper(converter);
	}

	static class DisregardExceptionConverterWrapper implements Converter {

		private final Converter converter;

		public DisregardExceptionConverterWrapper(final Converter converter) {
			this.converter = converter;
		}

		@SuppressWarnings("unchecked")
		public Object convert(final Object source, final Class destClass,
				final ConversionContext context) {
			try {
				return converter.convert(source, destClass, context);
			} catch (final Exception e) {
				return null;
			}
		}

		public void convert(final Object source, final Object dest,
				final ConversionContext context) {
			converter.convert(source, dest, context);
		}

		@SuppressWarnings("unchecked")
		public Class getDestClass() {
			return converter.getDestClass();
		}

		@SuppressWarnings("unchecked")
		public Class[] getSourceClasses() {
			return converter.getSourceClasses();
		}

	}

}
