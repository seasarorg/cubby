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
package org.seasar.cubby.internal.routing;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.PathResolver;

/**
 * ルーター。
 * 
 * @author baba
 * @since 1.0.0
 */
public interface Router {

	/**
	 * 対象外パターンを指定せずにルーティング処理を行い、内部フォワード情報を返します。
	 * 
	 * @param request
	 *            リクエスト
	 * @param response
	 *            レスポンス
	 * @return リクエスト URI に対応する内部フォワード情報、URI に対応する内部フォワード情報がない場合は
	 *         <code>null</code>
	 * @see #routing(HttpServletRequest, HttpServletResponse, List)
	 */
	PathInfo routing(HttpServletRequest request, HttpServletResponse response);

	/**
	 * リクエストのルーティング処理を行い、内部フォワード情報を返します。
	 * <p>
	 * このメソッドはリクエスト URI とメソッドに対応するフォワード情報を{@link PathResolver} によって決定します。
	 * </p>
	 * 
	 * @param request
	 *            リクエスト
	 * @param response
	 *            レスポンス
	 * @param ignorePathPatterns
	 *            対象外とするパスのパターン
	 * @return リクエストに対応する内部フォワード情報、URI とリクエストメソッドに対応する内部フォワード情報がない場合や URI
	 *         が対象外とするパスのパターンにマッチする場合は <code>null</code>
	 * @see PathResolver#getPathInfo(String, String, String)
	 * @see org.seasar.cubby.action.Path
	 * @see org.seasar.cubby.action.Accept
	 */
	PathInfo routing(HttpServletRequest request, HttpServletResponse response,
			List<Pattern> ignorePathPatterns);

}