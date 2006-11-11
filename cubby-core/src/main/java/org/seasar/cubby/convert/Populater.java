package org.seasar.cubby.convert;

import java.util.Map;

public interface Populater {
	void populate(Object target, Map<String, Object> params);
}
