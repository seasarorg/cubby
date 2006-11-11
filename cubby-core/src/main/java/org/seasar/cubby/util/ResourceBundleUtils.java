package org.seasar.cubby.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ResourceBundleUtils {

	public static Map toMap(ResourceBundle resource) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration propertyNames = resource.getKeys();
		while (propertyNames.hasMoreElements()) {
			String key = (String) propertyNames.nextElement();
			Object value = resource.getObject(key);
			map.put(key, value);
		}
		return Collections.unmodifiableMap(map);
	}
}
