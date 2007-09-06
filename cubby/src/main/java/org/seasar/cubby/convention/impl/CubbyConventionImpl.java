package org.seasar.cubby.convention.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.controller.impl.ActionDefImpl;
import org.seasar.cubby.convention.CubbyConvention;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.exception.NoSuchMethodRuntimeException;
import org.seasar.framework.util.ClassUtil;

public class CubbyConventionImpl implements CubbyConvention {

	private S2Container container;

	private NamingConvention namingConvention;

	public void setContainer(final S2Container container) {
		this.container = container;
	}

	public void setNamingConvention(final NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
	}

	public ActionDef fromPathToActionDef(final String path) {
		List<String> tokens = new ArrayList<String>();
		for (StringTokenizer tokenizer = new StringTokenizer(path, "/"); tokenizer
				.hasMoreTokens();) {
			String token = tokenizer.nextToken();
			tokens.add(token);
		}
		if (tokens.isEmpty()) {
			return null;
		}

		String methodName = tokens.remove(tokens.size() - 1);

		StringUtils.join(tokens.iterator(), '_');
		final String actionName = StringUtils.join(tokens.iterator(), '_')
				+ namingConvention.getActionSuffix();

		if (!container.getRoot().hasComponentDef(actionName)) {
			return null;
			// Cubbyのパスじゃない
		}
		final ComponentDef componentDef = container.getRoot().getComponentDef(
				actionName);
		final Method method;
		try {
			method = ClassUtil.getMethod(componentDef
					.getComponentClass(), methodName, new Class[0]);
		} catch (NoSuchMethodRuntimeException e) {
			return null;
		}

		ActionDef actionDef = new ActionDefImpl(componentDef, method);

		return actionDef;
	}

}
