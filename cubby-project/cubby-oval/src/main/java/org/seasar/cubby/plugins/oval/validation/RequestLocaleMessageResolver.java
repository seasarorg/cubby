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

import static org.seasar.cubby.CubbyConstants.ATTR_MESSAGES_RESOURCE_BUNDLE;

import java.util.ResourceBundle;

import javax.servlet.ServletRequest;

import net.sf.oval.localization.message.MessageResolver;

import org.seasar.cubby.internal.controller.ThreadContext;

/**
 * 現在の実行スレッドに関連付けられた要求に対応するメッセージ用の {@link ResourceBundle} からメッセージを取得する
 * {@link MessageResolver} です。
 * 
 * @author baba
 */
public class RequestLocaleMessageResolver implements MessageResolver {

	/** このクラスのシングルトン。 */
	public static RequestLocaleMessageResolver INSTANCE = new RequestLocaleMessageResolver();

	/**
	 * {@inheritDoc}
	 * <p>
	 * 現在の実行スレッドに関連付けられた要求に対応するメッセージ用の {@link ResourceBundle} からメッセージを取得します。
	 * </p>
	 */
	public String getMessage(final String key) {
		final ThreadContext currentContext = ThreadContext.getCurrentContext();
		final ServletRequest request = currentContext.getRequest();
		final ResourceBundle bundle = (ResourceBundle) request
				.getAttribute(ATTR_MESSAGES_RESOURCE_BUNDLE);
		final String message = bundle.getString(key);
		return message;
	}

}
