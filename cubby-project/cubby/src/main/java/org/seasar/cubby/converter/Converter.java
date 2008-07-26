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
package org.seasar.cubby.converter;

/**
 * 変換元クラスのインスタンスを変換先クラスのインスタンスに変換するコンバータを表します。
 * 
 * @author baba
 * @since 1.1.0
 */
public interface Converter {

	/**
	 * このコンバータがサポートしているクラスを返します。
	 * 
	 * @return このコンバータがサポートしているクラス
	 */
	Class<?> getConversionClass();

	/**
	 * <code>value</code>をこのコンバータがサポートしているクラスのインスタンスに変換します。
	 * 
	 * @param value
	 *            変換元のオブジェクト
	 * 
	 * @return <code>value</code>を変換したオブジェクト
	 */
	Object convertToObject(Object value);

	/**
	 * このコンバータがサポートしているクラスのインスタンスである<code>value</code>を文字列に変換します。
	 * 
	 * @param value
	 *            変換元のオブジェクト
	 * 
	 * @return <code>value</code>を変換した文字列
	 */
	String convertToString(Object value);

}
