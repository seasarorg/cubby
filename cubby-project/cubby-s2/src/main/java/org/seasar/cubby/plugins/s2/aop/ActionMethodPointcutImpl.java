/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.cubby.plugins.s2.aop;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.seasar.cubby.util.ActionUtils;
import org.seasar.framework.aop.Pointcut;
import org.seasar.framework.aop.impl.PointcutImpl;
import org.seasar.framework.exception.EmptyRuntimeException;

/**
 * アクションメソッドに適用される {@link Pointcut}。
 * 
 * @author baba
 * @since 2.0.0
 */
public class ActionMethodPointcutImpl extends PointcutImpl implements
		Serializable {

	/** シリアルバージョン UID。 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link ActionMethodPointcutImpl}を作成します。
	 * 
	 * @param targetClass
	 *            対象のクラス
	 * @throws EmptyRuntimeException
	 *             対象のクラスが <code>null</code> の場合
	 */
	public ActionMethodPointcutImpl(final Class<?> targetClass)
			throws EmptyRuntimeException {
		super(targetClass);
	}

	/**
	 * {@link ActionMethodPointcutImpl}を作成します。
	 * 
	 * @param methodNames
	 *            メソッド名
	 * @throws EmptyRuntimeException
	 *             メソッド名が <code>null</code> または空文字の場合
	 */
	public ActionMethodPointcutImpl(final String[] methodNames)
			throws EmptyRuntimeException {
		super(methodNames);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 対象メソッドがアクションメソッドでない場合は <code>false</code> を返します。
	 * そうでない場合はスーパークラスに処理を移譲します。
	 * </p>
	 */
	public boolean isApplied(final Method targetMethod) {
		if (!ActionUtils.isActionMethod(targetMethod)) {
			return false;
		}
		return super.isApplied(targetMethod);
	}

}
