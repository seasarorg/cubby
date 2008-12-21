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

/**
 * Java Beans を扱うためのインターフェースです。
 * 
 * @author baba
 * @since 2.0.0
 */
public interface BeanDesc {

	/**
	 * {@link PropertyDesc}を持っているかどうかを返します。
	 * 
	 * @param propertyName
	 *            プロパティ名
	 * @return {@link PropertyDesc}を持っているかどうか
	 */
	boolean hasPropertyDesc(String propertyName);

	/**
	 * 指定されたプロパティの{@link PropertyDesc}を返します。
	 * 
	 * @param propertyName
	 *            プロパティ名
	 * @return {@link PropertyDesc}
	 * @throws PropertyNotFoundException
	 *             {@link PropertyDesc}が見つからない場合
	 */
	PropertyDesc getPropertyDesc(String propertyName)
			throws PropertyNotFoundException;

	/**
	 * すべてのプロパティの{@link PropertyDesc}を返します。
	 * 
	 * @return {@link PropertyDesc}の配列
	 */
	PropertyDesc[] getPropertyDescs();

}
