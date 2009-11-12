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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 実行スレッドのコンテキスト情報です。
 * 
 * @author baba
 */
public class ThreadContext {

	/** ThreadContext を保存するスレッドローカル。 */
	private static final ThreadLocal<ThreadContext> THREAD_LOCAL = new ThreadLocal<ThreadContext>();

	private static final ThreadLocal<ThreadContext> PREVIOUS = new ThreadLocal<ThreadContext>();

	/** 要求。 */
	private final HttpServletRequest request;

	/** 応答 */
	private final HttpServletResponse response;

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
	public static ThreadContext getCurrentContext() {
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
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * 現在の実行スレッドに関連付けられた応答を取得します。
	 * 
	 * @return 応答
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * コンテキストに入ります。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 */
	public static void enter(final HttpServletRequest request,
			final HttpServletResponse response) {
		if (request == null) {
			throw new NullPointerException("request");
		}
		if (response == null) {
			throw new NullPointerException("response");
		}

		final ThreadContext previous = THREAD_LOCAL.get();
		if (previous != null) {
			PREVIOUS.set(previous);
		}
		final ThreadContext context = new ThreadContext(request, response);
		THREAD_LOCAL.set(context);
	}

	/**
	 * コンテキストを抜けます。
	 */
	public static void exit() {
		final ThreadContext previous = PREVIOUS.get();
		if (previous != null) {
			THREAD_LOCAL.set(previous);
		}
	}

	/**
	 * コンテキストを削除します。
	 */
	public static void remove() {
		THREAD_LOCAL.remove();
	}

}
