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
package org.seasar.cubby.util;

import static org.seasar.cubby.CubbyConstants.ATTR_MESSAGES_RESOURCE_BUNDLE;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.ServletRequest;

import org.seasar.cubby.internal.controller.ThreadContext;

/**
 * メッセージリソースを取得するユーティリティクラスです。
 * 
 * @author agata
 */
public class Messages {

	/**
	 * 指定されたメッセージリソースから指定されたキーの値を取得し、置換処理を行ったメッセージを取得します。
	 * 
	 * @param resource
	 *            メッセージリソース
	 * @param key
	 *            メッセージキー
	 * @param args
	 *            置換文字列
	 * @return 置換処理後のメッセージ
	 */
	public static String getText(final ResourceBundle resource,
			final String key, final Object... args) {
		try {
			final String text = resource.getString(key);
			return MessageFormat.format(text, args);
		} catch (final MissingResourceException e) {
			return key;
		}
	}

	/**
	 * メッセージリソース（messages.properties）から指定されたキーの値を取得し、置換処理を行ったメッセージを取得します。
	 * 
	 * <pre>
	 * msg.sample2=メッセージ中に置換文字列を使用できます（引数1={0}, 引数2={1}）。
	 * 
	 * // 「メッセージ中に置換文字列を使用できます（引数1=foo, 引数2=bar）。」
	 * String message = Messages.getText(&quot;msg.sample2&quot;, &quot;foo&quot;, &quot;bar&quot;);
	 * </pre>
	 * 
	 * @see Messages#getText(ResourceBundle, String, Object...)
	 * @param key
	 *            メッセージキー
	 * @param args
	 *            置換文字列
	 * @return 置換処理後のメッセージ
	 */
	public static String getText(final String key, final Object... args) {
		final ThreadContext currentContext = ThreadContext.getCurrentContext();
		final ServletRequest request = currentContext.getRequest();
		final ResourceBundle bundle = (ResourceBundle) request
				.getAttribute(ATTR_MESSAGES_RESOURCE_BUNDLE);
		return getText(bundle, key, args);
	}

}
