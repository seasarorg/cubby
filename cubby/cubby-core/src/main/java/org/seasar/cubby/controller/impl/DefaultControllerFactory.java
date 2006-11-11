package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;

import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.ControllerFactory;
import org.seasar.cubby.util.ClassFinder;
import org.seasar.cubby.util.ClassFinderImpl;
import org.seasar.cubby.util.ClassUtils;

public class DefaultControllerFactory implements ControllerFactory {

	private ClassFinder contrllerClassFinder = new ClassFinderImpl();

	public Controller createController(Method m) {
		return (Controller) ClassUtils.newInstance(m.getDeclaringClass());
	}

	public Collection<Class> getContorollerClassList(Map<String, Object> params) {
		// find controller class
		ServletContext context = (ServletContext) params.get("context");
		contrllerClassFinder.find(context, true, ".*Controller$");
		return contrllerClassFinder.getClassCollection();
	}
}
