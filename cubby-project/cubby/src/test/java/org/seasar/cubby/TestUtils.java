/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(TestUtils.class);

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

	public static void bind(Object client, Object injectee) {
		Class<?> clientType = client.getClass();
		bind(client, clientType, injectee);
	}

	private static void bind(Object client, Class<?> clientType, Object injectee) {
		for (Field field : clientType.getDeclaredFields()) {
			if (field.getType().isAssignableFrom(injectee.getClass())) {
				field.setAccessible(true);
				try {
					field.set(client, injectee);
					logger.debug("Bind [" + field + "] to " + injectee);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		Class<?> superType = clientType.getSuperclass();
		if (!superType.equals(Object.class)) {
			bind(client, superType, injectee);
		}
	}

}
