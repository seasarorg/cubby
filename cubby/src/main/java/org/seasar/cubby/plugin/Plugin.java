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
package org.seasar.cubby.plugin;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.spi.Provider;

/**
 * プラグインを表すインターフェイスです。
 * <p>
 * プラグインは所属する Web アプリケーションのサーブレットに対する変更の通知を受け取ることができます。
 * </p>
 * 
 * @author baba
 */
public interface Plugin {

	// プラグインのライフサイクル

	/**
	 * <code>CubbyFilter</code> がサービスを提供できるようになった時に実行されます。
	 * 
	 * @param servletContext
	 *            呼び出し元が現在実行している {@link ServletContext} への参照
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	void initialize(ServletContext servletContext);

	/**
	 * このプラグインが提供するサービスプロバイダを取得します。
	 * <p>
	 * このプラグインが指定されたサービスを提供しない場合は <code>null</code> を返します。
	 * </p>
	 * 
	 * @param <S>
	 *            サービスの型
	 * @param service
	 *            サービス
	 * @return サービスプロバイダ
	 */
	<S extends Provider> S getProvider(Class<S> service);

	/**
	 * このプラグインが提供するサービスプロバイダのセットを返します。
	 * 
	 * @return このプラグインが提供するサービスプロバイダのセット
	 */
	Set<Class<? extends Provider>> getSupportedServices();

	/**
	 * プラグインの準備が完了した時に実行されます。
	 */
	void ready();

	/**
	 * <code>CubbyFilter</code> がサービス提供を 停止するときに実行されます。
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	void destroy();

	// リクエストの処理

	/**
	 * 要求に対する処理を開始する時に実行されます。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 */
	void beginRequestProcessing(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * アクションメソッドの実行前に実行されます。
	 * <p>
	 * このメソッドの戻り値が <code>null</code> でない場合はアクションメソッドを実行せず、
	 * このメソッドの戻り値をアクションメソッドの戻り値として以降の処理を行います。
	 * </p>
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param actionContext
	 *            アクションのコンテキスト
	 * @return 置き換えるための実行結果
	 */
	ActionResult beforeActionInvoke(HttpServletRequest request,
			HttpServletResponse response, ActionContext actionContext);

	/**
	 * アクションメソッドの実行後に実行されます。
	 * <p>
	 * このメソッドの戻り値が <code>null</code> でない場合はアクションメソッドの実行結果ではなく、
	 * このメソッドの戻り値をアクションメソッドの戻り値として以降の処理を行います。
	 * </p>
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param actionContext
	 *            アクションのコンテキスト
	 * @param actionResult
	 *            アクションメソッドの実行結果
	 * @return 置き換えるための実行結果
	 */
	ActionResult afterActionInvoke(HttpServletRequest request,
			HttpServletResponse response, ActionContext actionContext,
			ActionResult actionResult);

	/**
	 * アクションの実行結果の実行前に実行されます。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param actionContext
	 *            アクションのコンテキスト
	 * @param actionResult
	 *            アクションメソッドの実行結果
	 */
	void beforeInvokeActionResult(HttpServletRequest request,
			HttpServletResponse response, ActionContext actionContext,
			ActionResult actionResult);

	/**
	 * アクションの実行結果の実行後に実行されます。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param actionContext
	 *            アクションのコンテキスト
	 * @param actionResult
	 *            アクションメソッドの実行結果
	 */
	void afterInvokeActionResult(HttpServletRequest request,
			HttpServletResponse response, ActionContext actionContext,
			ActionResult actionResult);

	/**
	 * 要求に対する処理が終了した時に実行されます。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 */
	void endRequestProcessing(HttpServletRequest request,
			HttpServletResponse response);

}
