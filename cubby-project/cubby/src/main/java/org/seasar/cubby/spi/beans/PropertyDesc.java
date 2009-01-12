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
package org.seasar.cubby.spi.beans;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * プロパティを扱うためのインターフェースです。
 * 
 * @author baba
 * @since 2.0.0
 */
public interface PropertyDesc {

	/**
	 * プロパティ名を返します。
	 * 
	 * @return プロパティ名
	 */
	String getPropertyName();

	/**
	 * プロパティの型を返します。
	 * 
	 * @return プロパティの型
	 */
	Class<?> getPropertyType();

	/**
	 * getterメソッドを返します。
	 * 
	 * @return getterメソッド
	 */
	Method getReadMethod();

	/**
	 * getterメソッドを持っているかどうか返します。
	 * 
	 * @return getterメソッドを持っているかどうか
	 */
	boolean hasReadMethod();

	/**
	 * setterメソッドを返します。
	 * 
	 * @return setterメソッド
	 */
	Method getWriteMethod();

	/**
	 * setterメソッドを持っているかどうか返します。
	 * 
	 * @return setterメソッドを持っているかどうか
	 */
	boolean hasWriteMethod();

	/**
	 * プロパティの値が取得できるかどうかを返します。
	 * 
	 * @return プロパティの値が取得できるかどうか
	 */
	boolean isReadable();

	/**
	 * プロパティの値が設定できるかどうかを返します。
	 * 
	 * @return プロパティの値が設定できるかどうか
	 */
	boolean isWritable();

	/**
	 * プロパティの値を返します。
	 * 
	 * @param target
	 * @return プロパティの値
	 * @throws IllegalPropertyException
	 *             値の取得に失敗した場合。
	 */
	Object getValue(Object target) throws IllegalPropertyException;

	/**
	 * プロパティに値を設定します。
	 * 
	 * @param target
	 * @param value
	 * @throws IllegalPropertyException
	 *             値の設定に失敗した場合。
	 */
	void setValue(Object target, Object value) throws IllegalPropertyException;

	/**
	 * このプロパティがパラメタ化された型の場合に<code>true</code>を返します。
	 * 
	 * @return このプロパティがパラメタ化された型の場合に<code>true</code>
	 */
	boolean isParameterized();

	/**
	 * このプロパティがパラメタ化された型の場合、その情報を返します。
	 * <p>
	 * このプロパティがパラメタ化された型でない場合は<code>null</code>を返します。
	 * </p>
	 * 
	 * @return このプロパティがパラメタ化された型の場合、その情報
	 */
	ParameterizedClassDesc getParameterizedClassDesc();

	/**
	 * プロパティから指定されたアノテーションを取得します。
	 * <p>
	 * 以下の順序でプロパティのメソッドの定義を検索し、最初に見つかったアノテーションを返します。
	 * <ol>
	 * <li>プロパティ値の読み込みに使用するメソッド {@link #getReadMethod()}</li>
	 * <li>プロパティ値の書き込みに使用するメソッド {@link #getWriteMethod()}</li>
	 * </ol>
	 * </p>
	 * <p>
	 * また、クラスが {@link Proxy} になどよって動的に生成されている場合などは、メソッドからアノテーションを取得することができません。
	 * (アノテーションが {@link Inherited} で修飾されている場合でも取得できません。)
	 * そのため、読み込み/書き込みメソッドの定義を以下のように検索し、アノテーションを取得します。
	 * <ul>
	 * <li>読み込み/書き込みメソッドが定義されたクラス ({@link Method#getDeclaringClass()})
	 * を検索対象クラスの起点とします。</li>
	 * <li>検索対象クラスと、そのインターフェイスから読み込み/書き込みメソッドの定義を検索します。
	 * <li>アノテーションが取得できなかった場合は、検索対象クラスをそのスーパークラスとし、再度検索を行います。</li>
	 * </ul>
	 * </p>
	 * 
	 * @param <T>
	 *            アノテーション
	 * @param annotationClass
	 *            取得するアノテーションの型
	 * @return アノテーション
	 */
	public <T extends Annotation> T getAnnotation(Class<T> annotationClass);

	/**
	 * プロパティが指定されたアノテーションで修飾されているかを示します。
	 * 
	 * @param annotationClass
	 *            アノテーションの型
	 * @return プロパティが指定されたアノテーションで修飾されている場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	public boolean isAnnotationPresent(
			Class<? extends Annotation> annotationClass);

}
