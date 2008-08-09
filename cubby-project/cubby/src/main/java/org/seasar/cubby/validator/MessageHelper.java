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
package org.seasar.cubby.validator;

/**
 * メッセージ取得のためのヘルパクラスです。
 * 
 * @author baba
 * @since 1.0.0
 */
public class MessageHelper {

	/**
	 * メッセージのキー。
	 */
	private final String messageKey;

	/**
	 * 初期化します。
	 * 
	 * @param messageKey
	 *            エラーメッセージのキー
	 */
	public MessageHelper(final String messageKey) {
		this.messageKey = messageKey;
	}

	/**
	 * メッセージのキーを元に、メッセージ情報を作成して取得します。
	 * 
	 * @param arguments
	 *            置換文字列
	 * @return メッセージ情報
	 */
	public MessageInfo createMessageInfo(final Object... arguments) {
		final MessageInfo messageInfo = new MessageInfo();
		messageInfo.setKey(this.messageKey);
		messageInfo.setArguments(arguments);
		return messageInfo;
	}

}
