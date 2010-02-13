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

package org.seasar.cubby.spi.beans;

import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ProviderFactory;

/**
 * {@link BeanDesc} のファクトリです。
 * 
 * @author baba
 */
public class BeanDescFactory {

	/**
	 * 指定されたクラスの {@link BeanDesc} を返します。
	 * 
	 * @param clazz
	 *            対象となるクラス
	 * @return 指定されたクラスの {@link BeanDesc}
	 */
	public static BeanDesc getBeanDesc(final Class<?> clazz) {
		final BeanDescProvider provider = ProviderFactory
				.get(BeanDescProvider.class);
		return provider.getBeanDesc(clazz);
	}

}
