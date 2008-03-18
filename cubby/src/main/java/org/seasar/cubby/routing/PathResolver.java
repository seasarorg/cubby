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

import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.routing.impl.PathResolverImpl.Routing;

/**
 * {@link Router} がフォワードするための情報を抽出するクラス。
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
	 * @return フォワード情報
	 */
	InternalForwardInfo getInternalForwardInfo(String path, String requestMethod);

	/**
	 * ルーティング情報の一覧を取得します。
	 * ルーティング情報は優先度順にソートされています。
	 * 
	 * @return ルーティング情報の一覧
	 */
	List<Routing> getRoutings();
	
	/**
	 * ルーティング情報を手動登録します。
	 * 手動登録の場合、優先度は0から連番で設定されます。
	 * 
	 * @see {@link Path#priority()} 自動設定の際のプライオリティ
	 * 
	 * @param actionPath
	 *            アクションのパス
	 * @param actionClass
	 *            アクションクラス
	 * @param method
	 *            アクションメソッド
	 * @param erequestMethodsquestMethods
	 *            リクエストメソッド。<code>null</code>の場合、{@link RequestMethod#GET},{@link RequestMethod#POST}がデフォルト値として設定されます。
	 */
	void add(final String actionPath,
			final Class<? extends Action> actionClass, final String methodName,
			final RequestMethod... erequestMethodsquestMethods);
}