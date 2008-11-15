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
package org.seasar.cubby.internal.routing;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.RequestMethod;

/**
 * ルーティング。
 * 
 * @author baba
 * @since 1.1.0
 */
public interface Routing {

	/**
	 * アクションクラスを取得します。
	 * 
	 * @return アクションクラス
	 */
	Class<? extends Action> getActionClass();

	/**
	 * メソッドを取得します。
	 * 
	 * @return メソッド
	 */
	Method getMethod();

	/**
	 * アクションのパスを取得します。
	 */
	String getActionPath();

	/**
	 * URI パラメータ名を取得します。
	 * 
	 * @return URI パラメータ名
	 */
	List<String> getUriParameterNames();

	/**
	 * 正規表現パターンを取得します。
	 * 
	 * @return 正規表現パターン
	 */
	Pattern getPattern();

	/**
	 * リクエストメソッドを取得します。
	 * 
	 * @return リクエストメソッド
	 */
	RequestMethod getRequestMethod();

	/**
	 * このルーティングを使用することを判断するためのパラメータ名を取得します。
	 * 
	 * @return パラメータ名
	 */
	String getOnSubmit();

	/**
	 * プライオリティを取得します。
	 * 
	 * @return プライオリティ
	 */
	int getPriority();

	/**
	 * 自動登録されたルーティングかを示します。
	 * 
	 * @return 自動登録されたルーティングの場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	boolean isAuto();

	/**
	 * 指定されたリクエストメソッドがこのルーティングの対象かどうかを示します。
	 * 
	 * @param requestMethod
	 *            リクエストメソッド
	 * @return 対象の場合は <code>true</code>、そうでない場合は <code>false</code>
	 */
	boolean isAcceptable(final String requestMethod);

}