package org.seasar.cubby.util;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.annotation.Url;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.results.Forward;
import org.seasar.cubby.controller.results.Redirect;

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
		return m.getReturnType().isAssignableFrom(ActionResult.class)
				&& m.getParameterTypes().length == 0;
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
	
	public static boolean isControllerClass(Class c) {
		return ClassUtils.isSubClass(Controller.class, c);
	}


}
