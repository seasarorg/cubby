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
		ActionResult result = new ActionResult() {

			public void execute(ActionContext actionContext,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
			}

		};

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
