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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.Converter;
import org.seasar.extension.dxo.converter.impl.ConverterFactoryImpl;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.DisposableUtil;
import org.seasar.framework.util.ResourceUtil;

/**
 * 
 * @author baba
 * 
 */
public class RequestParameterConverterFactoryImpl extends ConverterFactoryImpl {

	private static final Logger logger = Logger
			.getLogger(RequestParameterConverterFactoryImpl.class);

	private boolean initialized;

	private final List<String> converterContainerPaths = new ArrayList<String>();

	private final List<S2Container> containers = new ArrayList<S2Container>();

	private boolean includeApplicationConverters = true;

	public void addConverterContainerPath(final String converterContainerPath) {
		this.converterContainerPaths.add(converterContainerPath);
	}

	public void setIncludeApplicationConverters(
			final boolean includeApplicationConverters) {
		this.includeApplicationConverters = includeApplicationConverters;
	}

	@Override
	public void initialize() {
		if (initialized) {
			return;
		}

		if (includeApplicationConverters) {
			super.initialize();
		}

		final Map<Class<? extends Converter>, Converter> converterMap = new LinkedHashMap<Class<? extends Converter>, Converter>();
		for (final String path : this.converterContainerPaths) {
			if (ResourceUtil.isExist(path)) {
				final S2Container container = S2ContainerFactory.create(path);
				containers.add(container);
				for (final ComponentDef componentDef : container
						.findAllComponentDefs(Converter.class)) {
					final Converter converter = (Converter) componentDef
							.getComponent();
					converterMap.put(converter.getClass(), converter);
					if (logger.isDebugEnabled()) {
						logger.log("DCUB0010", new Object[] { converter });
					}
				}
			}
		}

		final List<Converter> converters = new ArrayList<Converter>(
				converterMap.values());
		for (final Converter converter : this.converters) {
			if (!converterMap.containsKey(converter.getClass())) {
				converters.add(converter);
				if (logger.isDebugEnabled()) {
					logger.log("DCUB0010", new Object[] { converter });
				}
			}
		}
		this.converters = converters.toArray(new Converter[0]);

		DisposableUtil.add(this);
		initialized = true;
	}

	@Override
	public void dispose() {
		for (final S2Container container : this.containers) {
			container.destroy();
		}
		containers.clear();
		super.dispose();
		initialized = false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Converter getConverter(final Class sourceClass, final Class destClass) {
		initialize();
		final Converter converter = super.getConverter(sourceClass, destClass);
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
