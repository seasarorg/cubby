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

package org.seasar.cubby.internal.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.internal.util.LruHashMap;
import org.seasar.cubby.internal.util.TokenHelper;

import static org.easymock.EasyMock.*;

public class TokenHelperTest {

	@Test
	public void generateGUID() throws Exception {
		final int count = 50000;
		Set<String> generatedGuids = new HashSet<String>();
		for (int i = 0; i < count; i++) {
			String guid = TokenHelper.generateGUID();
			// assertEquals("GUIDは32文字[" + guid + "]", 32, guid.length());
			assertNotNull(guid);
			assertTrue(guid.length() > 0);
			assertFalse("GUIDが重複した:" + guid, generatedGuids.contains(guid));
			generatedGuids.add(guid);
		}
		assertEquals(count, generatedGuids.size());
	}

	@Test
	public void setToken() throws Exception {
		String token = "tokenstring";

		HttpSession session = createMock(HttpSession.class);
		Map<String, String> tokenMap = new LruHashMap<String, String>(32);
		expect(session.getAttribute(CubbyConstants.ATTR_TOKEN)).andReturn(
				tokenMap).anyTimes();
		replay(session);

		TokenHelper.setToken(session, token);

		assertTrue("トークン文字列がトークン用のMapのキーとして追加", tokenMap
				.containsKey("tokenstring"));
	}

	@Test
	public void validateToken() throws Exception {
		String token = "tokenstring";

		HttpSession session = createMock(HttpSession.class);
		Map<String, String> tokenMap = new LruHashMap<String, String>(32);
		expect(session.getAttribute(CubbyConstants.ATTR_TOKEN)).andReturn(
				tokenMap).anyTimes();
		replay(session);

		TokenHelper.setToken(session, token);

		assertFalse("セッション中に存在しないトークン文字列", TokenHelper.validateToken(session,
				"dummytoken"));
		assertTrue("セッション中に存在するトークン文字列", TokenHelper.validateToken(session,
				"tokenstring"));
		assertFalse("1度validateTokenした後は指定したトークン文字列は除去されている", TokenHelper
				.validateToken(session, "tokenstring"));
	}
}
