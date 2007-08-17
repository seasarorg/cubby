package org.seasar.cubby.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class CubbyFunctions {
	
	public static Boolean contains(Object c, Object value) {
		if (c instanceof Collection) {
			return _contains((Collection)c, value);
		} else if (c != null && c.getClass().isArray()) {
			return _contains(Arrays.asList((Object[])c), value);
		} else {
			return false;
		}
	}

	public static Boolean _contains(Collection c, Object value) {
		return c.contains(value);
	}

	public static Boolean containsKey(Map m, Object value) {
		return m.containsKey(value);
	}

	public static Boolean containsValue(Map m, Object value) {
		return m.containsValue(value);
	}

	public static String odd(Integer index, String classnames) {
		String[] c = classnames.split(",");
		return c[index % c.length];
	}

	public static String out(Object value) {
		return value == null ? "" : escapeHtml(value.toString());
	}

	public static String escapeHtml(Object value) {
		if (value == null) { return "";}
		String text;
		if (value instanceof String) {
			text = (String) value;
		} else {
			text = value.toString();
		}
		text = StringUtils.replace(text, "&", "&amp;");
		text = StringUtils.replace(text, "<", "&lt;");
		text = StringUtils.replace(text, ">", "&gt;");
		text = StringUtils.replace(text, "\"", "&quot;");
		text = StringUtils.replace(text, "'", "&#39;");
		return text;
	}

	public static String dateFormat(Object date, String pattern) {
		if (date instanceof Date) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(date);
		} else {
			return "";
		}
	}

}
