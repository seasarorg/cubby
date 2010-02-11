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
import java.util.Set;

/**
 * Java Beans を扱うためのインターフェースです。
 * 
 * @author baba
 */
public interface BeanDesc {

	/**
	 * 指定された名前のプロパティへアクセスする属性があるかどうかを示します。
	 * 
	 * @param name
	 *            属性名
	 * @return 指定された名前のプロパティへアクセスする属性がある場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	boolean hasPropertyAttribute(String name);

	/**
	 * 指定された名前のプロパティへアクセスする属性を返します。
	 * 
	 * @param name
	 *            属性名
	 * @return {@link Attribute}
	 * @throws AttributeNotFoundException
	 *             {@link Attribute} が見つからない場合
	 */
	Attribute getPropertyAttribute(String name)
			throws AttributeNotFoundException;

	/**
	 * 指定された名前のフィールドへアクセスする属性があるかどうかを示します。
	 * 
	 * @param name
	 *            属性名
	 * @return 指定された名前のフィールドへアクセスする属性がある場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	boolean hasFieldAttribute(String name);

	/**
	 * 指定された名前のフィールドへアクセスする属性を返します。
	 * 
	 * @param name
	 *            属性名
	 * @return {@link Attribute} のコレクション
	 * @throws AttributeNotFoundException
	 *             {@link Attribute} が見つからない場合
	 */
	Attribute getFieldAttribute(String name);

	/**
	 * すべてのプロパティへアクセスする検索します。
	 * 
	 * @return {@link Attribute} のコレクション
	 */
	Set<Attribute> findtPropertyAttributes();

	/**
	 * すべてのフィールドへアクセスする属性を検索します。
	 * 
	 * @return {@link Attribute} のコレクション
	 */
	Set<Attribute> findFieldAttributes();

	/**
	 * すべてのプロパティとフィールドへアクセスする属性を返します。
	 * 
	 * @return {@link Attribute} のコレクション
	 */
	Set<Attribute> findAllAttributes();

	/**
	 * 指定されたアノテーションで修飾された、プロパティまたはフィールドへアクセスする属性を返します。
	 * 
	 * @param annotationClass
	 *            アノテーションの型
	 * @return {@link Attribute} のコレクション
	 */
	Set<Attribute> findAttributesAnnotatedWith(
			Class<? extends Annotation> annotationClass);

}
