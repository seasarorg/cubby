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

package org.seasar.cubby.spi.beans;

import java.lang.annotation.Annotation;

/**
 * オブジェクトの属性を扱うためのインターフェイスです。
 * 
 * @author baba
 */
public interface Attribute {

	/**
	 * 属性名を返します。
	 * 
	 * @return 属性名
	 */
	String getName();

	/**
	 * 属性の型を返します。
	 * 
	 * @return 属性の型
	 */
	Class<?> getType();

	/**
	 * 属性の値が取得可能かどうかを返します。
	 * 
	 * @return 属性の値が取得可能かどうか
	 */
	boolean isReadable();

	/**
	 * 属性の値が設定可能かどうかを返します。
	 * 
	 * @return 属性の値が設定可能かどうか
	 */
	boolean isWritable();

	/**
	 * 属性の値を返します。
	 * 
	 * @param target
	 *            値を取得するオブジェクト
	 * @return 属性の値
	 * @throws IllegalAttributeException
	 *             値の取得に失敗した場合。
	 */
	Object getValue(Object target);

	/**
	 * 属性に値を設定します。
	 * 
	 * @param target
	 *            値を設定するオブジェクト
	 * @param value
	 *            設定する値
	 * @throws IllegalAttributeException
	 *             値の設定に失敗した場合
	 */
	void setValue(Object target, Object value);

	/**
	 * この属性がパラメタ化された型の場合、その情報を返します。
	 * <p>
	 * この属性がパラメタ化された型でない場合は<code>null</code>を返します。
	 * </p>
	 * 
	 * @return この属性がパラメタ化された型の場合、その情報
	 */
	ParameterizedClassDesc getParameterizedClassDesc();

	/**
	 * 属性から指定されたアノテーションを取得します。
	 * 
	 * @param <T>
	 *            アノテーション
	 * @param annotationClass
	 *            取得するアノテーションの型
	 * @return アノテーション
	 */
	<T extends Annotation> T getAnnotation(Class<T> annotationClass);

	/**
	 * 属性が指定されたアノテーションで修飾されているかを示します。
	 * 
	 * @param annotationClass
	 *            アノテーションの型
	 * @return 属性が指定されたアノテーションで修飾されている場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	boolean isAnnotationPresent(Class<? extends Annotation> annotationClass);

}
