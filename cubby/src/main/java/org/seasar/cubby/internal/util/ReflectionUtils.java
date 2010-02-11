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

package org.seasar.cubby.internal.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * リフレクションのためのユーティリティクラスです。
 * 
 * @author baba
 */
public class ReflectionUtils {

	/**
	 * 指定されたクラスとそのすべてのスーパークラスに定義されたフィールドを取得します。
	 * 
	 * @param clazz
	 *            フィールドを検索するクラス
	 * @return 検索したフィールドのコレクション
	 */
	public static Collection<Field> findAllDeclaredField(final Class<?> clazz) {
		final Collection<Field> fields = new ArrayList<Field>(50);
		appendFields(clazz, fields);
		return Collections.unmodifiableCollection(fields);
	}

	/**
	 * 指定されたクラスとそのすべてのスーパークラスに定義されたフィールドを指定されたコレクションに追加します。
	 * 
	 * @param clazz
	 *            フィールドを検索するクラス
	 * @param fields
	 *            フィールドを追加するコレクション
	 */
	private static void appendFields(final Class<?> clazz,
			final Collection<Field> fields) {
		for (final Field field : clazz.getDeclaredFields()) {
			fields.add(field);
		}
		final Class<?> superClass = clazz.getSuperclass();
		if (!Object.class.equals(superClass)) {
			appendFields(superClass, fields);
		}

	}

}
