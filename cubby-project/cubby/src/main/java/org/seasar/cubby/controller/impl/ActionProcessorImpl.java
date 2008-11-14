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

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION;
import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_CONTEXT;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_ROUTINGS;
import static org.seasar.cubby.util.LogMessages.format;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionException;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.impl.ActionErrorsImpl;
import org.seasar.cubby.container.Container;
import org.seasar.cubby.container.ContainerFactory;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.controller.ActionResultWrapper;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.RoutingsDispatcher;
import org.seasar.cubby.factory.RequestParserFactory;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.handler.ActionHandlerChainFactory;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.util.CubbyUtils;
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

	/** リクエストパラメータに応じたルーティングを割り当てるためのクラス。 */
	private RoutingsDispatcher routingsDispatcher = new RoutingsDispatcherImpl();

//	/** リクエスト解析器。 */
//	private RequestParserSelector requestParserSelector;
//
//	/**
//	 * リクエストパラメータに応じたルーティングを割り当てるためのクラスを設定します。
//	 * 
//	 * @param routingsDispatcher
//	 *            リクエストパラメータに応じたルーティングを割り当てるためのクラス
//	 */
//	public void setRoutingsDispatcher(
//			final RoutingsDispatcher routingsDispatcher) {
//		this.routingsDispatcher = routingsDispatcher;
//	}
//
//	/**
//	 * リクエスト解析器セレクタを設定します。
//	 * 
//	 * @param requestParserSelector
//	 *            リクエスト解析器
//	 */
//	public void setRequestParserSelector(
//			final RequestParserSelector requestParserSelector) {
//		this.requestParserSelector = requestParserSelector;
//	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResultWrapper process(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final Map<String, Routing> routings = CubbyUtils.getAttribute(request,
				ATTR_ROUTINGS);
		if (routings == null) {
			return null;
		}
		request.removeAttribute(ATTR_ROUTINGS);

		final Map<String, Object[]> parameterMap = parseRequest(request);
		request.setAttribute(ATTR_PARAMS, parameterMap);

		final Routing routing = routingsDispatcher.dispatch(routings,
				parameterMap);
		if (routing == null) {
			return null;
		}

		final Method actionMethod = routing.getMethod();
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0004", request.getRequestURI()));
			logger.debug(format("DCUB0005", actionMethod));
		}

		final Class<? extends Action> actionClass = routing.getActionClass();

		final Container container = ContainerFactory.getContainer();

		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new FlashHashMap<String, Object>(request);

		final Action action = (Action) container.lookup(actionClass);
		action.setErrors(actionErrors);
		action.setFlash(flashMap);
		request.setAttribute(ATTR_ACTION, action);

		final ActionContext actionContext = new ActionContextImpl(action,
				actionClass, actionMethod, actionErrors, flashMap);
		request.setAttribute(ATTR_ACTION_CONTEXT, actionContext);

//		final ActionHandlerChainFactory actionHandlerChainFactory;
//		if (container.has(ActionHandlerChainFactory.class)) {
//			actionHandlerChainFactory = container
//					.lookup(ActionHandlerChainFactory.class);
//		} else {
//			actionHandlerChainFactory = new DefaultActionHandlerChainFactory();
//		}

		final ActionHandlerChainFactory actionHandlerChainFactory = container
				.lookup(ActionHandlerChainFactory.class);
//		final ActionHandlerChainFactory actionHandlerChainFactory = ServiceFactory
//				.getProvider(ActionHandlerChainFactory.class);
		final ActionHandlerChain actionHandlerChain = actionHandlerChainFactory.getActionHandlerChain();
		final ActionResult actionResult = actionHandlerChain.chain(request,
				response, actionContext);
		if (actionResult == null) {
			throw new ActionException(format("ECUB0101", actionMethod));
		}
		final ActionResultWrapper actionResultWrapper = new ActionResultWrapperImpl(
				actionResult, actionContext);
		return actionResultWrapper;
	}

	/**
	 * リクエストをパースしてパラメータを取り出し、{@link Map}に変換して返します。
	 * 
	 * @param request
	 *            リクエスト
	 * @return リクエストパラメータの{@link Map}
	 */
	private Map<String, Object[]> parseRequest(final HttpServletRequest request) {
		final Container container = ContainerFactory.getContainer();
		final RequestParserFactory requestParserFactory = container
				.lookup(RequestParserFactory.class);
		final RequestParser requestParser = requestParserFactory
				.getRequestParser(request);
		if (requestParser == null) {
			throw new NullPointerException("requestParser");
		}
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0016", requestParser));
		}
		final Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(request);
		return parameterMap;
	}

}
