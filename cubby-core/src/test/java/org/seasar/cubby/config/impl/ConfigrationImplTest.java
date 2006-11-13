package org.seasar.cubby.config.impl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class ConfigrationImplTest extends TestCase {

	public void testGetValue() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "value1");
		ConfigrationImpl config = new ConfigrationImpl(map);
		assertEquals("value1", config.getValue("key1"));
	}
}
