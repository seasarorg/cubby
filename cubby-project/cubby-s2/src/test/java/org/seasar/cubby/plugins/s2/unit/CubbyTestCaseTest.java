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
package org.seasar.cubby.plugins.s2.unit;


public class CubbyTestCaseTest extends CubbyTestCase {

	/**
	 * Fix https://www.seasar.org/issues/browse/CUBBY-114
	 * 
	 * @throws Exception
	 */
	public void testSetupThreadContext() throws Exception {
//		ThreadContext.remove();
//		try {
//			ThreadContext.getRequest();
//			fail();
//		} catch (IllegalStateException e) {
//		}
//		setupThreadContext(getRequest());
//		assertNotNull(ThreadContext.getRequest());
//		
//		TokenValidator v = new TokenValidator();
//		ValidationContext context = new ValidationContext();
//		v.validate(context, new String[] { "1" });
//		assertTrue("上記のvalidateメソッドの呼び出しでNPEが発生しないこと", true);
	}
}
