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

package org.seasar.cubby.converter;

/**
 * 変換元クラスのインスタンスを変換先クラスのインスタンスに変換するコンバータを表します。
 * 
 * @author baba
 */
public interface Converter {

	/**
	 * このコンバータがサポートしているクラスを返します。
	 * 
	 * @return このコンバータがサポートしているクラス
	 */
	Class<?> getObjectType();

	/**
	 * このコンバータが指定された要求パラメータの型を指定された値の型に変換できるかを示します。
	 * <p>
	 * <code>parameterType</code> に <code>null</code> が指定された場合は
	 * <code>false</code> を返します。
	 * </p>
	 * 
	 * @param parameterType
	 *            要求パラメータの型
	 * @param objectType
	 *            値の型
	 * @return このコンバータが指定された要求パラメータの型を指定された値の型に変換できる場合は <code>true</code>
	 *         、そうでない場合は <code>false</code>
	 */
	boolean canConvert(Class<?> parameterType, Class<?> objectType);

	/**
	 * <code>value</code>をこのコンバータがサポートしているクラスのインスタンスに変換します。
	 * 
	 * @param value
	 *            変換元のオブジェクト
	 * @param objectType
	 *            値の型
	 * @param helper
	 *            変換のヘルパクラス
	 * @return <code>value</code>を変換したオブジェクト
	 * @throws ConversionException
	 *             型変換に失敗した場合
	 */
	Object convertToObject(Object value, Class<?> objectType,
			ConversionHelper helper) throws ConversionException;

	/**
	 * このコンバータがサポートしているクラスのインスタンスである<code>value</code>を文字列に変換します。
	 * 
	 * @param value
	 *            変換元のオブジェクト
	 * @param helper
	 *            変換のヘルパクラス
	 * 
	 * @return <code>value</code>を変換した文字列
	 */
	String convertToString(Object value, ConversionHelper helper);

}
