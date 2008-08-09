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
package org.seasar.cubby.validator.validators;

import javax.servlet.http.HttpSession;

import org.seasar.cubby.controller.ThreadContext;
import org.seasar.cubby.util.TokenHelper;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class TokenValidatorTest extends S2TestCase {

	@SuppressWarnings("unchecked")
	public void testValidate() throws Exception {
		TokenValidator validator = new TokenValidator();
		MockServletContextImpl servletContext = new MockServletContextImpl(
				"/cubby");
		ThreadContext.setRequest(new MockHttpServletRequestImpl(servletContext,
				"/servlet"));
		HttpSession session = ThreadContext.getRequest().getSession();

		ValidationContext context = new ValidationContext();
		validator.validate(context, new Object[] { "tokenstring" });
		assertFalse("セッション中にトークン文字列が存在しないためエラー", context.getMessageInfos()
				.isEmpty());

		TokenHelper.setToken(session, "tokenstring");
		context = new ValidationContext();
		validator.validate(context, new Object[] { "tokenstring" });
		assertTrue("セッション中にトークン文字列が存在するためエラーではない", context.getMessageInfos()
				.isEmpty());

		context = new ValidationContext();
		validator.validate(context, new Object[] { "tokenstring" });
		assertFalse("セッション中のトークン文字列が除去された（２重サブミットの状態）ためエラー", context
				.getMessageInfos().isEmpty());
	}

	public void testRequestIsNull() throws Exception {
		ThreadContext.setRequest(null);
		TokenValidator validator = new TokenValidator();
		ValidationContext context = new ValidationContext();
		try {
			validator.validate(context, new Object[] { "tokenstring" });
			fail("ThreadContext.getRequest()がnullの場合、ここは通らない");
		} catch (IllegalStateException ex) {
		}
	}

}
