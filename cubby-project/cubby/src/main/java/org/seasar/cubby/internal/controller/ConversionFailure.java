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
package org.seasar.cubby.internal.controller;

import org.seasar.cubby.action.FieldInfo;
import org.seasar.cubby.validator.MessageInfo;

/**
 * 型変換の失敗を表すクラスです。
 * 
 * @author baba
 */
public class ConversionFailure {

	/** フィールド名。 */
	private final String fieldName;

	/** メッセージ情報。 */
	private final MessageInfo messageInfo;

	/** フィールド情報。 */
	private final FieldInfo[] fieldInfos;

	/**
	 * インスタンス化します。
	 * 
	 * @param fieldName
	 *            フィールド名
	 * @param messageInfo
	 *            メッセージ情報
	 * @param fieldInfos
	 *            フィールド情報
	 */
	public ConversionFailure(final String fieldName,
			final MessageInfo messageInfo, final FieldInfo... fieldInfos) {
		this.fieldName = fieldName;
		this.messageInfo = messageInfo;
		this.fieldInfos = fieldInfos;
	}

	/**
	 * フィールド名を取得します。
	 * 
	 * @return フィールド名
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * メッセージ情報を取得します。
	 * 
	 * @return メッセージ情報
	 */
	public MessageInfo getMessageInfo() {
		return messageInfo;
	}

	/**
	 * フィールド情報を取得します。
	 * 
	 * @return フィールド情報
	 */
	public FieldInfo[] getFieldInfos() {
		return fieldInfos;
	}

}
