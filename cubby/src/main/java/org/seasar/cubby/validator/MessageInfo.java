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

import org.seasar.cubby.util.Messages;

/**
 * メッセージ情報です。
 * 
 * @author baba
 */
public class MessageInfo {

	/** {@link Messages}からメッセージを取得するためのキー。 */
	private String key;

	/** メッセージの置換パターンを置き換えるオブジェクトからなる配列。 */
	private Object[] arguments;

	/**
	 * {@link Messages}からメッセージを取得するためのキーを取得します。
	 * 
	 * @return キー
	 */
	public String getKey() {
		return key;
	}

	/**
	 * {@link Messages}からメッセージを取得するためのキーを設定します。
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
		this.arguments = arguments;
	}

	/**
	 * このメッセージ情報から文字列へ変換するためのビルダを取得します。
	 * 
	 * @return メッセージのビルダ
	 */
	public MessageBuilder builder() {
		final MessageBuilder builder = new MessageBuilder(key, arguments);
		return builder;
	}

	/**
	 * {@link Messages}を使用したメッセージを構築するためのビルダ。
	 * 
	 * @see Messages
	 * @author baba
	 */
	public class MessageBuilder {

		/** メッセージキー。 */
		private final String messageKey;

		/** 置換文字列。 */
		private final Object[] arguments;

		/** フィールド名のキー。 */
		private String fieldNameKey;

		/**
		 * 指定された情報からインスタンス化します。
		 * 
		 * @param messageKey
		 *            メッセージキー
		 * @param arguments
		 *            置換文字列
		 */
		private MessageBuilder(final String messageKey, final Object[] arguments) {
			this.messageKey = messageKey;
			this.arguments = arguments;
		}

		/**
		 * フィールド名のキーを設定します。
		 * 
		 * @param fieldNameKey
		 *            フィールド名のキー
		 * @return このオブジェクト
		 */
		public MessageBuilder fieldNameKey(final String fieldNameKey) {
			this.fieldNameKey = fieldNameKey;
			return this;
		}

		/**
		 * {@inheritDoc}
		 * <p>
		 * {@link Messages}から取得したメッセージをフォーマットした文字列を返します。
		 * </p>
		 */
		@Override
		public String toString() {
			final Object[] args;
			if (fieldNameKey != null) {
				args = new Object[this.arguments.length + 1];
				final String paramNameText = Messages.getText(fieldNameKey);
				args[0] = paramNameText;
				System.arraycopy(this.arguments, 0, args, 1,
						this.arguments.length);
			} else {
				args = this.arguments;
			}
			return Messages.getText(messageKey, args);
		}
	}

}
