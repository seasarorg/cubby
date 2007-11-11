package org.seasar.cubby.validator.validators;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.seasar.cubby.controller.ThreadContext;
import org.seasar.cubby.util.TokenHelper;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class TokenValidatorTest extends S2TestCase {

	final static Map<String, Object[]> emptyMap = Collections.emptyMap();

	@SuppressWarnings("unchecked")
	public void testValidate() throws Exception {

		TokenValidator validator = new TokenValidator();
		MockServletContextImpl servletContext = new MockServletContextImpl("/cubby");
		ThreadContext.setRequest(new MockHttpServletRequestImpl(servletContext, "/servlet"));
		HttpSession session = ThreadContext.getRequest().getSession();

		ValidationContext context = new ValidationContext("cubby.token",
				new Object[] { "tokenstring" }, emptyMap, null);
		validator.validate(context);
		assertTrue("セッション中にトークン文字列が存在しないためエラー", context.hasError());
		TokenHelper.setToken(session, "tokenstring");
		context = new ValidationContext("cubby.token",
				new Object[] { "tokenstring" }, emptyMap, null);
		validator.validate(context);
		assertFalse("セッション中にトークン文字列が存在するためエラーではない", context.hasError());

		context = new ValidationContext("cubby.token",
				new Object[] { "tokenstring" }, emptyMap, null);
		validator.validate(context);
		assertTrue("セッション中のトークン文字列が除去された（２重サブミットの状態）ためエラー", context.hasError());
	}

}
