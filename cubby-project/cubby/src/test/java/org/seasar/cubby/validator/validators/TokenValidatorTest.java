/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.easymock.IAnswer;
import org.junit.Test;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.util.TokenHelper;
import org.seasar.cubby.validator.ValidationContext;

public class TokenValidatorTest {

	@Test
	public void validate() throws Exception {
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		final HttpSession session = createMock(HttpSession.class);
		expect(request.getSession()).andStubReturn(session);
		expect(request.getSession(false)).andStubReturn(session);

		final Map<String, Object> sessionAttributes = new HashMap<String, Object>();
		expect(session.getAttribute((String) anyObject())).andAnswer(
				new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return sessionAttributes.get(getCurrentArguments()[0]);
					}

				}).anyTimes();
		session.setAttribute((String) anyObject(), anyObject());
		expectLastCall().andAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				sessionAttributes.put((String) getCurrentArguments()[0],
						getCurrentArguments()[1]);
				return null;
			}
		}).anyTimes();
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, session, response);

		ThreadContext.enter(request, response);
		try {
			final TokenValidator validator = new TokenValidator();

			ValidationContext context = new ValidationContext();
			validator.validate(context, new Object[]{"tokenstring"});
			assertFalse("セッション中にトークン文字列が存在しないためエラー", context.getMessageInfos()
					.isEmpty());

			TokenHelper.setToken(session, "tokenstring");
			context = new ValidationContext();
			validator.validate(context, new Object[]{"tokenstring"});
			assertTrue("セッション中にトークン文字列が存在するためエラーではない", context
					.getMessageInfos().isEmpty());

			context = new ValidationContext();
			validator.validate(context, new Object[]{"tokenstring"});
			assertFalse("セッション中のトークン文字列が除去された（２重サブミットの状態）ためエラー", context
					.getMessageInfos().isEmpty());
		} finally {
			ThreadContext.exit();
		}
		ThreadContext.remove();

		verify(request, session, response);
	}

}
