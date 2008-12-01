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

	/** ThreadContext を保存するスレッドローカル。 */
	private static final ThreadLocal<ThreadContext> THREAD_LOCAL = new ThreadLocal<ThreadContext>();

	/** 前回のコンテキスト。 */
	private final ThreadContext previous;

	/** 要求。 */
	private final HttpServletRequest request;

	/** メッセージのリソースバンドル。 */
	private ResourceBundle messagesResourceBundle = null;

	/** メッセージの {@link Map} */
	private Map<String, Object> messages = null;

	/** メッセージ表示用リソースバンドルの振る舞い。 */
	private MessagesBehaviour messagesBehaviour;

	/**
	 * インスタンス化します。
	 * 
	 * @param previous
	 *            前回のコンテキスト
	 * @param request
	 *            要求
	 */
	private ThreadContext(final ThreadContext previous,
			final HttpServletRequest request) {
		this.previous = previous;
		this.request = request;
	}

	/**
	 * スレッドローカル変数からコンテキストを取得します。
	 * 
	 * @return コンテキスト
	 */
	private static ThreadContext getContext() {
		final ThreadContext context = THREAD_LOCAL.get();
		if (context == null) {
			throw new IllegalStateException();
		}
		return context;
	}

	/**
	 * 現在の実行スレッドに対するコンテキストを保存し、指定された情報をもつ新規コンテキストを関連付けます。
	 * 
	 * @param request
	 *            要求
	 */
	public static void newContext(final HttpServletRequest request) {
		final ThreadContext previous = THREAD_LOCAL.get();
		final ThreadContext context = new ThreadContext(previous, request);
		THREAD_LOCAL.set(context);
	}

	/**
	 * スレッドローカル変数を {@link ThreadContext#newContext(HttpServletRequest)}
	 * 呼び出し以前の状態に戻します。
	 */
	public static void restoreContext() {
		final ThreadContext context = THREAD_LOCAL.get();
		if (context != null) {
			THREAD_LOCAL.set(context.previous);
		} else {
			remove();
		}
	}

	/**
	 * スレッドローカル変数から現在の実行スレッドに関する情報を削除します。
	 */
	public static void remove() {
		THREAD_LOCAL.remove();
	}

	/**
	 * 現在の実行スレッドに関連付けられた要求を取得します。
	 * 
	 * @return 要求
	 */
	public static HttpServletRequest getRequest() {
		return getContext().request;
	}

	/**
	 * 現在の実行スレッドに関連付けられた要求に対応するメッセージ用の {@link ResourceBundle} を取得します。
	 * 
	 * @return リソースバンドル
	 */
	public static ResourceBundle getMessagesResourceBundle() {
		final ThreadContext context = getContext();
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
	public static Map<String, Object> getMessagesMap() {
		final ThreadContext context = getContext();
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
		}
		return context.messagesBehaviour;
	}

}
