package org.seasar.cubby;

import java.lang.reflect.Field;

public class TestUtils {

	@SuppressWarnings("unchecked")
	public static <T> T getPrivateField(Object obj, String fieldName)
			throws NoSuchFieldException {

		Class<?> class1 = obj.getClass();
		final Field field = findField(class1, fieldName);
		field.setAccessible(true);

		try {
			return (T) field.get(obj);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private static Field findField(Class<?> clazz, String fieldName)
			throws NoSuchFieldException {
		for (; clazz != null; clazz = clazz.getSuperclass()) {
			for (Field afield : clazz.getDeclaredFields()) {
				if (afield.getName().equals(fieldName)) {
					return afield;
				}
			}
		}

		throw new NoSuchFieldException(fieldName);
	}

}
