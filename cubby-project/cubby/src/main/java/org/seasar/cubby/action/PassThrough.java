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

package org.seasar.cubby.action;

import static org.seasar.cubby.CubbyConstants.ATTR_FILTER_CHAIN;
import static org.seasar.cubby.internal.util.RequestUtils.getAttribute;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * フィルターチェーンを実行する {@link ActionResult} です。
 * 
 * @author baba
 */
public class PassThrough implements ActionResult {

	/**
	 * {@inheritDoc}
	 * 
	 * @see FilterChain#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse)
	 */
	public void execute(final ActionContext actionContext,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		final FilterChain filterChain = getAttribute(request, ATTR_FILTER_CHAIN);
		if (filterChain == null) {
			throw new NullPointerException("filterChain");
		}
		filterChain.doFilter(request, response);
	}

}
