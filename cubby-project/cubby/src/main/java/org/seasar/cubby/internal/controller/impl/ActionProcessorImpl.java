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
package org.seasar.cubby.internal.controller.impl;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION;
import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_CONTEXT;
import static org.seasar.cubby.CubbyConstants.ATTR_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_FLASH;
import static org.seasar.cubby.internal.util.LogMessages.format;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionException;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.internal.action.impl.ActionContextImpl;
import org.seasar.cubby.internal.action.impl.ActionErrorsImpl;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * リクエストのパスを元にアクションメソッドを決定して実行するクラスの実装です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class ActionProcessorImpl implements ActionProcessor {

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(ActionProcessorImpl.class);

	/** プラグインのレジストリ。 */
	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	/**
	 * {@inheritDoc}
	 */
	public ActionResultWrapper process(final HttpServletRequest request,
			final HttpServletResponse response, final Routing routing)
			throws Exception {

		final Method actionMethod = routing.getActionMethod();
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0004", request.getRequestURI()));
			logger.debug(format("DCUB0005", actionMethod));
		}

		final Class<?> actionClass = routing.getActionClass();

		final Container container = ProviderFactory
				.get(ContainerProvider.class).getContainer();

		final ActionErrors actionErrors = new ActionErrorsImpl();
		request.setAttribute(ATTR_ERRORS, actionErrors);

		final Map<String, Object> flashMap = new FlashMap<String, Object>(
				request);
		request.setAttribute(ATTR_FLASH, flashMap);

		final Object action = container.lookup(actionClass);
		request.setAttribute(ATTR_ACTION, action);

		final ActionContext actionContext = new ActionContextImpl(action,
				actionClass, actionMethod, actionErrors, flashMap);
		request.setAttribute(ATTR_ACTION_CONTEXT, actionContext);

		final ActionResult actionResult = invokeAction(request, response,
				actionContext);
		if (actionResult == null) {
			throw new ActionException(format("ECUB0101", actionMethod));
		}
		final ActionResultWrapper actionResultWrapper = new ActionResultWrapperImpl(
				actionResult, actionContext);
		return actionResultWrapper;
	}

	/**
	 * アクションコンテキスト中のアクションメソッドを実行します。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param actionContext
	 *            アクションコンテキスト
	 * @return アクションメソッドの実行結果
	 * @throws Exception
	 *             アクションメソッドの実行時に例外が発生した場合
	 */
	protected ActionResult invokeAction(final HttpServletRequest request,
			final HttpServletResponse response,
			final ActionContext actionContext) throws Exception {
		actionContext.invokeInitializeMethod();

		for (final Plugin plugin : pluginRegistry.getPlugins()) {
			final ActionResult pluginActionResult = plugin.beforeActionInvoke(
					request, response, actionContext);
			if (pluginActionResult != null) {
				return pluginActionResult;
			}
		}

		final Object action = actionContext.getAction();
		final Method actionMethod = actionContext.getActionMethod();
		final ActionResult actionResult = (ActionResult) actionMethod
				.invoke(action);

		for (final Plugin plugin : pluginRegistry.getPlugins()) {
			final ActionResult pluginActionResult = plugin.afterActionInvoke(
					request, response, actionContext, actionResult);
			if (pluginActionResult != null) {
				return pluginActionResult;
			}
		}

		return actionResult;
	}

}