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
package org.seasar.cubby.controller;

/**
 * {@link ClassDetector} によって検出されたクラスを処理するインターフェイスです。
 * <p>
 * このインターフェイスを実装したクラスがコンテナに登録されてるいと、クラスが検出された時に {
 * {@link #processClass(String, String)} を呼び出します。
 * </p>
 * 
 * @author baba
 * @since 1.1.0
 */
public interface DetectClassProcessor {

	/**
	 * クラスが検出された時に呼び出されるメソッドです。
	 * 
	 * @param packageName
	 *            パッケージ名
	 * @param shortClassName
	 *            クラス短縮名
	 */
	void processClass(String packageName, String shortClassName);

}
