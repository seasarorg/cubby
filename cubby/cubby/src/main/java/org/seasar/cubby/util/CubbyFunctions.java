/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.seasar.framework.util.StringUtil;

public class CubbyFunctions {

	public static Boolean contains(Object c, Object value) {
		if (c instanceof Collection) {
			return _contains((Collection<?>) c, value);
		} else if (c != null && c.getClass().isArray()) {
			return _contains(Arrays.asList((Object[]) c), value);
		} else {
			return false;
		}
	}

	public static Boolean _contains(Collection<?> c, Object value) {
		return c.contains(value);
	}

	public static Boolean containsKey(Map<?, ?> m, Object value) {
		return m.containsKey(value);
	}

	public static Boolean containsValue(Map<?, ?> m, Object value) {
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
		if (value == null) {
			return "";
		}
		String text;
		if (value instanceof String) {
			text = (String) value;
		} else {
			text = value.toString();
		}
		text = StringUtil.replace(text, "&", "&amp;");
		text = StringUtil.replace(text, "<", "&lt;");
		text = StringUtil.replace(text, ">", "&gt;");
		text = StringUtil.replace(text, "\"", "&quot;");
		text = StringUtil.replace(text, "'", "&#39;");
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
