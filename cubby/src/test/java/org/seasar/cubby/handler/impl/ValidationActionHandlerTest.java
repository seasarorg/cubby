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
package org.seasar.cubby.handler.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.TestUtils;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationProcessor;

public class ValidationActionHandlerTest {

	@Test
	public void handle() throws Exception {
		ActionResult result = new ActionResult() {

			public void execute(ActionContext actionContext,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
			}

		};

		HttpServletRequest request = createMock(HttpServletRequest.class);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionContext context = createMock(ActionContext.class);
		ActionHandlerChain chain = createMock(ActionHandlerChain.class);
		expect(chain.chain(request, response, context)).andReturn(result);
		ValidationProcessor validationProcessor = createMock(ValidationProcessor.class);
		validationProcessor.process(request, context);
		replay(request, response, context, chain, validationProcessor);

		ActionHandler handler = new ValidationActionHandler();
		TestUtils.bind(handler, validationProcessor);

		assertSame(result, handler.handle(request, response, context, chain));

		verify(request, response, context, chain, validationProcessor);
	}

	@Test
	public void handleWithValidationFail() throws Exception {
		ActionResult failResult = new ActionResult() {

			public void execute(ActionContext actionContext,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
			}

		};

		ValidationException validationException = new ValidationException();

		HttpServletRequest request = createMock(HttpServletRequest.class);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionContext context = createMock(ActionContext.class);
		ActionHandlerChain chain = createMock(ActionHandlerChain.class);
		ValidationProcessor validationProcessor = createMock(ValidationProcessor.class);
		validationProcessor.process(request, context);
		expectLastCall().andThrow(validationException);
		expect(
				validationProcessor.handleValidationException(
						validationException, request, context)).andReturn(
				failResult);
		replay(request, response, context, chain, validationProcessor);

		ActionHandler handler = new ValidationActionHandler();
		TestUtils.bind(handler, validationProcessor);

		assertSame(failResult, handler
				.handle(request, response, context, chain));

		verify(request, response, context, chain, validationProcessor);
	}

}
