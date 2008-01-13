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
package org.seasar.cubby.interceptor;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.validator.ValidationProcessor;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * 入力検証を行います。
 * <p>
 * 入力検証が失敗した場合
 * <ul>
 * <li>またリクエスト中の入力検証エラーフラグを <code>true</code> に設定します。</li>
 * <li>アクションメソッドに設定された{@link Validation#errorPage()}へフォワードします。</li>
 * </ul>
 * </p>
 * 
 * @see CubbyConstants#ATTR_VALIDATION_FAIL 入力検証エラーフラグの属性名
 * @author agata
 * @author baba
 * @since 1.0
 */
public class ValidationInterceptor implements MethodInterceptor {

	private static final ValidationRules NULL_VALIDATION_RULES = new ValidationRules() {
		public List<ValidationRule> getRules() {
			return Collections.emptyList();
		}
	};

	private ValidationProcessor validationProcessor;

	private HttpServletRequest request;

	private ActionContext context;

	public ValidationInterceptor() {
	}

	public void setValidationProcessor(
			final ValidationProcessor validationProcessor) {
		this.validationProcessor = validationProcessor;
	}

	public void setRequest(final HttpServletRequest request) {
		this.request = request;
	}

	public void setActionContext(final ActionContext context) {
		this.context = context;
	}

	public Object invoke(final MethodInvocation invocation) throws Throwable {
		final Validation validation = context.getValidation();

		final boolean success;
		if (validation == null) {
			success = true;
		} else {
			final Map<String, Object[]> params = getParams(request);
			final Object form = context.getFormBean();
			final ActionErrors errors = context.getAction().getErrors();
			final ValidationRules rules = getValidationRules(context);
			success = validationProcessor.process(errors, params, form, rules);
		}

		final Object result;
		if (success) {
			result = invocation.proceed();
		} else {
			request.setAttribute(ATTR_VALIDATION_FAIL, true);
			final String path = validation.errorPage();
			result = new Forward(path);
		}

		return result;
	}

	private ValidationRules getValidationRules(final ActionContext context) {
		final Validation validation = context.getValidation();
		final ValidationRules rules;
		if (validation == null) {
			rules = NULL_VALIDATION_RULES;
		} else {
			final Action action = context.getAction();
			final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(action
					.getClass());
			final PropertyDesc propertyDesc = beanDesc
					.getPropertyDesc(validation.rules());
			rules = (ValidationRules) propertyDesc.getValue(action);
		}
		return rules;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object[]> getParams(
			final HttpServletRequest request) {
		return (Map<String, Object[]>) request.getAttribute(ATTR_PARAMS);
	}

}
