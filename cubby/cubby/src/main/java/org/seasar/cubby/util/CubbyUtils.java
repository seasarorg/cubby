package org.seasar.cubby.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Url;
import org.seasar.framework.util.StringUtil;

public class CubbyUtils {

	private static Url DEFAULT_URL_ANNOTATION;
	static {
		@Url
		class UrlDummy {
		}
		DEFAULT_URL_ANNOTATION = UrlDummy.class.getAnnotation(Url.class);
	}

	public static String getActionClassName(final Class<?> c) {
		final String actionName;
		final Url url = c.getAnnotation(Url.class);
		if (url != null && !StringUtil.isEmpty(url.value())) {
			actionName = url.value();
		} else {
			final String name = left(c.getSimpleName(), "$");
			actionName = toFirstLower(name.replaceAll(
					"(.*[.])*([^.]+)(Action$)", "$2"));
		}
		return actionName;
	}

	static String getActionMethodName(final Method m) {
		final String actionName;
		final Url url = m.getAnnotation(Url.class);
		if (url != null && !StringUtil.isEmpty(url.value())) {
			actionName = url.value();
		} else {
			final String methodName = m.getName();
			if ("index".equals(methodName)) {
				actionName = "";
			} else {
				actionName = methodName;
			}
		}
		return actionName;
	}

	public static String getActionUrl(final Class<?> c, final Method m) {
		final String actionMethodName = getActionMethodName(m);
		if (actionMethodName.startsWith("/")) {
			return actionMethodName;
		} else {
			final String actionName = CubbyUtils.getActionClassName(c);
			if ("/".equals(actionName)) {
				return "/" + actionMethodName;
			} else {
				return "/" + actionName + "/" + actionMethodName;
			}
		}
	}

	public static Url.RequestMethod[] getAcceptableRequestMethods(
			final Class<?> c, final Method m) {
		Url url = m.getAnnotation(Url.class);
		if (url == null) {
			url = c.getAnnotation(Url.class);
			if (url == null) {
				url = DEFAULT_URL_ANNOTATION;
			}
		}
		return url.accept();
	}

	public static boolean isActionMethod(final Method m) {
		return m.getReturnType().isAssignableFrom(ActionResult.class)
				&& m.getParameterTypes().length == 0;
	}

	public static int getObjectSize(final Object value) {
		final int size;
		if (value == null) {
			size = 0;
		} else if (value.getClass().isArray()) {
			final Object[] array = (Object[]) value;
			size = array.length;
		} else if (value instanceof Collection) {
			final Collection<?> collection = (Collection<?>) value;
			size = collection.size();
		} else {
			size = 1;
		}
		return size;
	}

	public static String getPath(final HttpServletRequest request) {
		final String uri = request.getRequestURI();
		final String contextPath = request.getContextPath();
		final String path;
		if ("/".equals(contextPath)) {
			path = uri;
		} else {
			path = uri.substring(contextPath.length());
		}
		return path;
	}

	public static boolean isActionClass(final Class<?> c) {
		return Action.class.isAssignableFrom(c);
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
