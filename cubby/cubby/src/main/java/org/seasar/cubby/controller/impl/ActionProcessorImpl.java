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
package org.seasar.cubby.controller.impl;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.controller.ActionDefBuilder;
import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.exception.ActionRuntimeException;
import org.seasar.cubby.filter.CubbyHttpServletRequestWrapper;
import org.seasar.framework.log.Logger;

/**
 * リクエストのパスを元にアクションメソッドを決定して実行するクラスの実装です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class ActionProcessorImpl implements ActionProcessor {

	/** ロガー。 */
	private static final Logger logger = Logger
			.getLogger(ActionProcessorImpl.class);

	/** アクションのコンテキスト。 */
	private ActionContext context;

	/** アクションの定義を組み立てるビルダ。 */
	private ActionDefBuilder actionDefBuilder;

	/**
	 * アクションのコンテキストを設定します。
	 * 
	 * @param context
	 *            アクションのコンテキスト
	 */
	public void setActionContext(final ActionContext context) {
		this.context = context;
	}

	/**
	 * アクションの定義を組み立てるビルダを設定します。
	 * 
	 * @param actionDefBuilder
	 *            アクションの定義を組み立てるビルダ
	 */
	public void setActionDefBuilder(final ActionDefBuilder actionDefBuilder) {
		this.actionDefBuilder = actionDefBuilder;
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResult process(final HttpServletRequest request,
			final HttpServletResponse response, final FilterChain chain)
			throws Exception {
		final ActionDef actionDef = actionDefBuilder.build(request);
		if (actionDef != null) {
			context.initialize(actionDef);
			if (logger.isDebugEnabled()) {
				logger
						.log("DCUB0004",
								new Object[] { request.getRequestURI() });
				logger.log("DCUB0005", new Object[] { context.getMethod() });
			}
			final ActionResult result = context.invoke();
			if (result == null) {
				throw new ActionRuntimeException("ECUB0001",
						new Object[] { context.getMethod() });
			}
			final HttpServletRequest wrappedRequest = new CubbyHttpServletRequestWrapper(
					request, context);
			result.execute(context, wrappedRequest, response);
			return result;
		} else {
			final HttpServletRequest wrappedRequest = new CubbyHttpServletRequestWrapper(
					request, context);
			chain.doFilter(wrappedRequest, response);
			return null;
		}
	}

}
