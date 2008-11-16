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

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION;
import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_CONTEXT;
import static org.seasar.cubby.internal.util.LogMessages.format;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionException;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.handler.ActionHandlerChainFactory;
import org.seasar.cubby.internal.action.impl.ActionContextImpl;
import org.seasar.cubby.internal.action.impl.ActionErrorsImpl;
import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.container.ContainerFactory;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.routing.Routing;
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

	/**
	 * {@inheritDoc}
	 */
	public ActionResultWrapper process(final HttpServletRequest request,
			final HttpServletResponse response, final Routing routing)
			throws Exception {

		final Method actionMethod = routing.getMethod();
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0004", request.getRequestURI()));
			logger.debug(format("DCUB0005", actionMethod));
		}

		final Class<? extends Action> actionClass = routing.getActionClass();

		final Container container = ContainerFactory.getContainer();

		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new FlashHashMap<String, Object>(
				request);

		final Action action = (Action) container.lookup(actionClass);
		action.setErrors(actionErrors);
		action.setFlash(flashMap);
		request.setAttribute(ATTR_ACTION, action);

		final ActionContext actionContext = new ActionContextImpl(action,
				actionClass, actionMethod, actionErrors, flashMap);
		request.setAttribute(ATTR_ACTION_CONTEXT, actionContext);

		final ActionHandlerChainFactory actionHandlerChainFactory = container
				.lookup(ActionHandlerChainFactory.class);
		final ActionHandlerChain actionHandlerChain = actionHandlerChainFactory
				.getActionHandlerChain();
		final ActionResult actionResult = actionHandlerChain.chain(request,
				response, actionContext);
		if (actionResult == null) {
			throw new ActionException(format("ECUB0101", actionMethod));
		}
		final ActionResultWrapper actionResultWrapper = new ActionResultWrapperImpl(
				actionResult, actionContext);
		return actionResultWrapper;
	}

}