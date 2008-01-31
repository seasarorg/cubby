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

import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.controller.ActionDefBuilder;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.exception.NoSuchMethodRuntimeException;
import org.seasar.framework.util.ClassUtil;

/**
 * アクションの定義を組み立てるビルダの実装です。
 * 
 * @author baba
 */
public class ActionDefBuilderImpl implements ActionDefBuilder {

	/** 内部フォワードパスのパターン */
	private static final Pattern INTERNAL_FORWARD_PATH_PATTERN = Pattern
			.compile("^/" + INTERNAL_FORWARD_DIRECTORY + "/(.*)/(.*)$");

	/** コンテナ */
	private S2Container container;

	/**
	 * コンテナを設定します。
	 * 
	 * @param container
	 *            コンテナ
	 */
	public void setContainer(final S2Container container) {
		this.container = container;
	}

	/**
	 * リクエストから {@link ActionDef} 生成します。
	 * <p>
	 * リクエストに対応するアクションが定義されていない場合は <code>null</code> を返します。
	 * </p>
	 * 
	 * @param request
	 *            リクエスト
	 * @return アクション定義
	 */
	public ActionDef build(final HttpServletRequest request) {
		final String path = CubbyUtils.getPath(request);
		final Matcher matcher = INTERNAL_FORWARD_PATH_PATTERN.matcher(path);
		if (!matcher.matches()) {
			return null;
		}
		final String actionClassName = matcher.group(1);
		final Class<?> actionClass;
		try {
			actionClass = ClassUtil.forName(actionClassName);
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
					methodName, new Class[0]);
		} catch (final NoSuchMethodRuntimeException e) {
			return null;
		}

		final ActionDef actionDef = new ActionDefImpl(componentDef, method);

		return actionDef;
	}

}
