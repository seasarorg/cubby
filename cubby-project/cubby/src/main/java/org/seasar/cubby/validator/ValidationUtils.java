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
package org.seasar.cubby.validator;

import java.lang.reflect.Method;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Validation;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * 
 * @author baba
 * @since 1.1.0
 */
public class ValidationUtils {

	/**
	 * 指定されたメソッドを修飾する {@link Validation} を取得します。
	 * 
	 * @param method
	 *            メソッド
	 * @return {@link Validation}、修飾されていない場合は <code>null</code>
	 */
	public static Validation getValidation(final Method method) {
		return method.getAnnotation(Validation.class);
	}

	/**
	 * 実行しているアクションメソッドの入力検証ルールの集合を取得します。
	 * 
	 * @param action
	 *            アクション
	 * @param rulesPropertyName
	 *            入力検証ルールの集合が定義されたプロパティ名
	 * @return アクションメソッドの入力検証ルールの集合
	 */
	public static ValidationRules getValidationRules(final Action action,
			final String rulesPropertyName) {
		final BeanDesc beanDesc = BeanDescFactory
				.getBeanDesc(action.getClass());
		final PropertyDesc propertyDesc = beanDesc
				.getPropertyDesc(rulesPropertyName);
		final ValidationRules rules = (ValidationRules) propertyDesc
				.getValue(action);
		return rules;
	}

}
