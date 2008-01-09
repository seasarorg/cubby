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
