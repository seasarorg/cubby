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
package org.seasar.cubby.beans;

import static org.seasar.cubby.util.LoggerMessages.format;

/**
 * プロパティの値の設定に失敗したときにスローされる例外です。
 * 
 * @author baba
 * @since 2.0.0
 */
public class IllegalPropertyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Class<?> targetClass;

	private String propertyName;

	/**
	 * {@link IllegalPropertyException}を作成します。
	 * 
	 * @param targetClass
	 * @param propertyName
	 * @param cause
	 */
	public IllegalPropertyException(Class<?> targetClass, String propertyName,
			Throwable cause) {
		super(format("ECUB0051", targetClass.getName(), propertyName, cause),
				cause);
		this.targetClass = targetClass;
		this.propertyName = propertyName;
	}

	/**
	 * ターゲットの{@link Class}を返します。
	 * 
	 * @return ターゲットの{@link Class}
	 */
	public Class<?> getTargetClass() {
		return targetClass;
	}

	/**
	 * プロパティ名を返します。
	 * 
	 * @return プロパティ名
	 */
	public String getPropertyName() {
		return propertyName;
	}
}