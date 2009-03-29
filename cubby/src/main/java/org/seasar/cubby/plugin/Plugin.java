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

import javax.servlet.ServletContextEvent;

import org.seasar.cubby.spi.Provider;

/**
 * プラグインを表すインターフェイスです。
 * <p>
 * プラグインは所属する Web アプリケーションのサーブレットコンテキストに対する変更の通知を受け取ることができます。
 * </p>
 * 
 * @author baba
 */
public interface Plugin {

	/**
	 * Web アプリケーションが要求を処理する準備ができたことの通知です。
	 * 
	 * @param event
	 * @see javax.servlet.ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	void contextInitialized(ServletContextEvent event);

	/**
	 * サーブレットコンテキストがシャットダウン処理に入ることの通知です。
	 * 
	 * @param event
	 * @see javax.servlet.ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	void contextDestroyed(ServletContextEvent event);

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

}
