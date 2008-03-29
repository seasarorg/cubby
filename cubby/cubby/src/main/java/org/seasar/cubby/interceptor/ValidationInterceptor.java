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

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.validator.ValidationProcessor;
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
 * @since 1.0.0
 */
public class ValidationInterceptor implements MethodInterceptor {

	/** 入力検証を行うクラス。 */
	private ValidationProcessor validationProcessor;

	/** リクエスト。 */
	private HttpServletRequest request;

	/** アクションメソッドの実行時コンテキスト。 */
	private ActionContext context;

	/**
	 * インスタンス化します。
	 */
	public ValidationInterceptor() {
	}

	/**
	 * 入力検証を行うクラスを設定します。
	 * 
	 * @param validationProcessor
	 *            入力検証を行うクラス
	 */
	public void setValidationProcessor(
			final ValidationProcessor validationProcessor) {
		this.validationProcessor = validationProcessor;
	}

	/**
	 * リクエストを設定します。
	 * 
	 * @param request
	 *            リクエスト
	 */
	public void setRequest(final HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * アクションメソッド実行時のコンテキストを設定します。
	 * 
	 * @param context
	 *            アクションメソッド実行時のコンテキスト
	 */
	public void setActionContext(final ActionContext context) {
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * アクションメソッドの実行前に入力検証を実行し、入力にエラーがあった場合はアクションメソッドを実行せずに
	 * {@link ValidationRules#fail(String)} の結果を返します。
	 * </p>
	 */
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		final Validation validation = getValidation(invocation.getMethod());
		if (validation != null) {
			final Map<String, Object[]> params = getAttribute(request,
					ATTR_PARAMS);
			final Object form = context.getFormBean();
			final ActionErrors errors = context.getAction().getErrors();
			final Action action = (Action) invocation.getThis();
			final ValidationRules rules = getValidationRules(action, validation
					.rules());
			final boolean success = validationProcessor.process(errors, params,
					form, rules);
			if (!success) {
				request.setAttribute(ATTR_VALIDATION_FAIL, true);
				final String errorPage = validation.errorPage();
				return rules.fail(errorPage);
			}
		}

		final Object result = invocation.proceed();

		return result;
	}

	/**
	 * 指定されたメソッドを修飾する {@link Validation} を取得します。
	 * 
	 * @param method
	 *            メソッド
	 * @return {@link Validation}、修飾されていない場合は <code>null</code>
	 */
	private static Validation getValidation(final Method method) {
		return method.getAnnotation(Validation.class);
	}

	/**
	 * 実行しているアクションメソッドの入力検証ルールの集合を取得します。
	 * 
	 * @param action
	 *            アクション
	 * @param rulesPropertyName
	 *            入力検証ルールの集合が定義されたプロパティ名
	 * @return アクションメソッドの入力検証ルールの集合
	 */
	private ValidationRules getValidationRules(final Action action,
			final String rulesPropertyName) {
		final BeanDesc beanDesc = BeanDescFactory
				.getBeanDesc(action.getClass());
		final PropertyDesc propertyDesc = beanDesc
				.getPropertyDesc(rulesPropertyName);
		final ValidationRules rules = (ValidationRules) propertyDesc
				.getValue(action);
		return rules;
	}

	/**
	 * リクエストから属性を取得します。
	 * 
	 * @param <T>
	 *            取得する属性の型
	 * @param request
	 *            リクエスト
	 * @param name
	 *            属性名
	 * @return 属性
	 */
	@SuppressWarnings("unchecked")
	private static <T> T getAttribute(final HttpServletRequest request,
			final String name) {
		return (T) request.getAttribute(name);
	}

}
