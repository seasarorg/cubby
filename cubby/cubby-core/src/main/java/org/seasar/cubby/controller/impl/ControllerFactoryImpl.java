package org.seasar.cubby.controller.impl;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.ControllerFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class ControllerFactoryImpl implements ControllerFactory {

	public Controller createController(Method m) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		Class<?> declaringClass = m.getDeclaringClass();
		return (Controller) container.getComponent(declaringClass);
	}

	public Collection<Class> getContorollerClassList(Map<String, Object> params) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		ComponentDef[] defs = container.findAllComponentDefs(Controller.class);
		List<Class> contorllerClassList = new ArrayList<Class>(); 
		for (ComponentDef def : defs) {
			Class concreteClass = def.getComponentClass();
			contorllerClassList.add(concreteClass);
		}
		return contorllerClassList;
	}
}
