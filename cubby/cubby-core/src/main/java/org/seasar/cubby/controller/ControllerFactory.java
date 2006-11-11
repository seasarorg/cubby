package org.seasar.cubby.controller;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public interface ControllerFactory {

	Controller createController(Method m);

	Collection<Class> getContorollerClassList(Map<String, Object> params);

}
