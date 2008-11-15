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
package org.seasar.cubby.internal.controller.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.RequestParser;

/**
 * リクエスト解析器のデフォルト実装です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class DefaultRequestParserImpl implements RequestParser {

	/** デフォルトの優先順位。 */
	static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;

	/** 優先順位。 */
	private int priority = DEFAULT_PRIORITY;

	/**
	 * {@inheritDoc}
	 * <p>
	 * {@link HttpServletRequest#getParameterMap()} の結果をそのまま返します。
	 * </p>
	 * 
	 * @see HttpServletRequest#getParameterMap()
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object[]> getParameterMap(
			final HttpServletRequest request) {
		return request.getParameterMap();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isParsable(final HttpServletRequest request) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * デフォルトの優先順位は {@link Integer#MAX_VALUE} です。
	 * </p>
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * 優先順位を設定します。
	 * 
	 * @param priority
	 *            優先順位
	 */
	public void setPriority(final int priority) {
		this.priority = priority;
	}

}
