package org.seasar.cubby.validator.validators;

import javax.servlet.http.HttpSession;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.controller.ThreadContext;
import org.seasar.cubby.util.TokenHelper;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class TokenValidatorTest extends S2TestCase {

	private Action action = new MockAction();

	@SuppressWarnings("unchecked")
	public void testValidate() throws Exception {
		TokenValidator validator = new TokenValidator();
		MockServletContextImpl servletContext = new MockServletContextImpl(
				"/cubby");
		ThreadContext.setRequest(new MockHttpServletRequestImpl(servletContext,
				"/servlet"));
		HttpSession session = ThreadContext.getRequest().getSession();

		ValidationContext context = new MockValidationContext(action);
		action.getErrors().clear();
		validator.validate(context, new Object[] { "tokenstring" });
		assertFalse("セッション中にトークン文字列が存在しないためエラー", action.getErrors().isEmpty());

		TokenHelper.setToken(session, "tokenstring");
		context = new MockValidationContext(action);
		action.getErrors().clear();
		validator.validate(context, new Object[] { "tokenstring" });
		assertTrue("セッション中にトークン文字列が存在するためエラーではない", action.getErrors().isEmpty());

		context = new MockValidationContext(action);
		action.getErrors().clear();
		validator.validate(context, new Object[] { "tokenstring" });
		assertFalse("セッション中のトークン文字列が除去された（２重サブミットの状態）ためエラー", action.getErrors()
				.isEmpty());
	}

}
