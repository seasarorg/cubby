package org.seasar.cubby.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Url;

public class CubbyUtils {

	@SuppressWarnings("unchecked")
	public static String getActionClassName(Class c) {
		String name = c.getName();
		name = StringUtils.left(name, "$");
		String actionName = StringUtils.toFirstLower(name.replaceAll("(.*[.])([^.]+)(Action$)", "$2"));
		if (c.getAnnotation(Url.class) != null) {
			actionName = ((Url)c.getAnnotation(Url.class)).value();
		}
		return actionName;
	}

	@SuppressWarnings("unchecked")
	static String getActionMethodName(Method m) {
		String actionName = m.getName();
		if (m.getAnnotation(Url.class) != null) {
			actionName = ((Url)m.getAnnotation(Url.class)).value();
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
		if (value == null) {
			return 0;
		} else if (value.getClass().isArray()) {
			return ((Object[])value).length;
		} else if (value instanceof Collection) {
			return ((Collection<?>) value).size();
		} else {
			return 1;
		}
	}

	public static boolean isForwardResult(ActionResult result) {
		return result instanceof Forward;
	}

	public static boolean isRedirectResult(ActionResult result) {
		return result instanceof Redirect;
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
			Object[] values = (Object[])value;
			return values[0];
		} else {
			return value;
		}
	}
}
