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

package org.seasar.cubby.plugin;

/**
 * 実行情報です。
 * 
 * @author baba
 * 
 * @param <T>
 *            戻り値の型
 */
public interface Invocation<T> {

	/**
	 * 実行します。
	 * 
	 * @return 戻り値
	 * @throws Exception
	 *             実行時に例外が発生した場合
	 */
	T proceed() throws Exception;

}
