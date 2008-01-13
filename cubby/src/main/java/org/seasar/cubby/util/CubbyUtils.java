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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.framework.util.StringUtil;

public class CubbyUtils {

	private static Accept DEFAULT_ACCEPT_ANNOTATION;
	static {
		@Accept
		class AcceptDummy {
		}
		DEFAULT_ACCEPT_ANNOTATION = AcceptDummy.class.getAnnotation(Accept.class);
	}

	public static String getActionClassName(final Class<?> c) {
		final String actionName;
		final Path path = c.getAnnotation(Path.class);
		if (path != null && !StringUtil.isEmpty(path.value())) {
			actionName = path.value();
		} else {
			final String name = left(c.getSimpleName(), "$");
			actionName = toFirstLower(name.replaceAll(
					"(.*[.])*([^.]+)(Action$)", "$2"));
		}
		return actionName;
	}

	static String getActionMethodName(final Method m) {
		final String actionName;
		final Path path = m.getAnnotation(Path.class);
		if (path != null && !StringUtil.isEmpty(path.value())) {
			actionName = path.value();
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

	public static RequestMethod[] getAcceptableRequestMethods(
			final Class<?> c, final Method m) {
		Accept accept = m.getAnnotation(Accept.class);
		if (accept == null) {
			accept = c.getAnnotation(Accept.class);
			if (accept == null) {
				accept = DEFAULT_ACCEPT_ANNOTATION;
			}
		}
		return accept.value();
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
