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
package org.seasar.cubby.converter.impl;

import org.seasar.cubby.controller.FormatPattern;
import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;

/**
 * 変換のヘルパクラスの実装。
 * 
 * @author baba
 * @since 1.1.0
 */
public class ConversionHelperImpl implements ConversionHelper {

	/**
	 * {@inheritDoc}
	 */
	public FormatPattern getFormatPattern() {
		final Container container = ProviderFactory
				.get(ContainerProvider.class).getContainer();
		final FormatPattern formatPattern = container
				.lookup(FormatPattern.class);
		return formatPattern;
	}

}
