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
package org.seasar.cubby.routing;

import java.util.Collection;
import java.util.Map;

import org.seasar.cubby.action.RequestMethod;

/**
 * パスに対応するアクションメソッドを解決するためのクラスです。
 * 
 * @author baba
 * @since 1.0.0
 */
public interface PathResolver {

	/**
	 * 指定されたパスとメソッドからフォワードするための情報を抽出します。
	 * <p>
	 * パスにマッチするパターンがない場合は <code>null</code> を返します。
	 * </p>
	 * 
	 * @param path
	 *            パス
	 * @param requestMethod
	 *            HTTPメソッド
	 * @param characterEncoding
	 *            URI のエンコーディング
	 * @return フォワード情報
	 */
	PathInfo getPathInfo(String path, String requestMethod,
			String characterEncoding);

	/**
	 * ルーティング情報の一覧を取得します。 ルーティング情報は優先度順にソートされています。
	 * 
	 * @return ルーティング情報の一覧
	 */
	Collection<Routing> getRoutings();

	/**
	 * 指定されたアクションクラスのルーティング情報を登録します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 */
	void add(Class<?> actionClass);

	/**
	 * 指定されたアクションクラスのコレクションからすべてのルーティング情報を登録します。
	 * 
	 * @param actionClasses
	 *            アクションクラスのコレクション
	 */
	void addAll(Collection<Class<?>> actionClasses);

	/**
	 * ルーティング情報を手動登録します。
	 * <p>
	 * 手動登録の場合、優先度は0から連番で設定されます。
	 * </p>
	 * 
	 * @param actionPath
	 *            アクションのパス
	 * @param actionClass
	 *            アクションクラス
	 * @param actionMethodName
	 *            アクションメソッド名
	 * @param requestMethod
	 *            リクエストメソッド
	 * @param onSubmit
	 *            アクションメソッドへ振り分けるための要求パラメータ名
	 * @param priority
	 *            プライオリティ
	 */
	void add(String actionPath, Class<?> actionClass, String actionMethodName,
			RequestMethod requestMethod, String onSubmit, int priority);

	/**
	 * 登録されたルーティング情報をクリアします。
	 */
	void clear();

	/**
	 * 指定されたアクションクラス、メソッド名、パラメータからパスを逆引きします。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            メソッド名
	 * @param parameters
	 *            パラメータ
	 * @param characterEncoding
	 *            URI のエンコーディング
	 * @return リダイレクト用のパス
	 * @since 1.1.0
	 */
	String reverseLookup(Class<?> actionClass, String methodName,
			Map<String, String[]> parameters, String characterEncoding);

}