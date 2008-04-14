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
import static org.seasar.cubby.interceptor.InterceptorUtils.getAction;
import static org.seasar.cubby.interceptor.InterceptorUtils.getActionClass;
import static org.seasar.cubby.interceptor.InterceptorUtils.getMethod;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.dxo.FormDxo;
import org.seasar.cubby.util.CubbyUtils;

/**
 * アクションの初期化やリクエストパラメータからフォームオブジェクトへのバインドなどを行うインターセプタです。
 * {@link Action#initialize()}、{@link Action#prerender()} の実行を行います。
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class InitializeInterceptor implements MethodInterceptor {

	/** リクエスト。 */
	private HttpServletRequest request;

	/** アクションのコンテキスト。 */
	private ActionContext context;

	/** リクエストパラメータとフォームオブジェクトを変換する DXO */
	private FormDxo formDxo;

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
	 * アクションのコンテキストを設定します。
	 * 
	 * @param context
	 *            アクションのコンテキスト
	 */
	public void setActionContext(final ActionContext context) {
		this.context = context;
	}

	/**
	 * リクエストパラメータとフォームオブジェクトを変換する DXO を設定します。
	 * 
	 * @param formDxo
	 *            リクエストパラメータとフォームオブジェクトを変換する DXO
	 */
	public void setFormDxo(final FormDxo formDxo) {
		this.formDxo = formDxo;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 以下のようなフローでアクションメソッドを実行します。
	 * <ul>
	 * <li>リクエストの属性{@link CubbyConstants#ATTR_PARAMS}の値をフォームオブジェクトにバインドします。</li>
	 * <li>アクションの{@link Action#initialize()}を呼び出します。</li>
	 * <li>アクションメソッドを呼び出します。</li>
	 * <li>アクションの{@link Action#prerender()}を呼び出します。</li>
	 * <li>アクションメソッドの実行結果を返します。</li>
	 * </ul>
	 * </p>
	 */
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		final Action action = getAction(invocation);
		final Class<?> actionClass = getActionClass(invocation);
		final Method method = getMethod(invocation);
		setupForm(action, actionClass, method, request);
		action.initialize();

		final ActionResult result = (ActionResult) invocation.proceed();
		if (result != null) {
			result.prerender(context);
		}

		return result;
	}

	/**
	 * リクエストの属性{@link CubbyConstants#ATTR_PARAMS}の値をフォームオブジェクトにバインドします。
	 * 
	 * @param action
	 *            アクション
	 * @param actionClass
	 *            アクションクラス
	 * @param method
	 *            アクションメソッド
	 * @param request
	 *            リクエスト
	 */
	private void setupForm(final Action action, final Class<?> actionClass,
			final Method method, final HttpServletRequest request) {
		final Object formBean = CubbyUtils.getFormBean(action, actionClass,
				method);
		if (formBean != null) {
			final Map<String, Object[]> params = getAttribute(request,
					ATTR_PARAMS);
			formDxo.convert(params, formBean);
		}
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
