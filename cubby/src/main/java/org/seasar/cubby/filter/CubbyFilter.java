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
package org.seasar.cubby.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.controller.ThreadContext;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.log.Logger;

/**
 * Cubby用のフィルター。
 * リクエストの処理をActionProcesserに委譲します。
 * 
 * @author agata
 * @author baba
 */
public class CubbyFilter implements Filter {

	/**
	 * ログ 
	 */
	private static final Logger logger = Logger.getLogger(CubbyFilter.class);

	/**
	 * 初期化処理。
	 * 特に何も処理しません。
	 */
	public void init(final FilterConfig config) throws ServletException {
	}

	/**
	 * 廃棄処理。
	 * 特に何も処理しません。
	 */
	public void destroy() {
	}

	/**
	 * フィルター処理。
	 * リクエストの処理をS2Containerから取得したActionProcesserに委譲します。
	 */
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {
		try {
			final HttpServletRequest request = (HttpServletRequest) req;
			final HttpServletResponse response = (HttpServletResponse) res;
			ThreadContext.setRequest(request);

			final ActionProcessor processor = SingletonS2Container
					.getComponent(ActionProcessor.class);
			processor.process(request, response, chain);
		} catch (final Throwable e) {
			logger.log(e);
			throw new ServletException(e);
		} finally {
			ThreadContext.remove();
		}
	}

}
