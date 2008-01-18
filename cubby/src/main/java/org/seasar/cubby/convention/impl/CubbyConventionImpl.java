/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.convention.impl;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_CLASS_NAME;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.controller.impl.ActionDefImpl;
import org.seasar.cubby.convention.CubbyConvention;
import org.seasar.cubby.util.CubbyUtils;
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

	public ActionDef fromPathToActionDef(final HttpServletRequest request) {
		final String path = CubbyUtils.getPath(request);
		final List<String> tokens = new ArrayList<String>();
		for (final StringTokenizer tokenizer = new StringTokenizer(path, "/"); tokenizer
				.hasMoreTokens();) {
			final String token = tokenizer.nextToken();
			tokens.add(token);
		}
		if (tokens.isEmpty()) {
			return null;
		}

		final String methodName = tokens.remove(tokens.size() - 1);

		final String actionName = CubbyUtils.join(tokens.iterator(), '_')
				+ namingConvention.getActionSuffix();
		final ComponentDef componentDef = fromRequestOrActionNameToComponentDef(
				request, actionName);
		if (componentDef == null) {
			return null;
		}

		final Method method;
		try {
			method = ClassUtil.getMethod(componentDef.getComponentClass(),
					methodName, new Class[0]);
		} catch (final NoSuchMethodRuntimeException e) {
			return null;
		}

		final ActionDef actionDef = new ActionDefImpl(componentDef, method);

		return actionDef;
	}

	private ComponentDef fromRequestOrActionNameToComponentDef(
			final HttpServletRequest request, final String actionName) {
		ComponentDef componentDef = fromRequestToComponentDef(request);
		if (componentDef == null) {
			componentDef = fromActionNameToComponentDef(actionName);
		}
		return componentDef;
	}

	private ComponentDef fromRequestToComponentDef(
			final HttpServletRequest request) {
		final String actionClassName = (String) request
				.getAttribute(ATTR_ACTION_CLASS_NAME);
		if (actionClassName == null) {
			return null;
		}
		final Class<?> actionClass = ClassUtil.forName(actionClassName);
		if (!container.getRoot().hasComponentDef(actionClass)) {
			return null;
		}
		final ComponentDef componentDef = container.getRoot().getComponentDef(
				actionClass);
		return componentDef;
	}

	private ComponentDef fromActionNameToComponentDef(final String actionName) {
		if (!container.getRoot().hasComponentDef(actionName)) {
			return null;
		}
		final ComponentDef componentDef = container.getRoot().getComponentDef(
				actionName);
		return componentDef;
	}

}
