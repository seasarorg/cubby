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
