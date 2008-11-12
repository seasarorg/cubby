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
package org.seasar.cubby.controller.chain;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.RequestParameterBinder;
import org.seasar.cubby.controller.impl.RequestParameterBinderImpl;
import org.seasar.cubby.util.CubbyUtils;

/**
 * アクションの初期化やリクエストパラメータからフォームオブジェクトへのバインドなどを行うインターセプタです。
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class InitializeActionHandler implements ActionHandler {

	/** リクエストパラメータをオブジェクトへバインドするクラス。 */
	private RequestParameterBinder requestParameterBinder = new RequestParameterBinderImpl();

	//
	// /**
	// * リクエストパラメータをオブジェクトへバインドするクラスを設定します。
	// *
	// * @param requestParameterBinder
	// * リクエストパラメータをオブジェクトへバインドするクラス
	// */
	// public void setRequestParameterBinder(
	// final RequestParameterBinder requestParameterBinder) {
	// this.requestParameterBinder = requestParameterBinder;
	// }

	/**
	 * {@inheritDoc}
	 * <p>
	 * 以下のようなフローでアクションメソッドを実行します。
	 * <ul>
	 * <li>{@link Action#invokeInitializeMethod(Method)} を呼び出してアクションを初期化します。</li>
	 * <li>{@link RequestParameterBinder#bind(Map, Object, Method)}
	 * によってリクエストパラメータをフォームオブジェクトにバインドします。</li>
	 * <li>アクションメソッドを呼び出します。</li>
	 * <li>メソッドの実行結果を返します。</li>
	 * </ul>
	 * </p>
	 * 
	 * @throws Exception
	 */
	public ActionResult handle(HttpServletRequest request,
			HttpServletResponse response, ActionContext actionContext,
			ActionHandlerChain actionInvocationChain) throws Exception {
		actionContext.invokeInitializeMethod();

		final Object formBean = actionContext.getFormBean();
		if (formBean != null) {
			final Map<String, Object[]> parameterMap = CubbyUtils.getAttribute(
					request, ATTR_PARAMS);
			requestParameterBinder.bind(parameterMap, formBean, actionContext);
		}

		final ActionResult result = actionInvocationChain.chain(request,
				response, actionContext);
		return result;
	}

}
