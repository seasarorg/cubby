package org.seasar.cubby.controller;

import java.util.Map;

public interface Populator {
	void populate(Map<String, Object[]> src, Object dest);

	Map<String, String> describe(final Object src);
}
