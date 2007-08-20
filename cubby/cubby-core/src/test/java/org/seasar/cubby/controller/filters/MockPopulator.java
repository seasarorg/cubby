package org.seasar.cubby.controller.filters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.controller.Populater;

public class MockPopulator implements Populater {
	private static final Map<String, String> DESCRIBED_MAP = Collections
			.unmodifiableMap(new HashMap<String, String>());

	public boolean populateProcessed = false;

	public void populate(Map<String, Object> src, Object dest) {
		populateProcessed = true;
	}

	public Map<String, String> describe(Object src) {
		return DESCRIBED_MAP;
	}
}
