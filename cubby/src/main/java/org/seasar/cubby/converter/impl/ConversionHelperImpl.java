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
package org.seasar.cubby.converter.impl;

import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.internal.util.ServiceFactory;

/**
 * 変換のヘルパクラスの実装。
 * 
 * @author baba
 * @since 1.1.0
 */
public class ConversionHelperImpl implements ConversionHelper {

	/** 日付や時刻のフォーマットパターンを保持するクラス。 */
	private FormatPattern formatPattern;

	// /**
	// * 設定情報を設定します。
	// *
	// * @param cubbyConfiguration
	// * 設定情報
	// */
	// public void setCubbyConfiguration(
	// final CubbyConfiguration cubbyConfiguration) {
	// this.formatPattern = cubbyConfiguration.getFormatPattern();
	// }

	public ConversionHelperImpl() {
		// final Container container = ContainerFactory.getContainer();
		// final CubbyConfiguration configuration = container
		// .lookup(CubbyConfiguration.class);
		// this.formatPattern = configuration.getFormatPattern();
		this.formatPattern = ServiceFactory.getProvider(FormatPattern.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public FormatPattern getFormatPattern() {
		return formatPattern;
	}

}
