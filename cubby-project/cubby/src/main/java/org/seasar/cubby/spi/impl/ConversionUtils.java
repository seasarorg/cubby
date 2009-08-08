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

import java.util.HashMap;
import java.util.Map;

/**
 * 型変換のためのユーティリティクラスです。
 * 
 * @author baba
 */
class ConversionUtils {

	private static Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_MAP = new HashMap<Class<?>, Class<?>>();

	static {
		PRIMITIVE_TO_WRAPPER_MAP.put(Character.TYPE, Character.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(Byte.TYPE, Byte.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(Short.TYPE, Short.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(Integer.TYPE, Integer.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(Long.TYPE, Long.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(Double.TYPE, Double.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(Float.TYPE, Float.class);
		PRIMITIVE_TO_WRAPPER_MAP.put(Boolean.TYPE, Boolean.class);
	}

	/**
     * 
     */
	protected ConversionUtils() {
	}

	/**
	 * プリミティブの場合はラッパークラス、そうでない場合はもとのクラスを返します。
	 * 
	 * @param clazz
	 * @return {@link Class}
	 */
	public static Class<?> getWrapperClassIfPrimitive(final Class<?> clazz) {
		if (PRIMITIVE_TO_WRAPPER_MAP.containsKey(clazz)) {
			return PRIMITIVE_TO_WRAPPER_MAP.get(clazz);
		} else {
			return clazz;
		}
	}

}
