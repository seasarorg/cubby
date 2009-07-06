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

/**
 * パラメタ化されたクラスを扱うためのインターフェースです。
 * 
 * @author baba
 */
public interface ParameterizedClassDesc {

	/**
	 * このインスタンスが表現するクラスがパラメタ化されていれば<code>true</code>を返します。
	 * 
	 * @return このインスタンスが表現するクラスがパラメタ化されていれば<code>true</code>
	 */
	boolean isParameterizedClass();

	/**
	 * 原型となるクラスを返します。
	 * 
	 * @return 原型となるクラス
	 * @see java.lang.reflect.ParameterizedType#getRawType()
	 */
	Class<?> getRawClass();

	/**
	 * 型引数を表す{@link ParameterizedClassDesc}の配列を返します。
	 * <p>
	 * このインスタンスが表現するクラスがパラメタ化されたクラスでない場合は、<code>null</code>を返します。
	 * </p>
	 * 
	 * @return 型引数を表す{@link ParameterizedClassDesc}の配列
	 * @see java.lang.reflect.ParameterizedType#getActualTypeArguments()
	 */
	ParameterizedClassDesc[] getArguments();

}
