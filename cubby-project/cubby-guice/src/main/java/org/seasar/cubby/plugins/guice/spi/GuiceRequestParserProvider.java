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

import java.util.Collection;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.spi.impl.AbstractRequestParserProvider;

import com.google.inject.Inject;

/**
 * Guice 向けの {@link RequestParser} のプロバイダです。
 * 
 * @author baba
 */
public class GuiceRequestParserProvider extends AbstractRequestParserProvider {

	/** 要求解析器 */
	private final Collection<RequestParser> requestParsers;

	/**
	 * インスタンス化します。
	 * 
	 * @param requestParsers
	 *            要求解析器のコレクション
	 */
	@Inject
	public GuiceRequestParserProvider(
			final Collection<RequestParser> requestParsers) {
		this.requestParsers = requestParsers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<RequestParser> getRequestParsers() {
		return requestParsers;
	}

}
