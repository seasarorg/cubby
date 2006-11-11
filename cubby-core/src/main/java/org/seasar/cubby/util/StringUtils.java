package org.seasar.cubby.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String toFirstUpper(String propertyName) {
		if (isEmpty(propertyName)) {
			throw new IllegalArgumentException("property mame is empty.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(propertyName.substring(0, 1).toUpperCase());
		if (propertyName.length() > 1) {
			sb.append(propertyName.substring(1));
		}
		return sb.toString();
	}

	public static String lastSub(String str, String sep) {
		int pos = str.lastIndexOf(sep);
		if (pos == -1) {
			return str;
		} else if (pos == str.length()) {
			return str.substring(0, pos);
		} else {
			return str.substring(pos + 1);
		}
	}

	public static String toFirstLower(String propertyName) {
		if (isEmpty(propertyName)) {
			throw new IllegalArgumentException("properyName is empty.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(propertyName.substring(0, 1).toLowerCase());
		if (propertyName.length() > 1) {
			sb.append(propertyName.substring(1));
		}
		return sb.toString();
	}

	public static String trimRight(String str, String trimStr) {
		int pos = str.lastIndexOf(trimStr);
		if (pos == str.length() - trimStr.length()) {
			return str.substring(0, pos);
		} else {
			return str;
		}
	}

	public static String left(String str, String sep) {
		int pos = str.indexOf(sep);
		if (pos != -1) {
			return str.substring(0, pos);
		}
		return str;
	}

	public static Object removeEmpty(Object[] source) {
		int emptyCount = 0;
		for (Object s : source) {
			if (s instanceof String && StringUtils.isEmpty((String)s)) {
				emptyCount++;
			}
		}
		if (emptyCount == 0) {
			return source;
		}
		List<String> result = new ArrayList<String>();
		for (Object s : source) {
			if (s instanceof String && StringUtils.isNotEmpty((String)s)) {
				result.add((String)s);
			}
		}
		return result.toArray(new String[result.size()]);
	}
}
