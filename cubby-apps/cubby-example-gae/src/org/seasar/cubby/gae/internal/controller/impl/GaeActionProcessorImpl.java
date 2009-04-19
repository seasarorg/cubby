package org.seasar.cubby.gae.internal.controller.impl;

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
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.internal.action.impl.ActionContextImpl;
import org.seasar.cubby.internal.action.impl.ActionErrorsImpl;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.spi.ActionHandlerChainProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GaeActionProcessorImpl extends ActionProcessorImpl {

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(GaeActionProcessorImpl.class);

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

		final ActionHandlerChain actionHandlerChain = ProviderFactory.get(
				ActionHandlerChainProvider.class).getActionHandlerChain();
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
