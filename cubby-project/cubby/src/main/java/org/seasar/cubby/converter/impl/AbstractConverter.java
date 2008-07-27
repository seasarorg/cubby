package org.seasar.cubby.converter.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.converter.Converter;

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

	public boolean canConvert(Class<?> parameterType, Class<?> objectType) {
		if (!getObjectType().isAssignableFrom(objectType)) {
			final Class<?> wrapperArray = (Class<?>) PRIMITIVE_ARRAY_TO_WRAPPER_ARRAY
					.get(objectType);
			if (wrapperArray != null) {
				return canConvert(parameterType, wrapperArray);
			}
			return false;
		}
		return true;
	}

}
