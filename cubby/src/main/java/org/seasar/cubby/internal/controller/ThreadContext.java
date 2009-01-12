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

import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;

/**
 * 実行スレッドのコンテキスト情報です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class ThreadContext {

	/** ThreadContext を保存するスレッドローカル。 */
	private static final ThreadLocal<ThreadContext> THREAD_LOCAL = new ThreadLocal<ThreadContext>();

	/** 要求。 */
	private final HttpServletRequest request;

	/** 応答 */
	private final HttpServletResponse response;

	/** メッセージのリソースバンドル。 */
	private ResourceBundle messagesResourceBundle = null;

	/** メッセージの {@link Map} */
	private Map<String, Object> messages = null;

	/** メッセージ表示用リソースバンドルの振る舞い。 */
	private MessagesBehaviour messagesBehaviour;

	/**
	 * インスタンス化します。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 */
	private ThreadContext(final HttpServletRequest request,
			final HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**
	 * スレッドローカル変数からコンテキストを取得します。
	 * 
	 * @return コンテキスト
	 */
	private static ThreadContext getContext() {
		final ThreadContext context = THREAD_LOCAL.get();
		if (context == null) {
			throw new IllegalStateException(
					"Could not get context from ThreadLocal. run in context scope.");
		}
		return context;
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
	 * 現在の実行スレッドに関連付けられた応答を取得します。
	 * 
	 * @return 応答
	 */
	public static HttpServletResponse getResponse() {
		return getContext().response;
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
					.getBundle(context.request.getLocale());
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
			final Container container = ProviderFactory.get(
					ContainerProvider.class).getContainer();
			context.messagesBehaviour = container
					.lookup(MessagesBehaviour.class);
		}
		return context.messagesBehaviour;
	}

	/**
	 * 指定されたコマンドを新しいコンテキスト内で実行します。
	 * 
	 * @param <T>
	 *            コマンドの戻り値
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param command
	 *            コンテキスト内で実行するコマンド
	 * @return コマンドの実行結果
	 * @throws Exception
	 *             コマンドの実行中に例外が発生した場合
	 */
	public static <T> T runInContext(final HttpServletRequest request,
			final HttpServletResponse response, final Command<T> command)
			throws Exception {
		if (request == null) {
			throw new NullPointerException("request");
		}
		if (response == null) {
			throw new NullPointerException("response");
		}
		if (command == null) {
			throw new NullPointerException("command");
		}

		final ThreadContext previous = THREAD_LOCAL.get();
		final ThreadContext context = new ThreadContext(request, response);
		THREAD_LOCAL.set(context);
		try {
			return command.execute(request, response);
		} finally {
			if (previous == null) {
				THREAD_LOCAL.remove();
			} else {
				THREAD_LOCAL.set(previous);
			}
		}
	}

	/**
	 * コンテキスト内で実行するコマンドのインターフェイスです。
	 * 
	 * @author baba
	 * @param <T>
	 *            コマンドの戻り値
	 */
	public interface Command<T> {

		/**
		 * コマンドを実行します。
		 * 
		 * @param request
		 *            要求
		 * @param response
		 *            応答
		 * @return コマンドの実行結果
		 * @throws Exception
		 *             コマンドの実行中に例外が発生した場合
		 */
		T execute(HttpServletRequest request, HttpServletResponse response)
				throws Exception;

	}

}
