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
package org.seasar.cubby.convention.impl;

import org.seasar.cubby.convention.ForwardInfo;
import org.seasar.extension.unit.S2TestCase;

public class PathResolverImplTest extends S2TestCase {
	
	private PathResolverImpl resolver;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		include("app.dicon");
	}
	
	public void testRoot() {
		ForwardInfo info = resolver.getForwardInfo("/");
		assertEquals("/mock/index", info.getForwardPath());

		info = resolver.getForwardInfo("/dummy1");
		assertEquals("/mock/dummy1", info.getForwardPath());
	}
	
}
