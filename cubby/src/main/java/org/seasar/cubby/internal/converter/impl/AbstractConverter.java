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
package org.seasar.cubby.internal.converter.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.internal.converter.Converter;

/**
 * {@link Converter} の抽象クラスです。
 * 
 * @author baba
 * @since 1.1.0
 */
public abstract class AbstractConverter implements Converter {

	/** プリミティブ型の配列クラスとラッパー型の配列クラスのマッピング */
	protected static Map<Class<?>, Class<?>> PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY = new HashMap<Class<?>, Class<?>>();
	static {
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(boolean[].class, Boolean[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(char[].class, Character[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(byte[].class, Byte[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(short[].class, Short[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(int[].class, Integer[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(long[].class, Long[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(float[].class, Float[].class);
		PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.put(double[].class, Double[].class);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean canConvert(final Class<?> parameterType,
			final Class<?> objectType) {
		if (getObjectType().isAssignableFrom(objectType)) {
			return true;
		}
		if (PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY.containsKey(objectType)) {
			final Class<?> wrapperArray = PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY
					.get(objectType);
			return canConvert(parameterType, wrapperArray);
		}
		return false;
	}

}
