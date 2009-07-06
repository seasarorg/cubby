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
package org.seasar.cubby.plugins;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.seasar.cubby.internal.util.ServiceLoader;
import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.spi.Provider;

/**
 * プログラムからサービスを登録できるプラグインです。
 * <p>
 * プログラムからサービスとプロバイダをひもづけたい場合に使用してください。
 * </p>
 * 
 * @author baba
 */
public class BinderPlugin extends AbstractPlugin {

	/** プロバイダ。 */
	private final Map<Class<? extends Provider>, Provider> providers = new HashMap<Class<? extends Provider>, Provider>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <S extends Provider> S getProvider(final Class<S> service) {
		return service.cast(providers.get(service));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Class<? extends Provider>> getSupportedServices() {
		return providers.keySet();
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
	public <S extends Provider> Binder<S> bind(final Class<S> service) {
		return new Binder<S>(service);
	}

	/**
	 * サービスをプロバイダに紐づけるためのクラスです。
	 * 
	 * @author baba
	 * 
	 * @param <S>
	 *            サービスの型
	 */
	public class Binder<S extends Provider> {

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
			providers.put(service, instance);
		}

	}

}
