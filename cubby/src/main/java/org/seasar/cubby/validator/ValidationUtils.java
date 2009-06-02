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
package org.seasar.cubby.validator;

import static org.seasar.cubby.internal.util.LogMessages.*;
import java.lang.reflect.Method;

import org.seasar.cubby.action.Validation;
import org.seasar.cubby.spi.beans.Attribute;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.BeanDescFactory;

/**
 * バリデーションのユーティリティクラスです。
 * 
 * @author baba
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
	 * @param attributeName
	 *            入力検証ルールの集合が定義された属性名
	 * @return アクションメソッドの入力検証ルールの集合
	 */
	public static ValidationRules getValidationRules(final Object action,
			final String attributeName) {
		final BeanDesc beanDesc = BeanDescFactory
				.getBeanDesc(action.getClass());
		final Attribute attribute;
		if (beanDesc.hasPropertyAttribute(attributeName)) {
			attribute = beanDesc.getPropertyAttribute(attributeName);
		} else if (beanDesc.hasFieldAttribute(attributeName)) {
			attribute = beanDesc.getFieldAttribute(attributeName);
		} else {
			throw new IllegalStateException(format("ECUB0113", action,
					attributeName));
		}
		if (!ValidationRules.class.isAssignableFrom(attribute.getType())) {
			throw new IllegalStateException(format("ECUB0114", action,
					attributeName, ValidationRules.class.getName()));
		}
		final ValidationRules rules = (ValidationRules) attribute
				.getValue(action);
		return rules;
	}

}
