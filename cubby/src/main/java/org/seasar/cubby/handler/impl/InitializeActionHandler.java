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
package org.seasar.cubby.handler.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;

/**
 * アクションを初期化するアクションハンドラーです。
 * 
 * @author baba
 * @since 2.0.0
 */
public class InitializeActionHandler implements ActionHandler {

	/**
	 * {@inheritDoc}
	 * <p>
	 * {@link ActionContext#invokeInitializeMethod()} を呼び出してアクションを初期化します。
	 * </p>
	 */
	public ActionResult handle(final HttpServletRequest request,
			final HttpServletResponse response,
			final ActionContext actionContext,
			final ActionHandlerChain actionHandlerChain) throws Exception {
		actionContext.invokeInitializeMethod();

		final ActionResult result = actionHandlerChain.chain(request, response,
				actionContext);
		return result;
	}

}
