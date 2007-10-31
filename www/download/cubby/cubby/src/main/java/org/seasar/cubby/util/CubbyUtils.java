package org.seasar.cubby.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Url;
import org.seasar.framework.util.StringUtil;

public class CubbyUtils {

	public static String getActionClassName(Class<?> c) {
		String name = left(c.getName(), "$");
		String actionName = toFirstLower(name.replaceAll(
				"(.*[.])([^.]+)(Action$)", "$2"));
		if (c.getAnnotation(Url.class) != null) {
			actionName = ((Url) c.getAnnotation(Url.class)).value();
		}
		return actionName;
	}

	static String getActionMethodName(Method m) {
		String actionName = m.getName();
		if (m.getAnnotation(Url.class) != null) {
			actionName = ((Url) m.getAnnotation(Url.class)).value();
		} else if ("index".equals(actionName)) {
			actionName = "";
		}
		return actionName;
	}

	public static String getActionUrl(Class<?> c, Method m) {
		String actionMethodName = getActionMethodName(m);
		if (actionMethodName.startsWith("/")) {
			return actionMethodName;
		} else {
			String actionName = CubbyUtils.getActionClassName(c);
			return "/" + actionName + "/" + actionMethodName;
		}
	}

	public static boolean isActionMethod(Method m) {
		return m.getReturnType().isAssignableFrom(ActionResult.class)
				&& m.getParameterTypes().length == 0;
	}

	public static int getObjectSize(Object value) {
		final int size;
		if (value == null) {
			size = 0;
		} else if (value.getClass().isArray()) {
			Object[] array = (Object[]) value;
			size = array.length;
		} else if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>) value;
			size = collection.size();
		} else {
			size = 1;
		}
		return size;
	}

	public static String getPath(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		return uri.substring(contextPath.length());
	}

	public static boolean isActionClass(Class<?> c) {
		return Action.class.isAssignableFrom(c);
	}

	public static Object getParamsValue(Map<String, Object> params, String key) {
		Object value = params.get(key);
		if (value == null) {
			return null;
		} else if (value.getClass().isArray()) {
			Object[] values = (Object[]) value;
			return values[0];
		} else {
			return value;
		}
	}

	static String toFirstLower(final String propertyName) {
		if (StringUtil.isEmpty(propertyName)) {
			throw new IllegalArgumentException("properyName is empty.");
		}
		final StringBuilder sb = new StringBuilder();
		sb.append(propertyName.substring(0, 1).toLowerCase());
		if (propertyName.length() > 1) {
			sb.append(propertyName.substring(1));
		}
		return sb.toString();
	}

	static String left(final String str, final String sep) {
		final int pos = str.indexOf(sep);
		if (pos != -1) {
			return str.substring(0, pos);
		}
		return str;
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
