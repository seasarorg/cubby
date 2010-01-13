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
package org.seasar.cubby.action;

import java.util.Arrays;

import org.seasar.cubby.util.Messages;

/**
 * メッセージ情報です。
 * 
 * @author baba
 */
public class MessageInfo {

	/** {@link Messages} からメッセージを取得するためのキー。 */
	private String key;

	/** メッセージの置換パターンを置き換えるオブジェクトからなる配列。 */
	private Object[] arguments;

	/**
	 * {@link Messages} からメッセージを取得するためのキーを取得します。
	 * 
	 * @return キー
	 */
	public String getKey() {
		return key;
	}

	/**
	 * {@link Messages} からメッセージを取得するためのキーを設定します。
	 * 
	 * @param key
	 *            キー
	 */
	public void setKey(final String key) {
		this.key = key;
	}

	/**
	 * メッセージの置換パターンを置き換えるオブジェクトからなる配列を取得します。
	 * 
	 * @return 置換文字列の配列
	 */
	public Object[] getArguments() {
		if (arguments == null) {
			return null;
		}
		return arguments.clone();
	}

	/**
	 * メッセージの置換パターンを置き換えるオブジェクトからなる配列を取得します。
	 * 
	 * @param arguments
	 *            置換文字列
	 */
	public void setArguments(final Object... arguments) {
		final Object[] copyArguments = new Object[arguments.length];
		System.arraycopy(arguments, 0, copyArguments, 0, arguments.length);
		this.arguments = copyArguments;
	}

	/**
	 * メッセージ文字列に変換します。
	 * 
	 * @param fieldNameKey
	 *            フィールド名のリソースキー
	 * @return メッセージ文字列
	 */
	public String toMessage(final String fieldNameKey) {
		final Object[] args;
		if (fieldNameKey != null) {
			if (this.arguments != null) {
				args = new Object[this.arguments.length + 1];
				final String paramNameText = Messages.getText(fieldNameKey);
				args[0] = paramNameText;
				System.arraycopy(this.arguments, 0, args, 1,
						this.arguments.length);
			} else {
				args = new Object[]{Messages.getText(fieldNameKey)};
			}
		} else {
			args = this.arguments;
		}
		return Messages.getText(key, args);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("[key=");
		builder.append(key);
		builder.append(",arguments=");
		builder.append(Arrays.deepToString(arguments));
		builder.append("]");
		return builder.toString();
	}

}
