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
package org.seasar.cubby.controller.impl;

import static org.seasar.cubby.CubbyConstants.RES_MESSAGES;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.internal.util.ResourceBundleMap;

/**
 * メッセージの振る舞いのデフォルト実装です。
 * 
 * @author baba
 * @since 1.0.3
 */
public class DefaultMessagesBehaviour implements MessagesBehaviour {

	/** リソースバンドル、完全指定されたクラス名の基底名。 */
	private String baseName = RES_MESSAGES;

	/**
	 * リソースバンドル、完全指定されたクラス名の基底名を取得します。
	 * 
	 * @return リソースバンドル、完全指定されたクラス名の基底名
	 */
	public String getBaseName() {
		return baseName;
	}

	/**
	 * リソースバンドル、完全指定されたクラス名の基底名を設定します。
	 * 
	 * @param baseName
	 *            リソースバンドル、完全指定されたクラス名の基底名
	 */
	public void setBaseName(final String baseName) {
		this.baseName = baseName;
	}

	/**
	 * {@inheritDoc}
	 */
	public ResourceBundle getBundle(final Locale locale) {
		final ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		final ResourceBundle bundle = ResourceBundle.getBundle(baseName,
				(locale == null ? Locale.getDefault() : locale), classLoader);
		return bundle;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> toMap(final ResourceBundle bundle) {
		return new ResourceBundleMap(bundle);
	}

}
