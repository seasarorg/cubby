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
package org.seasar.cubby.spi.impl;

import static org.seasar.cubby.internal.util.LogMessages.format;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.spi.ConverterProvider;

/**
 * コンバータプロバイダのスケルトン実装です。
 * 
 * @author baba
 */
public abstract class AbstractConverterProvider implements ConverterProvider {

	/**
	 * コンバータの一覧を返します。
	 * 
	 * @return コンバータの一覧
	 */
	protected abstract Collection<Converter> getConverters();

	/**
	 * {@inheritDoc}
	 */
	public Converter getConverter(final Class<? extends Converter> converterType) {
		for (final Converter converter : getConverters()) {
			if (converterType.isAssignableFrom(converter.getClass())) {
				return converter;
			}
		}
		throw new IllegalArgumentException(format("ECUB0110", converterType));
	}

	/**
	 * {@inheritDoc}
	 */
	public Converter getConverter(final Class<?> parameterType,
			final Class<?> objectType) {
		final Class<?> destType = ConversionUtils
				.getWrapperClassIfPrimitive(objectType);
		final Converter converter = getDistanceTable(parameterType, destType);
		return converter;
	}

	private Converter getDistanceTable(final Class<?> parameterType,
			final Class<?> objectType) {
		final Map<Integer, Converter> distanceTable = new TreeMap<Integer, Converter>();
		for (final Converter converter : getConverters()) {
			if (!converter.canConvert(parameterType, objectType)) {
				continue;
			}
			final int distance = getDistance(converter.getObjectType(),
					objectType);
			distanceTable.put(distance, converter);
		}
		if (distanceTable.isEmpty()) {
			return null;
		}
		return distanceTable.values().iterator().next();
	}

	private int getDistance(final Class<?> assigner, final Class<?> assignee) {
		return getDistance(assigner, assignee, 0);
	}

	private int getDistance(final Class<?> assigner, final Class<?> assignee,
			final int distance) {
		if (assignee.equals(assigner)) {
			return distance;
		}
		if (Enum.class.equals(assigner) && assignee.isEnum()) {
			return distance + 5;
		}
		if (isImplements(assigner, assignee)) {
			return distance + 5;
		}

		final Class<?> superClass = assigner.getSuperclass();
		if (superClass == null) {
			return distance + 10;
		}
		return getDistance(superClass, assignee, distance + 10);
	}

	private boolean isImplements(final Class<?> assigner,
			final Class<?> assignee) {
		return !assigner.isInterface() && assignee.isInterface()
				&& assignee.isAssignableFrom(assigner);
	}

}
