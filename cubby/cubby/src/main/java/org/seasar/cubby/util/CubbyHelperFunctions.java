package org.seasar.cubby.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.framework.util.StringUtil;

public class CubbyHelperFunctions {

	public static String toAttr(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> entry : map.entrySet()) {
			final String key = entry.getKey();
			if ("value".equals(key) || "checkedValue".equals(key)) {
				continue;
			}
			sb.append(key);
			sb.append("=\"");
			sb.append(CubbyFunctions.escapeHtml(entry.getValue()));
			sb.append("\" ");
		}
		return sb.toString();
	}

	public static boolean isChecked(String value, Object values) {
		if (values instanceof Collection) {
			return ((Collection<?>) values).contains(value);
		} else if (values.getClass().isArray()) {
			for (Object v : (Object[]) values) {
				if (equalsValue(v, value)) {
					return true;
				}
			}
			return false;
		} else {
			return equalsValue(values, value);
		}
	}

	private static boolean equalsValue(Object values, Object value) {
		if (values == value) {
			return true;
		} else if (values == null) {
			return false;
		} else {
			return values.toString().equals(value.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public static void addClassName(Map dyn, String className) {
		String classValue = (String) dyn.get("class");
		if (StringUtil.isEmpty(classValue)) {
			classValue = className;
		} else {
			classValue = classValue + " " + className;
		}
		dyn.put("class", classValue);
	}

}
