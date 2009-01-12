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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;

/**
 * アクションを実行するアクションハンドラーです。
 * 
 * @author baba
 * @since 2.0.0
 */
public class InvocationActionHandler implements ActionHandler {

	/**
	 * {@inheritDoc}
	 * <p>
	 * アクションコンテキスト中のアクションメソッドを実行します。
	 * </p>
	 */
	public ActionResult handle(final HttpServletRequest request,
			final HttpServletResponse response,
			final ActionContext actionContext,
			final ActionHandlerChain actionInvocationChain) throws Exception {
		try {
			final Object action = actionContext.getAction();
			final Method actionMethod = actionContext.getActionMethod();
			final ActionResult actionResult = (ActionResult) actionMethod
					.invoke(action);
			return actionResult;
		} catch (final InvocationTargetException e) {
			final Throwable target = e.getTargetException();
			if (target instanceof Error) {
				throw (Error) target;
			} else if (target instanceof RuntimeException) {
				throw (RuntimeException) target;
			} else {
				throw (Exception) target;
			}
		}
	}

}
