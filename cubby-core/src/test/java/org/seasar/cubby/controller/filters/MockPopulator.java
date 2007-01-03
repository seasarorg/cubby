package org.seasar.cubby.controller.filters;

import java.util.Map;

import org.seasar.cubby.convert.Populater;

public class MockPopulator implements Populater {
	
	public boolean populateProcessed = false;
	public void populate(Object target, Map<String, Object> params) {
		populateProcessed = true;
	}
}
