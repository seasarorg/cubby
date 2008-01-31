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

import static org.seasar.cubby.CubbyConstants.INTERNAL_FORWARD_DIRECTORY;

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

	private static final String INDEX_METHOD_NAME = "index";

	private static Accept DEFAULT_ACCEPT_ANNOTATION;
	static {
		@Accept
		class AcceptDummy {
		}
		DEFAULT_ACCEPT_ANNOTATION = AcceptDummy.class
				.getAnnotation(Accept.class);
	}

	public static String getActionName(final Class<?> actionClass) {
		final String actionName;
		final Path path = actionClass.getAnnotation(Path.class);
		if (path != null && !StringUtil.isEmpty(path.value())) {
			actionName = path.value();
		} else {
			final String name = left(actionClass.getSimpleName(), "$");
			actionName = toFirstLower(name.replaceAll(
					"(.*[.])*([^.]+)(Action$)", "$2"));
		}
		return actionName;
	}

	public static String getActionPath(final Class<?> actionClass,
			final Method method) {
		final String path;
		final String actionMethodName = getActionMethodName(method);
		if (actionMethodName.startsWith("/")) {
			return path = actionMethodName;
		} else {
			final String actionName = CubbyUtils.getActionName(actionClass);
			if ("/".equals(actionName)) {
				path = "/" + actionMethodName;
			} else {
				path = "/" + actionName + "/" + actionMethodName;
			}
		}
		return path;
	}

	static String getActionMethodName(final Method method) {
		final String actionName;
		final Path path = method.getAnnotation(Path.class);
		if (path != null && !StringUtil.isEmpty(path.value())) {
			actionName = path.value();
		} else {
			final String methodName = method.getName();
			if (INDEX_METHOD_NAME.equals(methodName)) {
				actionName = "";
			} else {
				actionName = methodName;
			}
		}
		return actionName;
	}

	public static RequestMethod[] getAcceptableRequestMethods(
			final Class<?> actionClass, final Method method) {
		Accept accept = method.getAnnotation(Accept.class);
		if (accept == null) {
			accept = actionClass.getAnnotation(Accept.class);
			if (accept == null) {
				accept = DEFAULT_ACCEPT_ANNOTATION;
			}
		}
		return accept.value();
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

	/**
	 * アクションクラスとメソッドから内部フォワードのパスへ変換します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param method
	 *            アクションメソッド
	 * @return 内部フォワードパス
	 */
	public static String getInternalForwardPath(
			final Class<? extends Action> actionClass, final String methodName) {
		return "/" + INTERNAL_FORWARD_DIRECTORY + "/"
				+ actionClass.getCanonicalName() + "/" + methodName;
	}

	public static boolean isActionClass(final Class<?> c) {
		return Action.class.isAssignableFrom(c);
	}

	public static boolean isActionMethod(final Method method) {
		return method.getReturnType().isAssignableFrom(ActionResult.class)
				&& method.getParameterTypes().length == 0;
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

	public static String replaceFirst(final String text, final String replace, final String with) {
		if (text == null || replace == null || with == null) {
			return text;
		}
		final int index = text.indexOf(replace);
		if (index == -1) {
			return text;
		}
		final StringBuilder builder = new StringBuilder(100);
		builder.append(text.substring(0, index));
		builder.append(with);
		builder.append(text.substring(index + replace.length()));
		return builder.toString();
	}

	public static String[] split2(final String text, final char delim) {
		if (text == null) {
			return null;
		}
		int index = text.indexOf(delim);
		if (index == -1) {
			return new String[] { text };
		}
		String[] tokens = new String[2];
		tokens[0] = text.substring(0, index);
		tokens[1] = text.substring(index + 1);
		return tokens;
	}

}
