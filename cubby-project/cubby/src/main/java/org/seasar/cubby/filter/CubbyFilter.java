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

import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;

/**
 * Cubby用のフィルター。
 * <p>
 * リクエストの処理を{@link ActionProcessor}に委譲します。
 * </p>
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class CubbyFilter implements Filter {

	private ActionProcessor actionProcessor = new ActionProcessorImpl();

	/**
	 * {@inheritDoc}
	 */
	public void init(final FilterConfig config) throws ServletException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void destroy() {
	}

	/**
	 * フィルター処理。
	 * <p>
	 * リクエストの処理を {@link ActionProcessor} に委譲します。
	 * </p>
	 * 
	 * @param req
	 *            リクエスト
	 * @param res
	 *            レスポンス
	 * @param chain
	 *            フィルタチェイン
	 * @throws IOException
	 *             リクエストディスパッチャやフィルタチェインで例外が発生した場合
	 * @throws ServletException
	 *             リクエストディスパッチャやフィルタチェインで例外が発生した場合
	 */
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = new CubbyHttpServletRequestWrapper(
				(HttpServletRequest) req);
		final HttpServletResponse response = (HttpServletResponse) res;
		ThreadContext.setRequest(request);
		try {
			// final Container container = ContainerFactory.getContainer();
			// TODO
			// final S2Container container = SingletonS2ContainerFactory
			// .getContainer();
			// final ActionProcessor processor = container
			// .lookup(ActionProcessor.class);
			final ActionResultWrapper actionResultWrapper = actionProcessor
					.process(request, response);
			if (actionResultWrapper != null) {
				actionResultWrapper.execute(request, response);
			} else {
				chain.doFilter(request, response);
			}
		} catch (final Exception e) {
			if (e instanceof IOException) {
				throw (IOException) e;
			} else if (e instanceof ServletException) {
				throw (ServletException) e;
			} else {
				throw new ServletException(e);
			}
		} finally {
			ThreadContext.remove();
		}
	}

}
