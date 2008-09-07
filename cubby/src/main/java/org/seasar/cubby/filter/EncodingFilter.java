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
package org.seasar.cubby.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * リクエストのエンコーディングを設定するためのフィルタです。
 * <p>
 * 初期化パラメータ {@value #ENCODING}、{@value #FORCE_ENCODING} でリクエストの文字エンコーディングを指定します。
 * </p>
 * <p>
 * 初期化パラメータ {@value #URI_ENCODING}、{@value #URI_BYTES_ENCODING} で
 * {@link HttpServletRequest#getServletPath()}、
 * {@link HttpServletRequest#getPathInfo()} で取得できるパスのエンコーディングを指定します。
 * </p>
 * <p>
 * <table>
 * <thead>
 * <tr>
 * <th>param-name</th>
 * <th>param-value</th>
 * </tr>
 * <tbody>
 * <tr>
 * <td>{@value #ENCODING}</td>
 * <td>リクエストのエンコーディングを指定します。リクエストのエンコーディングが <code>null</code> か、
 * {@value #FORCE_ENCODING} に <code>true</code>
 * が指定された場合はこのエンコーディングがリクエストに設定されます。</td>
 * </tr>
 * <tr>
 * <td>{@value #FORCE_ENCODING}</td>
 * <td><code>true</code> を指定した場合は、リクエストにエンコーディングが設定されていても {@value #ENCODING}
 * で上書きします。
 * </tr>
 * <tr>
 * <td>{@value #URI_ENCODING}</td>
 * <td>URI のエンコーディングを指定します。</td>
 * </tr>
 * <tr>
 * <td>{@value #URI_BYTES_ENCODING}</td>
 * <td>URI をバイト配列として取得する際のエンコーディングを指定します。</td>
 * </tr>
 * </tbody>
 * </table>
 * <caption>初期化パラメータ</caption>
 * </p>
 * 
 * @author baba
 * @since 1.1.1
 */
public class EncodingFilter implements Filter {

	private static final String ALREADY_FILTERED_ATTRIBUTE_NAME = EncodingFilter.class
			.getName()
			+ ".FILTERED";

	/** エンコーディングのキー。 */
	private static final String ENCODING = "encoding";

	/** 強制エンコーディング設定のキー。 */
	private static final String FORCE_ENCODING = "forceEncoding";

	/** URI エンコーディングのキー。 */
	private static final String URI_ENCODING = "URIEncoding";

	/** URI バイト列のエンコーディングのキー。 */
	private static final String URI_BYTES_ENCODING = "URIBytesEncoding";

	/** URI バイト列のエンコーディングのデフォルト値。 */
	private static final String DEFAULT_URI_BYTE_ENCODING = "ISO-8859-1";

	/** エンコーディング。 */
	private String encoding;

	/** 強制エンコーディング設定。 */
	private boolean forceEncoding = false;

	/** URI エンコーディング。 */
	private String uriEncoding;

	/** URI バイト列のエンコーディング。 */
	private String uriBytesEncoding;

	/**
	 * {@inheritDoc}
	 */
	public void init(final FilterConfig config) throws ServletException {
		encoding = config.getInitParameter(ENCODING);
		try {
			validateEncoding(encoding);
		} catch (final UnsupportedEncodingException e) {
			throw new ServletException(e);
		}

		final String forceEncodingString = config
				.getInitParameter(FORCE_ENCODING);
		if (forceEncodingString != null) {
			forceEncoding = Boolean.parseBoolean(forceEncodingString);
		}

		uriEncoding = config.getInitParameter(URI_ENCODING);
		try {
			validateEncoding(uriEncoding);
		} catch (final UnsupportedEncodingException e) {
			throw new ServletException(e);
		}

		uriBytesEncoding = config.getInitParameter(URI_BYTES_ENCODING);
		if (uriBytesEncoding == null) {
			uriBytesEncoding = DEFAULT_URI_BYTE_ENCODING;
		}
		try {
			validateEncoding(uriBytesEncoding);
		} catch (final UnsupportedEncodingException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * 指定されたエンコーディングがサポートされているか検査します。
	 * 
	 * @param encoding
	 *            エンコーディング
	 * @throws UnsupportedEncodingException
	 *             指定されたエンコーディングがサポートされていない場合
	 */
	private void validateEncoding(final String encoding)
			throws UnsupportedEncodingException {
		if (encoding != null) {
			if (!Charset.isSupported(encoding)) {
				throw new UnsupportedEncodingException(encoding);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void destroy() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		if (request.getAttribute(ALREADY_FILTERED_ATTRIBUTE_NAME) == null) {
			request.setAttribute(ALREADY_FILTERED_ATTRIBUTE_NAME, Boolean.TRUE);
			if (request.getCharacterEncoding() == null || forceEncoding) {
				request.setCharacterEncoding(encoding);
			}
			if (uriEncoding == null) {
				chain.doFilter(request, response);
			} else {
				final ServletRequest wrapper = new EncodingHttpServletRequestWrapper(
						(HttpServletRequest) request, null, null);
				chain.doFilter(wrapper, response);
			}
			request.removeAttribute(ALREADY_FILTERED_ATTRIBUTE_NAME);
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * 適切なエンコーディングで処理するための {@link HttpServletRequestWrapper} です。
	 * 
	 * @author baba
	 * @since 1.1.1
	 */
	private class EncodingHttpServletRequestWrapper extends
			HttpServletRequestWrapper {

		/** URI エンコーディング。 */
		private final String uriEncoding;

		/** URI バイト列のエンコーディング。 */
		private final String uriBytesEncoding;

		/**
		 * 指定されたリクエストをラップします。
		 * 
		 * @param request
		 *            リクエスト
		 * @param uriEncoding
		 *            URI エンコーディング
		 * @param uriBytesEncoding
		 *            URI バイト列のエンコーディング。
		 * @throws IOException
		 *             スーパクラスのコンストラクタで例外が発生した場合
		 */
		public EncodingHttpServletRequestWrapper(
				final HttpServletRequest request, final String uriEncoding,
				final String uriBytesEncoding) throws IOException {
			super(request);
			this.uriEncoding = uriEncoding;
			this.uriBytesEncoding = uriBytesEncoding;
		}

		@Override
		public String getServletPath() {
			return rebuild(super.getServletPath(), uriEncoding,
					uriBytesEncoding);
		}

		@Override
		public String getPathInfo() {
			return rebuild(super.getPathInfo(), uriEncoding, uriBytesEncoding);
		}

		/**
		 * 指定された文字列を一度 <code>bytesEncoding</code> でバイト配列に戻し、
		 * <code>encoding</code> で文字列を再構築します。
		 * 
		 * @param str
		 *            文字列
		 * @param encoding
		 *            エンコーディング
		 * @param bytesEncoding
		 *            バイト配列に戻す時のエンコーディング
		 * @return 再構築された文字列
		 */
		private String rebuild(final String str, final String encoding,
				final String bytesEncoding) {
			if (str == null || encoding == null) {
				return str;
			}
			try {
				return new String(str.getBytes(bytesEncoding), encoding);
			} catch (final UnsupportedEncodingException e) {
				throw new IllegalStateException(e);
			}
		}

	}

}
