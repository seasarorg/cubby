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
package org.seasar.cubby.util;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link Class} 用のユーティリティクラスです。
 * 
 * @author baba
 * @since 2.0.0
 */
public class ClassUtils {

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
	protected ClassUtils() {
	}

	/**
	 * {@link Class}を返します。
	 * 
	 * @param className
	 * @return {@link Class}
	 * @throws ClassNotFoundException
	 * @see Class#forName(String)
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> forName(final String className) {
		final ClassLoader loader = Thread.currentThread()
				.getContextClassLoader();
		try {
			return Class.class.cast(Class.forName(className, true, loader));
		} catch (final ClassNotFoundException ex) {
			throw new IllegalStateException(className, ex);
		}
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

	/**
	 * クラス名の要素を結合します。
	 * 
	 * @param s1
	 * @param s2
	 * @return 結合された名前
	 */
	public static String concatName(final String s1, final String s2) {
		if (StringUtils.isEmpty(s1) && StringUtils.isEmpty(s2)) {
			return null;
		}
		if (!StringUtils.isEmpty(s1) && StringUtils.isEmpty(s2)) {
			return s1;
		}
		if (StringUtils.isEmpty(s1) && !StringUtils.isEmpty(s2)) {
			return s2;
		}
		return s1 + '.' + s2;
	}
}
