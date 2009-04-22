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
package org.seasar.cubby.converter;

import org.seasar.cubby.validator.MessageInfo;

/**
 * 型変換に失敗した場合にスローする例外です。
 * 
 * @author baba
 * @since 2.0.0
 */
public class ConversionException extends Exception {

	/** シリアルバージョンUID。 */
	private static final long serialVersionUID = 1L;

	/** メッセージ情報。 */
	private final MessageInfo messageInfo;

	/**
	 * 新規例外を構築します。
	 * 
	 * @param messageInfo
	 *            メッセージ情報
	 */
	public ConversionException(final MessageInfo messageInfo) {
		this.messageInfo = messageInfo;
	}

	/**
	 * 新規例外を構築します。
	 * 
	 * @param messageInfo
	 *            メッセージ情報
	 * @param cause
	 *            原因
	 */
	public ConversionException(final MessageInfo messageInfo,
			final Throwable cause) {
		super(cause);
		this.messageInfo = messageInfo;
	}

	/**
	 * メッセージ情報を取得します。
	 * 
	 * @return メッセージ情報
	 */
	public MessageInfo getMessageInfo() {
		return messageInfo;
	}

}
