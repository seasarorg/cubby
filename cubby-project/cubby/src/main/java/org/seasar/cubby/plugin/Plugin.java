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
	 * このプラグインを初期化します。
	 * <p>
	 * <code>CubbyFilter</code> がサービスを提供できるようになった時に実行されます。
	 * </p>
	 * 
	 * @param servletContext
	 *            呼び出し元が現在実行している {@link ServletContext} への参照
	 * @throws Exception
	 *             プラグインの初期化に失敗した場合
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	void initialize(ServletContext servletContext) throws Exception;

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
	 * このプラグインを準備します。
	 * <p>
	 * プラグインの準備が完了した時に実行されます。
	 * </p>
	 * 
	 * @throws Exception
	 *             プラグインの準備に失敗した場合
	 */
	void ready() throws Exception;

	/**
	 * このプラグインを破棄します。
	 * <p>
	 * <code>CubbyFilter</code> がサービスの提供を停止するときに実行されます。
	 * </p>
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	void destroy();

	// サーブレット要求の処理

	/**
	 * 要求に対する処理を実行します。
	 * <p>
	 * このメソッドをオーバーライドすることで、要求に対する処理の実行をインターセプトすることができます。
	 * </p>
	 * <p>
	 * このメソッド内で {@link RequestProcessingInvocation#proceed()}
	 * メソッドを実行することで、別のプラグインの
	 * {@link #invokeRequestProcessing(RequestProcessingInvocation)}
	 * または要求に対する処理が実行されます。
	 * </p>
	 * 
	 * @param invocation
	 *            要求に対する処理の実行情報
	 * @throws Exception
	 *             要求に対する処理の実行時に例外が発生した場合
	 */
	void invokeRequestProcessing(RequestProcessingInvocation invocation)
			throws Exception;

	/**
	 * アクションメソッドを実行します。
	 * <p>
	 * このメソッドをオーバーライドすることで、アクションメソッドの実行をインターセプトすることができます。
	 * </p>
	 * <p>
	 * このメソッド内で {@link ActionInvocation#proceed()} メソッドを実行することで、別のプラグインの
	 * {@link #invokeAction(ActionInvocation)} またはアクションメソッドが実行されます。
	 * </p>
	 * 
	 * @param invocation
	 *            アクションメソッドの実行情報
	 * @return アクションの実行結果
	 * @throws Exception
	 *             アクションメソッドの実行時に例外が発生した場合
	 */
	ActionResult invokeAction(ActionInvocation invocation) throws Exception;

	/**
	 * アクションの実行結果を実行します。
	 * <p>
	 * このメソッドをオーバーライドすることで、アクションの実行結果の実行をインターセプトすることができます。
	 * </p>
	 * <p>
	 * このメソッド内で {@link ActionResultInvocation#proceed()} メソッドを実行することで、別のプラグインの
	 * {@link #invokeActionResult(ActionResultInvocation)} またはアクションの実行結果が実行されます。
	 * </p>
	 * 
	 * @param invocation
	 *            アクションの実行結果の実行情報
	 * @throws Exception
	 *             アクションの実行結果の実行時に例外が発生した場合
	 */
	void invokeActionResult(ActionResultInvocation invocation) throws Exception;

}
