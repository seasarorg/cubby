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
package org.seasar.cubby.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

/**
 * 要求の URL をベースとしたリンク文字列を構築します。
 * 
 * @author baba
 * @since 1.1.4
 */
public class LinkBuilder {

	/** プロトコル。 */
	private String protocol;

	/** ホスト名。 */
	private String host;

	/** ホスト上でのポート番号。 */
	private int port;

	/** ホスト上のファイル。 */
	private String file;

	/**
	 * URL ビルダオブジェクトを生成します。
	 */
	public LinkBuilder() {
		this.clear();
	}

	/**
	 * このインスタンスをクリアします。
	 */
	public void clear() {
		this.protocol = null;
		this.host = null;
		this.port = -1;
		this.file = null;
	}

	/**
	 * プロトコルを取得します。
	 * 
	 * @return プロトコル
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * プロトコルを設定します。
	 * 
	 * @param protocol
	 *            プロトコル
	 * @throws NullPointerException
	 *             指定された <code>protocol</code> が <code>null</code> の場合
	 */
	public void setProtocol(final String protocol) {
		if (protocol == null) {
			throw new NullPointerException("No protocol");
		}
		this.protocol = protocol;
	}

	/**
	 * ホスト名を取得します。
	 * 
	 * @return ホスト名
	 */
	public String getHost() {
		return host;
	}

	/**
	 * ホスト名を設定します。
	 * 
	 * @param host
	 *            ホスト名
	 * @throws NullPointerException
	 *             指定された <code>host</code> が <code>null</code> の場合
	 */
	public void setHost(final String host) {
		if (host == null) {
			throw new NullPointerException("No host");
		}
		this.host = host;
	}

	/**
	 * ホスト上のポート番号を取得します。
	 * 
	 * @return ホスト上のポート番号
	 */
	public int getPort() {
		return port;
	}

	/**
	 * ホスト上のポート番号を設定します。
	 * 
	 * @param port
	 *            ホスト上のポート番号
	 * @throws IllegalArgumentException
	 *             ポート番号が負の値の場合
	 */
	public void setPort(final int port) {
		if (port < 0) {
			throw new IllegalArgumentException("Invalid port number :" + port);
		}
		this.port = port;
	}

	/**
	 * ホスト上のファイルを取得します。
	 * 
	 * @return ホスト上のファイル
	 */
	public String getFile() {
		return file;
	}

	/**
	 * ホスト上のファイルを設定します。
	 * 
	 * @param file
	 *            ホスト上のファイル
	 */
	public void setFile(final String file) {
		this.file = file;
	}

	/**
	 * このリンクの文字列表現を構築します。
	 * <p>
	 * 指定された要求とこのオブジェクトに指定されたプロトコル、ホスト名、ホスト上のポート番号が同じ場合は相対パスであるとみなして内部形式
	 * のリンク文字列を、そうでない場合は外部形式のリンク文字列を構築します。
	 * </p>
	 * 
	 * @param request
	 *            要求
	 * @return URL の文字列表現
	 * @throws MalformedURLException
	 *             未知のプロトコルとして指定された場合
	 */
	public String toLink(final HttpServletRequest request)
			throws MalformedURLException {
		final URL requestURL = new URL(request.getRequestURL().toString());
		final URL newURL = new URL(getNewProtocol(requestURL.getProtocol()),
				getNewHost(requestURL.getHost()), getNewPort(requestURL
						.getPort()), getNewFile(requestURL.getFile()));
		if (isRelativeLink(requestURL, newURL)) {
			return newURL.getFile();
		} else {
			return newURL.toExternalForm();
		}
	}

	/**
	 * 指定された 2 つの URL を比較して、相対パス形式の URL 文字列を生成するかどうかを示します。
	 * 
	 * @param url1
	 *            URL1
	 * @param url2
	 *            URL2
	 * @return 相対パス形式の URL 文字列を生成する場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	private boolean isRelativeLink(final URL url1, final URL url2) {
		if (!url1.getProtocol().equals(url2.getProtocol())) {
			return false;
		}
		if (!url1.getHost().equals(url2.getHost())) {
			return false;
		}
		if (url1.getPort() != url2.getPort()) {
			return false;
		}
		return true;
	}

	/**
	 * 新しい URL のプロトコルを返します。
	 * 
	 * @param requestProtocol
	 *            要求のプロトコル
	 * @return 新しい URL のプロトコル
	 */
	private String getNewProtocol(final String requestProtocol) {
		if (this.protocol == null) {
			return requestProtocol;
		} else {
			return this.protocol;
		}
	}

	/**
	 * 新しい URL のホスト名を返します。
	 * 
	 * @param requestHost
	 *            要求のホスト名
	 * @return 新しい URL のホスト名
	 */
	private String getNewHost(final String requestHost) {
		if (this.host == null) {
			return requestHost;
		} else {
			return this.host;
		}
	}

	/**
	 * 新しい URL のホスト上のポート番号
	 * 
	 * @param requestPort
	 *            要求のホスト上のポート番号
	 * @return 新しい URL のホスト上のポート番号
	 */
	private int getNewPort(final int requestPort) {
		if (this.port < 0) {
			return requestPort;
		} else {
			return this.port;
		}
	}

	/**
	 * 新しい URL のホスト上のファイル
	 * 
	 * @param requestPort
	 *            要求のホスト上のファイル
	 * @return 新しい URL のホスト上のファイル
	 */
	private String getNewFile(final String currentFile) {
		if (this.file == null) {
			return currentFile;
		} else {
			return this.file;
		}
	}

	/**
	 * プロトコルを設定します。
	 * 
	 * @param protocol
	 *            プロトコル
	 * @return このオブジェクト
	 */
	public LinkBuilder protocol(final String protocol) {
		this.setProtocol(protocol);
		return this;
	}

	/**
	 * ホスト名を設定します。
	 * 
	 * @param host
	 *            ホスト名
	 * @return このオブジェクト
	 */
	public LinkBuilder host(final String host) {
		this.setHost(host);
		return this;
	}

	/**
	 * ホスト上のポート場号を設定します。
	 * 
	 * @param port
	 *            ホスト上のポート場号
	 * @return このオブジェクト
	 */
	public LinkBuilder port(final int port) {
		this.setPort(port);
		return this;
	}

	/**
	 * ホスト上のファイルを設定します。
	 * 
	 * @param file
	 *            ホスト上のファイル
	 * @return このオブジェクト
	 */
	public LinkBuilder file(final String file) {
		this.setFile(file);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(this);
		builder.append(" [host=");
		builder.append(host);
		builder.append(",protocol=");
		builder.append(protocol);
		builder.append(",port=");
		builder.append(port);
		builder.append(",file=");
		builder.append(file);
		builder.append("]");
		return builder.toString();
	}

}
