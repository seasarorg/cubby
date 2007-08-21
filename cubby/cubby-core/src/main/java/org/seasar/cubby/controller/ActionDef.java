package org.seasar.cubby.controller;

import java.lang.reflect.Method;

import org.seasar.framework.container.ComponentDef;

public interface ActionDef {

	ComponentDef getComponentDef();

	Method getMethod();

}
