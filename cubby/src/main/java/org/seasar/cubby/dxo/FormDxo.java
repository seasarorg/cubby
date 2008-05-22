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
package org.seasar.cubby.dxo;

import java.util.Map;

/**
 * フォームオブジェクトと {@link Map} とを相互に変換します。
 * 
 * @author baba
 * @since 1.0.0
 */
public interface FormDxo {

	/**
	 * リクエストパラメータを格納した {@link Map} からアクションのフォームオブジェクトへ値をマッピングします。
	 * 
	 * @param src
	 *            リクエストパラメータを格納した {@link Map}
	 * @param dest
	 *            フォームオブジェクト
	 */
	void convert(Map<String, Object[]> src, Object dest);

	/**
	 * アクションのフォームオブジェクトから&lt;form&gt;カスタムタグで使用する {@link Map} へ値をマッピングします。
	 * 
	 * @param src
	 *            フォームオブジェクト
	 * @param dest
	 *            &lt;form&gt;カスタムタグで使用する {@link Map}
	 */
	void convert(Object src, Map<String, String[]> dest);

}