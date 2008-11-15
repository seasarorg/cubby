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
package org.seasar.cubby.handler.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.internal.validator.impl.ValidationProcessorImpl;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationProcessor;

/**
 * 入力検証を行います。
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class ValidationActionHandler implements ActionHandler {

	/** 入力検証処理。 */
	private ValidationProcessor validationProcessor = new ValidationProcessorImpl();

	// /**
	// * 入力検証処理を設定します。
	// *
	// * @param validationProcessor
	// * 入力検証処理
	// */
	// public void setValidationProcessor(
	// final ValidationProcessor validationProcessor) {
	// this.validationProcessor = validationProcessor;
	// }

	/**
	 * {@inheritDoc}
	 * <p>
	 * メソッドの実行前に入力検証を実行します。
	 * </p>
	 * 
	 * @throws Exception
	 */
	public ActionResult handle(HttpServletRequest request,
			HttpServletResponse response, ActionContext actionContext,
			ActionHandlerChain actionInvocationChain) throws Exception {
		try {
			validationProcessor.process(request, actionContext);
			return actionInvocationChain
					.chain(request, response, actionContext);
		} catch (final ValidationException e) {
			return validationProcessor.handleValidationException(e, request,
					actionContext);
		}
	}

}