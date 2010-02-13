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

package org.seasar.cubby.internal.controller.impl;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.plugin.ActionResultInvocation;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugin.PluginRegistry;

/**
 * {@link org.seasar.cubby.action.ActionResult} のラッパの実装です。
 * 
 * @author baba
 */
class ActionResultWrapperImpl implements ActionResultWrapper {

	/** アクションの実行結果。 */
	private final ActionResult actionResult;

	/** アクションコンテキスト。 */
	private final ActionContext actionContext;

	/**
	 * 指定されたアクションの実行結果をラップしたインスタンスを生成します。
	 * 
	 * @param actionResult
	 *            アクションの実行結果
	 * @param actionContext
	 *            アクションコンテキスト
	 */
	public ActionResultWrapperImpl(final ActionResult actionResult,
			final ActionContext actionContext) {
		super();
		this.actionResult = actionResult;
		this.actionContext = actionContext;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final ActionResultInvocation actionResultInvocation = new ActionResultInvocationImpl(
				request, response, actionContext, actionResult);
		actionResultInvocation.proceed();
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResult getActionResult() {
		return actionResult;
	}

	/**
	 * アクションの実行結果の実行情報の実装です。
	 * 
	 * @author baba
	 */
	static class ActionResultInvocationImpl implements ActionResultInvocation {

		/** 要求。 */
		private final HttpServletRequest request;

		/** 応答。 */
		private final HttpServletResponse response;

		/** アクションのコンテキスト。 */
		private final ActionContext actionContext;

		/** アクションの実行結果。 */
		private final ActionResult actionResult;

		/** プラグインのイテレータ。 */
		private final Iterator<Plugin> pluginsIterator;

		/**
		 * インスタンス化します。
		 * 
		 * @param request
		 *            要求
		 * @param response
		 *            応答
		 * @param actionContext
		 *            アクションのコンテキスト
		 * @param actionResult
		 *            アクションの実行結果
		 */
		public ActionResultInvocationImpl(final HttpServletRequest request,
				final HttpServletResponse response,
				final ActionContext actionContext,
				final ActionResult actionResult) {
			this.request = request;
			this.response = response;
			this.actionContext = actionContext;
			this.actionResult = actionResult;

			final PluginRegistry pluginRegistry = PluginRegistry.getInstance();
			this.pluginsIterator = pluginRegistry.getPlugins().iterator();
		}

		/**
		 * {@inheritDoc}
		 */
		public Void proceed() throws Exception {
			if (pluginsIterator.hasNext()) {
				final Plugin plugin = pluginsIterator.next();
				plugin.invokeActionResult(this);
			} else {
				final ActionResult actionResult = getActionResult();
				actionResult.execute(actionContext, request, response);
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletRequest getRequest() {
			return request;
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletResponse getResponse() {
			return response;
		}

		/**
		 * {@inheritDoc}
		 */
		public ActionContext getActionContext() {
			return actionContext;
		}

		/**
		 * {@inheritDoc}
		 */
		public ActionResult getActionResult() {
			return actionResult;
		}
	}

}
