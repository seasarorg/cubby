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

package org.seasar.cubby.plugins.s2.spi;

import java.util.Collection;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.spi.impl.AbstractRequestParserProvider;
import org.seasar.framework.container.S2Container;

/**
 * S2Container 向けの {@link RequestParser} のプロバイダです。
 * 
 * @author baba
 */
public class S2RequestParserProvider extends AbstractRequestParserProvider {

	public static final String s2Container_BINDING = "bindingType=must";

	/** S2 コンテナ。 */
	private S2Container s2Container;

	/**
	 * S2 コンテナを設定します。
	 * 
	 * @param s2Container
	 *            S2 コンテナ
	 */
	public void setS2Container(final S2Container s2Container) {
		this.s2Container = s2Container;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * S2 コンテナのルートコンテナから取得できる {@link RequestParser} のインスタンスを返します。
	 * </p>
	 */
	@Override
	protected Collection<RequestParser> getRequestParsers() {
		@SuppressWarnings("unchecked")
		final Collection<RequestParser> requestParsers = (Collection<RequestParser>) s2Container
				.getRoot().getComponent("requestParsers");
		return requestParsers;
	}

}
