/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.internal.util.StringUtils;
import org.seasar.cubby.spi.JsonProvider;
import org.seasar.cubby.spi.ProviderFactory;

/**
 * JSON 形式の応答を返す {@link ActionResult} です。
 * <p>
 * アクションメソッドの戻り値としてこのインスタンスを指定することで、指定された JavaBean を JSON/JSONP 形式に変換して応答を返します。
 * ブラウザの JavaScript から発行された要求を処理する場合等に使用してください。 JavaBean/ {@link Map}/配列/
 * {@link Collection}などがコンストラクタに渡すことができます。
 * </p>
 * <p>
 * 使用例1 : JSON 形式の応答を返す
 * 
 * <pre>
 * MyBean bean = ...;
 * return new Json(bean);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例2 : コールバック関数名を指定して JSONP 形式の応答を返す
 * 
 * <pre>
 * MyBean bean = ...;
 * return new Json(bean, &quot;callback&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例3 : MIME タイプと文字コードを指定して JSON 形式の応答を返す。<br>
 * 設定される MIME タイプは"text/javascript+json; charset=Shift_JIS"になります。
 * 
 * <pre>
 * MyBean bean = ...;
 * return new Json(bean).contentType(&quot;text/javascript+json&quot;).encoding(&quot;Shift_JIS&quot;);
 * </pre>
 * 
 * </p>
 * 
 * @see <a
 *      href="http://www.json.org/">JSON(JavaScript&nbsp;Object&nbsp;Notation)</a>
 * @see <a
 *      href="http://ajaxian.com/archives/jsonp-json-with-padding">JSONP(JSON&nbsp;with&nbsp;Padding)</a>
 * @see JsonProvider#toJson(Object)
 * @author baba
 * @author agata
 */
public class Json implements ActionResult {

	/** 変換対象のオブジェクト。 */
	private final Object bean;

	/** 変換に使用する {@link JsonProvider}。 */
	private final JsonProvider jsonProvider;

	/** コールバック関数名。 */
	private final String calllback;

	/** MIME タイプ。 */
	private String contentType = "text/javascript";

	/** エンコーディング。 */
	private String encoding = "utf-8";

	/** X-JSON 応答ヘッダを使用するか。 */
	private boolean xjson = false;

	/**
	 * JSON 形式で応答を返すインスタンスを生成します。
	 * 
	 * @param bean
	 *            JSON 形式に変換するオブジェクト
	 */
	public Json(final Object bean) {
		this(bean, null, ProviderFactory.get(JsonProvider.class));
	}

	/**
	 * JSON 形式で応答を返すインスタンスを生成します。
	 * 
	 * @param bean
	 *            JSON 形式に変換するオブジェクト
	 * @param jsonProvider
	 *            JSON のプロバイダ
	 */
	public Json(final Object bean, final JsonProvider jsonProvider) {
		this(bean, null, jsonProvider);
	}

	/**
	 * JSONP 形式で応答を返すインスタンスを生成します。
	 * 
	 * @param bean
	 *            JSONP 形式に変換するオブジェクト
	 * @param callback
	 *            コールバック関数名
	 */
	public Json(final Object bean, final String callback) {
		this(bean, callback, ProviderFactory.get(JsonProvider.class));
	}

	/**
	 * JSONP 形式で応答を返すインスタンスを生成します。
	 * 
	 * @param bean
	 *            JSONP 形式に変換するオブジェクト
	 * @param callback
	 *            コールバック関数名
	 * @param jsonProvider
	 *            JSON のプロバイダ
	 */
	public Json(final Object bean, final String callback,
			final JsonProvider jsonProvider) {
		if (jsonProvider == null) {
			throw new NullPointerException("jsonProvider");
		}
		this.bean = bean;
		this.calllback = callback;
		this.jsonProvider = jsonProvider;
	}

	/**
	 * JSON 形式に変換する JavaBeanを取得します。
	 * 
	 * @return JSON 形式に変換する JavaBean
	 */
	public Object getBean() {
		return this.bean;
	}

	/**
	 * コールバック関数名を取得します。
	 * 
	 * @return コールバック関数名
	 */
	public String getCallback() {
		return this.calllback;
	}

	/**
	 * MIME タイプを設定します。
	 * 
	 * @param contentType
	 *            MIME タイプ (例："text/javascript+json")
	 * @return {@link Json}
	 */
	public Json contentType(final String contentType) {
		this.contentType = contentType;
		return this;
	}

	/**
	 * MIME タイプを取得します。
	 * 
	 * @return MIME タイプ
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * エンコーディングを設定します。
	 * <p>
	 * 設定されたエンコーディングは MIME タイプの charset として使用されます。
	 * </p>
	 * 
	 * @param encoding
	 *            エンコーディング (例："Shift_JIS")
	 * @return {@link Json}
	 */
	public Json encoding(final String encoding) {
		this.encoding = encoding;
		return this;
	}

	/**
	 * エンコーディングを取得します。
	 * 
	 * @return エンコーディング
	 */
	public String getEncoding() {
		return this.encoding;
	}

	/**
	 * JSON 文字列を応答ボディではなく X-JSON 応答ヘッダに設定することを指定します。
	 * <p>
	 * prototype.js の <code>Ajax.Request</code> を使うときに使用してください。
	 * </p>
	 * 
	 * @see <a
	 *      href="http://www.prototypejs.org/api/ajax/options">www.prototypejs.org&nbsp;-&nbsp;Ajax&nbsp;Options</a>
	 */
	public void xjson() {
		this.xjson = true;
	}

	/**
	 * JSON 文字列を X-JOSN 応答ヘッダに設定するかを示します。
	 * 
	 * @return JSON 文字列を X-JOSN 応答ヘッダに設定する場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	public boolean isXjson() {
		return xjson;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute(final ActionContext actionContext,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding(this.encoding);
		response
				.setContentType(this.contentType + "; charset=" + this.encoding);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		final String script;
		if (isJsonp()) {
			script = appendCallbackFunction(jsonProvider.toJson(bean),
					calllback);
		} else {
			script = jsonProvider.toJson(bean);
		}

		if (xjson) {
			response.setHeader("X-JSON", script);
		} else {
			final Writer writer = response.getWriter();
			writer.write(script);
			writer.flush();
		}
	}

	/**
	 * JSONP 形式に変換するかどうかを示します。
	 * 
	 * @return JSONP 形式に変換する場合は <code>true</code>、そうでない場合は <code>false</code>
	 */
	private boolean isJsonp() {
		return !StringUtils.isEmpty(calllback);
	}

	/**
	 * JSON 形式のスクリプトに指定されたコールバック関数を付加します。
	 * 
	 * @param script
	 *            JSON 形式のスクリプト
	 * @param callback
	 *            コールバック関数名
	 * @return コールバック関数が追加された JSON 形式のスクリプト
	 */
	private static String appendCallbackFunction(final String script,
			final String callback) {
		final StringBuilder builder = new StringBuilder(script.length()
				+ callback.length() + 10);
		builder.append(callback);
		builder.append("(");
		builder.append(script);
		builder.append(");");
		return builder.toString();
	}

}
