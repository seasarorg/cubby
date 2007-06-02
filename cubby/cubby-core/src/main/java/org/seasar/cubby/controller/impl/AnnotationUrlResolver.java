package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class AnnotationUrlResolver extends DefaultUrlResolver {
	private static final Log LOG = LogFactory.getLog(AnnotationUrlResolver.class);

	@SuppressWarnings("unchecked")
	public void initialize() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("アクションメソッドを登録します。");
		}
		S2Container container = SingletonS2ContainerFactory.getContainer();
		ComponentDef[] defs = container.findAllComponentDefs(Action.class);
		for (ComponentDef def : defs) {
			Class concreteClass = def.getComponentClass();
			if (CubbyUtils.isActionClass(concreteClass)) {
				initAction(concreteClass);
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("アクションメソッドを登録しました。");
		}
	}

	private void initAction(Class<? extends Action> c) {
		for (Method m : c.getMethods()) {
			if (CubbyUtils.isActionMethod(m)) {
				String actionFullName = CubbyUtils.getActionUrl(c, m);
				add(actionFullName, c, m.getName());
			}
		}
	}
}
