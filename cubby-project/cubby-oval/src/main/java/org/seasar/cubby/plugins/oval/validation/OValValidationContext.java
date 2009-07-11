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
package org.seasar.cubby.plugins.oval.validation;

/**
 * スレッドに関連付けられる入力検証の状態です。
 * 
 * @author baba
 */
public class OValValidationContext {

	/** {@link OValValidationContext} を保持する {@link ThreadLocal} */
	private static final ThreadLocal<OValValidationContext> context = new ThreadLocal<OValValidationContext>() {

		@Override
		protected OValValidationContext initialValue() {
			return new OValValidationContext();
		}

	};

	/** メッセージキーのプリフィックス。 */
	private String resourceKeyPrefix;

	/**
	 * インスタンス化禁止。
	 */
	private OValValidationContext() {
	}

	/**
	 * スレッドに関連付けられた {@link OValValidationContext} を取得します。
	 * 
	 * @return スレッドに関連付けられた {@link OValValidationContext}
	 */
	public static OValValidationContext get() {
		return context.get();
	}

	/**
	 * スレッドに関連付けられた {@link OValValidationContext} を削除します。
	 */
	public static void remove() {
		context.remove();
	}

	/**
	 * メッセージキーのプリフィックスを取得します。
	 * 
	 * @return メッセージキーのプリフィックス
	 */
	public String getResourceKeyPrefix() {
		return resourceKeyPrefix;
	}

	/**
	 * メッセージキーのプリフィックスを設定します。
	 * 
	 * @param resourceKeyPrefix
	 *            メッセージキーのプリフィックス
	 */
	public void setResourceKeyPrefix(String resourceKeyPrefix) {
		this.resourceKeyPrefix = resourceKeyPrefix;
	}

}
