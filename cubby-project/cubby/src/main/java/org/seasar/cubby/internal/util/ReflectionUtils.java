package org.seasar.cubby.internal.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

	public static Field[] findAllDeclaredField(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>(50);
		appendFields(clazz, fields);
		return fields.toArray(new Field[fields.size()]);
	}

	private static void appendFields(Class<?> clazz, List<Field> fields) {
		for (Field field : clazz.getDeclaredFields()) {
			fields.add(field);
		}
		Class<?> superClass = clazz.getSuperclass();
		if (Object.class.equals(superClass)) {
			appendFields(superClass, fields);
		}

	}

}
