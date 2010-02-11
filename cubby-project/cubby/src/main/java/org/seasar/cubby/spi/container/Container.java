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

package org.seasar.cubby.spi.container;

/**
 * オブジェクトを生成、管理するコンテナのインターフェイスです。
 * 
 * @author baba
 */
public interface Container {

	/**
	 * 指定された型に代入可能なオブジェクトを取得します。
	 * 
	 * @param <T>
	 *            取得するオブジェクトの型
	 * @param type
	 *            取得するオブジェクトの型
	 * @return 指定された型に代入可能なオブジェクト
	 * @throws LookupException
	 *             指定された型に代入可能なオブジェクトの取得に失敗した場合
	 */
	<T> T lookup(Class<T> type) throws LookupException;

}
