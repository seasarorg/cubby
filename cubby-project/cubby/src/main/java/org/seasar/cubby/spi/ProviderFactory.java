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
package org.seasar.cubby.spi;

import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.internal.util.ServiceLoader;

/**
 * サービスプロバイダのファクトリです。
 * <p>
 * このファクトリは {@link ServiceLoader} で生成されたプロバイダをシングルトンとして保持します。 同じサービスを何度
 * {@link #get(Class)} しても同じオブジェクトを返します。
 * </p>
 * 
 * @author baba
 * @since 2.0.0
 */
public class ProviderFactory {

	/** プロバイダのシングルトン。 */
	private static Map<Class<? extends Provider>, Provider> PROVIDERS = new HashMap<Class<? extends Provider>, Provider>();

	/**
	 * インスタンス化を禁止します。
	 */
	private ProviderFactory() {
	}

	/**
	 * 指定されたサービスのプロバイダを取得します。
	 * 
	 * @param <S>
	 *            サービスの型
	 * @param service
	 *            サービス
	 * @return プロバイダ
	 */
	public static <S extends Provider> S get(final Class<S> service) {
		final S provider;
		if (PROVIDERS.containsKey(service)) {
			provider = service.cast(PROVIDERS.get(service));
		} else {
			synchronized (service) {
				if (PROVIDERS.containsKey(service)) {
					provider = service.cast(PROVIDERS.get(service));
				} else {
					provider = ServiceLoader.load(service).getProvider();
					PROVIDERS.put(service, provider);
				}
			}
		}
		return provider;
	}

	/**
	 * キャッシュしているプロバイダのシングルトンをクリアします。
	 */
	public static void clear() {
		PROVIDERS.clear();
	}

	/**
	 * {@link ServiceLoader} からのインスタンス取得を置換するため、指定されたサービスのバインダーを返します。
	 * <p>
	 * テストで使用することを想定しています。
	 * </p>
	 * 
	 * @param <S>
	 *            サービスの型
	 * @param service
	 *            サービス
	 * @return バインダー
	 */
	public static <S extends Provider> Binder<S> bind(final Class<S> service) {
		return new Binder<S>(service);
	}

	/**
	 * サービスを何かに紐づけるためのクラスです。
	 * 
	 * @author baba
	 * 
	 * @param <S>
	 *            サービスの型
	 */
	public static class Binder<S extends Provider> {

		/** サービス。 */
		private final Class<S> service;

		/**
		 * 指定されたサービスのバインダーを生成します。
		 * 
		 * @param service
		 *            サービス
		 */
		private Binder(final Class<S> service) {
			this.service = service;
		}

		/**
		 * サービスを特定のインスタンスにバインドします。
		 * 
		 * @param instance
		 *            インスタンス
		 */
		public void toInstance(final S instance) {
			PROVIDERS.put(service, instance);
		}

	}

}
