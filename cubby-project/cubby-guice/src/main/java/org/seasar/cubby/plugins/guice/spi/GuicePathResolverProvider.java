/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.plugins.guice.spi;

import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.spi.PathResolverProvider;

import com.google.inject.Inject;

/**
 * Guice 向けの {@link PathResolver} のプロバイダです。
 * 
 * @author baba
 */
public class GuicePathResolverProvider implements PathResolverProvider {

	/** パスに対応するアクションメソッドを解決するためのクラスです。 */
	private final PathResolver pathResolver;

	/**
	 * パスに対応するアクションメソッドを解決するためのクラスを設定します。
	 * 
	 * @param pathResolver
	 *            パスに対応するアクションメソッドを解決するためのクラス
	 */
	@Inject
	public GuicePathResolverProvider(final PathResolver pathResolver) {
		this.pathResolver = pathResolver;
	}

	/**
	 * {@inheritDoc}
	 */
	public PathResolver getPathResolver() {
		return pathResolver;
	}

}
