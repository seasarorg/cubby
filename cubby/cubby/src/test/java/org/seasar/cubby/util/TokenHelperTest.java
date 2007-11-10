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
