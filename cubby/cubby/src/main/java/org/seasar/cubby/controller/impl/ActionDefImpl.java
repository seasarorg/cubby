package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;

import org.seasar.cubby.controller.ActionDef;
import org.seasar.framework.container.ComponentDef;

/**
 * 
 * @author baba
 *
 */
public class ActionDefImpl implements ActionDef {

	private final ComponentDef componentDef;

	private final Method method;

	public ActionDefImpl(final ComponentDef componentDef, final Method method) {
		this.componentDef = componentDef;
		this.method = method;
	}

	public ComponentDef getComponentDef() {
		return componentDef;
	}

	public Method getMethod() {
		return method;
	}

}
