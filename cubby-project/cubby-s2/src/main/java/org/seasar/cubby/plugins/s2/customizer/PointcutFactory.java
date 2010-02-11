/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.plugins.s2.customizer;

import org.seasar.cubby.plugins.s2.aop.ActionMethodPointcutImpl;
import org.seasar.framework.aop.Pointcut;
import org.seasar.framework.util.StringUtil;

/**
 * {@link org.seasar.framework.aop.Pointcut ポイントカット}を構築するためのファクトリクラスです。
 * 
 * @author baba
 */
class PointcutFactory {

	/**
	 * 指定されたポイントカットを表す文字列から、 {@link org.seasar.framework.aop.Pointcut ポイントカット}
	 * を構築して返します。
	 * 
	 * @param pointcutStr
	 *            ポイントカットを表す文字列
	 * @return ポイントカット
	 */
	public static Pointcut createPointcut(final String pointcutStr) {
		if (!StringUtil.isEmpty(pointcutStr)) {
			final String[] methodNames = StringUtil.split(pointcutStr, ", \n");
			return new ActionMethodPointcutImpl(methodNames);
		}
		return null;
	}

	/**
	 * 指定された{@link Class クラス}から、 {@link org.seasar.framework.aop.Pointcut
	 * ポイントカット}を構築して返します。
	 * 
	 * @param clazz
	 *            クラス
	 * @return ポイントカット
	 */
	public static Pointcut createPointcut(final Class<?> clazz) {
		return new ActionMethodPointcutImpl(clazz);
	}

}
