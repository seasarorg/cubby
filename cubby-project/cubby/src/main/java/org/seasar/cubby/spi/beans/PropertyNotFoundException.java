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
package org.seasar.cubby.spi.beans;

import static org.seasar.cubby.internal.util.LogMessages.format;

/**
 * プロパティが見つからなかったことを表す例外です。
 * 
 * @author baba
 * @since 2.0.0
 */
public class PropertyNotFoundException extends RuntimeException {

	/** シリアルバージョン UID。 */
	private static final long serialVersionUID = 1L;

	/** 対象のクラス。 */
	private final Class<?> targetClass;

	/** プロパティ名。 */
	private final String propertyName;

	/**
	 * {@link PropertyNotFoundException}を返します。
	 * 
	 * @param targetClass
	 *            対象のクラス
	 * @param propertyName
	 *            プロパティ名
	 */
	public PropertyNotFoundException(final Class<?> targetClass,
			final String propertyName) {
		super(format("ECUB0052", targetClass.getName(), propertyName));
		this.targetClass = targetClass;
		this.propertyName = propertyName;
	}

	/**
	 * 対象のクラスを返します。
	 * 
	 * @return 対象のクラス
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