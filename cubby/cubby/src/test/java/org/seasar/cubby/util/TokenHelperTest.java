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
package org.seasar.cubby.util;

import junit.framework.TestCase;

import org.seasar.cubby.CubbyConstants;
import org.seasar.framework.mock.servlet.MockHttpSession;
import org.seasar.framework.mock.servlet.MockHttpSessionImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;
import org.seasar.framework.util.LruHashMap;

public class TokenHelperTest extends TestCase {

	public void testGenerateGUID() throws Exception {
		String guid = TokenHelper.generateGUID();
		assertEquals("GUIDは32文字", 32, guid.length());
	}

	public void testSetToken() throws Exception {
		String token = "tokenstring";
		MockHttpSession session = new MockHttpSessionImpl(new MockServletContextImpl("/cubby"));
		LruHashMap tokenMap = new LruHashMap(32);
		session.setAttribute(CubbyConstants.ATTR_TOKEN, tokenMap);
		TokenHelper.setToken(session, token);

		assertTrue("トークン文字列がトークン用のMapのキーとして追加", tokenMap
				.containsKey("tokenstring"));
	}

	public void testValidateToken() throws Exception {
		String token = "tokenstring";
		MockHttpSession session = new MockHttpSessionImpl(new MockServletContextImpl("/cubby"));
		LruHashMap tokenMap = new LruHashMap(32);
		session.setAttribute(CubbyConstants.ATTR_TOKEN, tokenMap);
		TokenHelper.setToken(session, token);

		assertFalse("セッション中に存在しないトークン文字列", TokenHelper.validateToken(
				session, "dummytoken"));
		assertTrue("セッション中に存在するトークン文字列", TokenHelper.validateToken(
				session, "tokenstring"));
		assertFalse("1度validateTokenした後は指定したトークン文字列は除去されている", TokenHelper.validateToken(
				session, "tokenstring"));
	}
}
