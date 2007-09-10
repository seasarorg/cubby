package org.seasar.cubby.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Deprecated
public class StringUtils {

	public static boolean isEmpty(final String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(final String str) {
		return !isEmpty(str);
	}

	public static String toFirstUpper(final String propertyName) {
		if (isEmpty(propertyName)) {
			throw new IllegalArgumentException("property mame is empty.");
		}
		final StringBuilder sb = new StringBuilder();
		sb.append(propertyName.substring(0, 1).toUpperCase());
		if (propertyName.length() > 1) {
			sb.append(propertyName.substring(1));
		}
		return sb.toString();
	}

	public static String lastSub(final String str, final String sep) {
		final int pos = str.lastIndexOf(sep);
		if (pos == -1) {
			return str;
		} else if (pos == str.length()) {
			return str.substring(0, pos);
		} else {
			return str.substring(pos + 1);
		}
	}

	public static String toFirstLower(final String propertyName) {
		if (isEmpty(propertyName)) {
			throw new IllegalArgumentException("properyName is empty.");
		}
		final StringBuilder sb = new StringBuilder();
		sb.append(propertyName.substring(0, 1).toLowerCase());
		if (propertyName.length() > 1) {
			sb.append(propertyName.substring(1));
		}
		return sb.toString();
	}

	public static String trimRight(final String str, final String trimStr) {
		final int pos = str.lastIndexOf(trimStr);
		if (pos == str.length() - trimStr.length()) {
			return str.substring(0, pos);
		} else {
			return str;
		}
	}

	public static String left(final String str, final String sep) {
		final int pos = str.indexOf(sep);
		if (pos != -1) {
			return str.substring(0, pos);
		}
		return str;
	}

	public static Object removeEmpty(final Object[] source) {
		int emptyCount = 0;
		for (final Object s : source) {
			if (s instanceof String && StringUtils.isEmpty((String) s)) {
				emptyCount++;
			}
		}
		if (emptyCount == 0) {
			return source;
		}
		final List<String> result = new ArrayList<String>();
		for (final Object s : source) {
			if (s instanceof String && StringUtils.isNotEmpty((String) s)) {
				result.add((String) s);
			}
		}
		return result.toArray(new String[result.size()]);
	}

	public static String join(final Iterator<?> iterator, final char separator) {
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return "";
		}
		final Object first = iterator.next();
		if (!iterator.hasNext()) {
	        return first != null ? first.toString() : "";
		}
		final StringBuffer buf = new StringBuffer(256);
		if (first != null) {
			buf.append(first);
		}
		while (iterator.hasNext()) {
			buf.append(separator);
			final Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}
		return buf.toString();
	}
}
