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

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION;
import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_CONTEXT;
import static org.seasar.cubby.CubbyConstants.ATTR_CONVERSION_FAILURES;
import static org.seasar.cubby.CubbyConstants.ATTR_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_FLASH;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;
import static org.seasar.cubby.CubbyConstants.ATTR_WRAPEE_REQUEST;
import static org.seasar.cubby.internal.util.LogMessages.format;
import static org.seasar.cubby.validator.ValidationUtils.getValidation;
import static org.seasar.cubby.validator.ValidationUtils.getValidationRules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionException;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.ConversionFailure;
import org.seasar.cubby.internal.controller.RequestParameterBinder;
import org.seasar.cubby.internal.util.RequestUtils;
import org.seasar.cubby.plugin.ActionInvocation;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugin.ValidationInvocation;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationFailBehaviour;
import org.seasar.cubby.validator.ValidationRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 要求のパスを元にアクションメソッドを決定して実行するクラスの実装です。
 * 
 * @author baba
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

		final Method actionMethod = routing.getActionMethod();
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0004", request.getRequestURI()));
			logger.debug(format("DCUB0005", actionMethod));
		}

		final Class<?> actionClass = routing.getActionClass();

		final Container container = ProviderFactory
				.get(ContainerProvider.class).getContainer();

		final Object action = container.lookup(actionClass);
		request.setAttribute(ATTR_ACTION, action);

		final HttpServletRequest wrapeeRequest = (HttpServletRequest) request
				.getAttribute(ATTR_WRAPEE_REQUEST);
		final ActionErrors actionErrors = setupActionErrors(wrapeeRequest);
		final Map<String, Object> flashMap = setupFlashMap(wrapeeRequest);
		final ActionContext actionContext = new ActionContextImpl(request,
				action, actionClass, actionMethod, actionErrors, flashMap);
		request.setAttribute(ATTR_ACTION_CONTEXT, actionContext);

		actionContext.invokeInitializeMethod();

		final ActionInvocation actionInvocation = new ActionInvocationImpl(
				request, response, actionContext);
		final ActionResult actionResult = actionInvocation.proceed();
		if (actionResult == null) {
			throw new ActionException(format("ECUB0101", actionMethod));
		}

		final ActionResultWrapper actionResultWrapper = new ActionResultWrapperImpl(
				actionResult, actionContext);
		return actionResultWrapper;
	}

	private ActionErrors setupActionErrors(final ServletRequest request) {
		final ActionErrors actionErrors = (ActionErrors) request
				.getAttribute(ATTR_ERRORS);
		if (actionErrors != null) {
			return actionErrors;
		}

		final ActionErrors newActionErrors = new ActionErrorsImpl();
		request.setAttribute(ATTR_ERRORS, newActionErrors);
		return newActionErrors;
	}

	private Map<String, Object> setupFlashMap(final ServletRequest request) {
		@SuppressWarnings("unchecked")
		final Map<String, Object> flashMap = (Map<String, Object>) request
				.getAttribute(ATTR_FLASH);
		if (flashMap != null) {
			return flashMap;
		}

		final Map<String, Object> newFlashMap = new FlashMapImpl(
				(HttpServletRequest) request);
		request.setAttribute(ATTR_FLASH, newFlashMap);
		return newFlashMap;
	}

	/**
	 * アクションの実行情報の実装です。
	 * 
	 * @author baba
	 */
	static class ActionInvocationImpl implements ActionInvocation {

		/** 要求。 */
		private final HttpServletRequest request;

		/** 応答。 */
		private final HttpServletResponse response;

		/** アクションのコンテキスト。 */
		private final ActionContext actionContext;

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
		 */
		public ActionInvocationImpl(final HttpServletRequest request,
				final HttpServletResponse response,
				final ActionContext actionContext) {
			this.request = request;
			this.response = response;
			this.actionContext = actionContext;

			final PluginRegistry pluginRegistry = PluginRegistry.getInstance();
			this.pluginsIterator = pluginRegistry.getPlugins().iterator();
		}

		/**
		 * {@inheritDoc}
		 */
		public ActionResult proceed() throws Exception {
			final ActionResult actionResult;
			if (pluginsIterator.hasNext()) {
				final Plugin plugin = pluginsIterator.next();
				actionResult = plugin.invokeAction(this);
			} else {
				final ValidationInvocation validationInvocation = new ValidationInvocationImpl(
						request, response, actionContext);
				return validationInvocation.proceed();
			}
			return actionResult;
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

	}

	/**
	 * 入力検証の実行情報の実装です。
	 * 
	 * @author baba
	 */
	static class ValidationInvocationImpl implements ValidationInvocation {

		/** 要求パラメータをオブジェクトへバインドするクラス。 */
		private final RequestParameterBinder requestParameterBinder = new RequestParameterBinderImpl();

		/** 要求。 */
		private final HttpServletRequest request;

		/** 応答。 */
		private final HttpServletResponse response;

		/** アクションのコンテキスト。 */
		private final ActionContext actionContext;

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
		 */
		public ValidationInvocationImpl(final HttpServletRequest request,
				final HttpServletResponse response,
				final ActionContext actionContext) {
			this.request = request;
			this.response = response;
			this.actionContext = actionContext;

			final PluginRegistry pluginRegistry = PluginRegistry.getInstance();
			this.pluginsIterator = pluginRegistry.getPlugins().iterator();
		}

		/**
		 * {@inheritDoc}
		 */
		public ActionResult proceed() throws Exception {
			final ActionResult actionResult;
			if (pluginsIterator.hasNext()) {
				final Plugin plugin = pluginsIterator.next();
				actionResult = plugin.invokeValidation(this);
			} else {
				final Map<String, Object[]> parameterMap = RequestUtils
						.getAttribute(request, ATTR_PARAMS);
				final Object formBean = actionContext.getFormBean();

				if (formBean != null) {
					final List<ConversionFailure> conversionFailures = requestParameterBinder
							.bind(parameterMap, formBean, actionContext);
					request.setAttribute(ATTR_CONVERSION_FAILURES,
							conversionFailures);
				}

				try {
					final Validation validation = getValidation(actionContext
							.getActionMethod());
					if (validation != null) {
						final ValidationRules validationRules = getValidationRules(
								actionContext.getAction(), validation.rules());
						validationRules.validate(parameterMap, formBean,
								actionContext.getActionErrors());
					}
					try {
						final Object action = actionContext.getAction();
						final Method actionMethod = actionContext
								.getActionMethod();
						actionResult = (ActionResult) actionMethod
								.invoke(action);
					} catch (final InvocationTargetException e) {
						final Throwable cause = e.getCause();
						if (cause instanceof ValidationException) {
							throw (ValidationException) cause;
						}
						throw e;
					}
				} catch (final ValidationException e) {
					request.setAttribute(ATTR_VALIDATION_FAIL, Boolean.TRUE);
					final ValidationFailBehaviour behaviour = e.getBehaviour();
					return behaviour
							.getValidationErrorActionResult(actionContext);
				}
			}
			return actionResult;
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

	}

}
