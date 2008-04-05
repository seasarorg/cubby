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
package org.seasar.cubby.controller.impl;

import static org.seasar.cubby.CubbyConstants.INTERNAL_FORWARD_DIRECTORY;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.exception.NoSuchMethodRuntimeException;
import org.seasar.framework.util.ClassUtil;

/**
 * アクションの定義を組み立てるビルダです。
 * 
 * @author baba
 * @since 1.0.0
 */
class ActionDefBuilder {

	/** 内部フォワードパスのパターン。 */
	private static final Pattern INTERNAL_FORWARD_PATH_PATTERN = Pattern
			.compile("^/" + INTERNAL_FORWARD_DIRECTORY + "/(.*)/(.*)$");

	/** 空のパラメータ型。 */
	private static final Class<?>[] EMPTY_PARAMETER_TYPES = new Class[0];

	/** コンテナ。 */
	private S2Container container;

	/**
	 * インスタンス化します。
	 * 
	 * @param container
	 *            コンテナ
	 */
	public ActionDefBuilder(final S2Container container) {
		this.container = container;
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionDef build(final HttpServletRequest request) {
		final String path = CubbyUtils.getPath(request);
		final Matcher matcher = INTERNAL_FORWARD_PATH_PATTERN.matcher(path);
		if (!matcher.matches()) {
			return null;
		}
		final String actionClassName = matcher.group(1);
		final Class<? extends Action> actionClass;
		try {
			actionClass = actionClassForName(actionClassName);
		} catch (final ClassNotFoundRuntimeException e) {
			return null;
		}
		if (!container.getRoot().hasComponentDef(actionClass)) {
			return null;
		}
		final ComponentDef componentDef = container.getRoot().getComponentDef(
				actionClass);

		final String methodName = matcher.group(2);
		final Method method;
		try {
			method = ClassUtil.getMethod(componentDef.getComponentClass(),
					methodName, EMPTY_PARAMETER_TYPES);
		} catch (final NoSuchMethodRuntimeException e) {
			return null;
		}

		final ActionDef actionDef = new ActionDef((Action) componentDef
				.getComponent(), actionClass, method);

		return actionDef;
	}

	@SuppressWarnings("unchecked")
	private static Class<? extends Action> actionClassForName(
			final String actionClassName) {
		return (Class<? extends Action>) ClassUtil.forName(actionClassName);
	}

}
