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
package org.seasar.cubby.internal.controller;

import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.internal.util.ServiceFactory;

/**
 * 実行スレッドのコンテキスト情報です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class ThreadContext {

	// /** デフォルトのメッセージのふるまい。 */
	// private static final MessagesBehaviour DEFAULT_MESSAGES_BEHAVIOUR = new
	// DefaultMessagesBehaviour();

	/** ThreadContext を保存するスレッドローカル。 */
	private static final ThreadLocal<ThreadContext> CONTEXT = new ThreadLocal<ThreadContext>() {

		@Override
		protected ThreadContext initialValue() {
			return new ThreadContext();
		}

	};

	/**
	 * スレッドローカルから現在の実行スレッドに関する情報を削除します。
	 */
	public static void remove() {
		CONTEXT.remove();
	}

	/**
	 * 現在の実行スレッドに関連付けられたリクエストを取得します。
	 * 
	 * @return リクエスト
	 */
	public static HttpServletRequest getRequest() {
		return CONTEXT.get().request;
	}

	/**
	 * 現在の実行スレッドに指定されたリクエストを関連付けます。
	 * 
	 * @param request
	 *            リクエスト
	 */
	public static void setRequest(final HttpServletRequest request) {
		CONTEXT.get().request = request;
	}

	//TODO
	// /**
	// * Cubby の全体的な設定情報を取得します。
	// *
	// * @return Cubby の全体的な設定情報
	// */
	// public static CubbyConfiguration getConfiguration() {
	// final Container container = ContainerFactory.getContainer();
	// return container.lookup(CubbyConfiguration.class);
	// }

	/**
	 * 現在の実行スレッドに関連付けられたリクエストに対応するメッセージ用の {@link ResourceBundle} を取得します。
	 * 
	 * @return リソースバンドル
	 */
	public static ResourceBundle getMessagesResourceBundle() {
		final ThreadContext context = CONTEXT.get();
		if (context.messagesResourceBundle == null) {
			final MessagesBehaviour messagesBehaviour = getMessagesBehaviour(context);
			context.messagesResourceBundle = messagesBehaviour
					.getBundle(context.request == null ? null : context.request
							.getLocale());
		}
		return context.messagesResourceBundle;
	}

	/**
	 * {@link #getMessagesResourceBundle()} で取得できる {@link ResourceBundle} を変換した
	 * {@link Map} を取得します。
	 * 
	 * @return メッセージの {@link Map}
	 */
	public static Map<String, String> getMessagesMap() {
		final ThreadContext context = CONTEXT.get();
		if (context.messages == null) {
			final ResourceBundle bundle = getMessagesResourceBundle();
			final MessagesBehaviour messagesBehaviour = getMessagesBehaviour(context);
			context.messages = messagesBehaviour.toMap(bundle);
		}
		return context.messages;
	}

	/**
	 * メッセージ表示用リソースバンドルの振る舞いを取得します。
	 * 
	 * @param context
	 *            実行スレッドのコンテキスト情報
	 * @return メッセージ表示用リソースバンドルの振る舞い
	 */
	private static MessagesBehaviour getMessagesBehaviour(
			final ThreadContext context) {
		if (context.messagesBehaviour == null) {
			context.messagesBehaviour = ServiceFactory
					.getProvider(MessagesBehaviour.class);
			// final Container container = ContainerFactory.getContainer();
			// if (container.has(MessagesBehaviour.class)) {
			// context.messagesBehaviour = container
			// .lookup(MessagesBehaviour.class);
			// } else {
			// context.messagesBehaviour = DEFAULT_MESSAGES_BEHAVIOUR;
			// }
		}
		return context.messagesBehaviour;
	}

	/**
	 * インスタンス化します。
	 */
	private ThreadContext() {
	}

	/** リクエスト。 */
	private HttpServletRequest request;

	/** メッセージのリソースバンドル。 */
	private ResourceBundle messagesResourceBundle = null;

	/** メッセージの {@link Map} */
	private Map<String, String> messages = null;

	/** メッセージ表示用リソースバンドルの振る舞い。 */
	private MessagesBehaviour messagesBehaviour;

}
