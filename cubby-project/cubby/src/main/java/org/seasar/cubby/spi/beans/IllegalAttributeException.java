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
package org.seasar.cubby.spi.beans;

import static org.seasar.cubby.internal.util.LogMessages.format;

/**
 * 属性の操作に失敗したときにスローされる例外です。
 * 
 * @author baba
 */
public class IllegalAttributeException extends RuntimeException {

	/** シリアルバージョン UID。 */
	private static final long serialVersionUID = 1L;

	/** 対象のクラス。 */
	private final Class<?> targetClass;

	/** 属性の名前。 */
	private final String name;

	/**
	 * {@link IllegalAttributeException} を作成します。
	 * 
	 * @param targetClass
	 *            対象のクラス
	 * @param name
	 *            属性の名前
	 * @param cause
	 *            属性の操作に失敗した理由となる例外
	 */
	public IllegalAttributeException(final Class<?> targetClass,
			final String name, final Throwable cause) {
		super(format("ECUB0051", targetClass.getName(), name, cause), cause);
		this.targetClass = targetClass;
		this.name = name;
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
	 * 属性名を返します。
	 * 
	 * @return 属性名
	 */
	public String getName() {
		return name;
	}
}
