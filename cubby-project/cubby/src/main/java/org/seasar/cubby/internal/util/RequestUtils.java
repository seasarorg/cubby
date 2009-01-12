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
package org.seasar.cubby.internal.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * サーブレット要求をに対するユーティリティクラスです。
 * 
 * @author baba
 * @since 2.0.0
 */
public class RequestUtils {

	/**
	 * リクエストの URI からコンテキストパスを除いたパスを返します。
	 * 
	 * @param request
	 *            リクエスト
	 * @return コンテキストパスを除いたパス
	 */
	public static String getPath(final HttpServletRequest request) {
		final StringBuilder builder = new StringBuilder();
		builder.append(request.getServletPath());
		final String pathInfo = request.getPathInfo();
		if (pathInfo != null) {
			builder.append(pathInfo);
		}
		return builder.toString();
	}

	/**
	 * リクエストから属性を取得します。
	 * 
	 * @param <T>
	 *            取得する属性の型
	 * @param request
	 *            要求
	 * @param name
	 *            属性名
	 * @return 属性
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(final ServletRequest request,
			final String name) {
		return (T) request.getAttribute(name);
	}

}
