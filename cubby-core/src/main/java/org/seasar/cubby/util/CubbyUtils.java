package org.seasar.cubby.util;

import java.lang.reflect.Method;
import java.util.Collection;

import org.seasar.cubby.annotation.Url;

public class CubbyUtils {

	@SuppressWarnings("unchecked")
	public static String getControllerName(Class c) {
		String name = c.getName();
		name = StringUtils.left(name, "$");
		String controllerName = StringUtils.toFirstLower(name.replaceAll("(.*[.])([^.]+)(Controller$)", "$2"));
		if (c.getAnnotation(Url.class) != null) {
			controllerName = ((Url)c.getAnnotation(Url.class)).value();
		}
		return controllerName;
	}

	@SuppressWarnings("unchecked")
	public static String getActionName(Method m) {
		String actionName = m.getName();
		if (m.getAnnotation(Url.class) != null) {
			actionName = ((Url)m.getAnnotation(Url.class)).value();
		}
		return actionName;
	}

	public static String getActionFullName(Class c, Method m) {
		String controllerName = CubbyUtils.getControllerName(c);
		if (StringUtils.isEmpty(controllerName)) {
			return "/" + getActionName(m);
		} else {
			return "/" + controllerName + "/" + getActionName(m);
		}
	}

	public static boolean isActionMethod(Method m) {
		return m.getReturnType() == String.class
				&& m.getParameterTypes().length == 0
				&& !m.getName().equals("toString");
	}

	public static int getObjectSize(Object value) {
		if (value == null) {
			return 0;
		} else if (value.getClass().isArray()) {
			return ((Object[])value).length;
		} else if (value instanceof Collection) {
			return ((Collection)value).size();
		} else {
			return 1;
		}
	}
}
