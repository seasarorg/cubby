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
package org.seasar.cubby.action;

import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.spi.JsonProvider;
import org.seasar.cubby.util.StringUtils;

/**
 * JSON 形式のレスポンスを返す {@link ActionResult} です。
 * <p>
 * アクションメソッドの戻り値としてこのインスタンスを指定することで、指定された JavaBean を JSON/JSONP
 * 形式に変換してレスポンスを返します。 ブラウザの JavaScript から発行されたリクエストを処理する場合等に使用してください。 JavaBean/
 * {@link Map}/配列/{@link Collection}などがコンストラクタに渡すことができます。
 * </p>
 * <p>
 * 使用例1 : JSON 形式のレスポンスを返す
 * 
 * <pre>
 * MyBean bean = ...;
 * return new Json(bean);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例2 : コールバック関数名を指定して JSONP 形式のレスポンスを返す
 * 
 * <pre>
 * MyBean bean = ...;
 * return new Json(bean, &quot;callback&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例3 : コンテントタイプと文字コードを指定して JSON 形式のレスポンスを返す。<br>
 * セットされるコンテントタイプは"text/javascript+json; charset=Shift_JIS"になります。
 * 
 * <pre>
 * MyBean bean = ...;
 * return new Json(bean).contentType(&quot;text/javascript+json&quot;).encoding(&quot;Shift_JIS&quot;);
 * </pre>
 * 
 * </p>
 * 
 * @see <a href="http://www.json.org/">JSON(JavaScript Object Notation)< /a>
 * @see <a href="http://ajaxian.com/archives/jsonp-json-with-padding">JSONP(JSON
 *      * with Padding)< /a>
 * @see JsonProvider#toJson(Object)
 * @author baba
 * @author agata
 * @since 1.0.0
 */
public class Json implements ActionResult {

	private Object bean;

	private String calllback;

	private String contentType = "text/javascript";

	private String encoding = "utf-8";

	private boolean xjson = false;

	/**
	 * JSON 形式でレスポンスを返すインスタンスを生成します。
	 * 
	 * @param bean
	 *            JSON 形式に変換する JavaBean/{@link Map}/配列/{@link Collection}など
	 */
	public Json(final Object bean) {
		this(bean, null);
	}

	/**
	 * JSONP 形式でレスポンスを返すインスタンスを生成します。
	 * 
	 * @param bean
	 *            JSONP 形式に変換する JavaBean/{@link Map}/配列/{@link Collection}など
	 * @param callback
	 *            コールバック関数名
	 */
	public Json(final Object bean, final String callback) {
		this.bean = bean;
		this.calllback = callback;
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
	 * コンテントタイプをセットします。
	 * 
	 * @param contentType
	 *            コンテントタイプ。(例："text/javascript+json")
	 * @return {@link Json}
	 */
	public Json contentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	/**
	 * コンテントタイプを取得します。
	 * 
	 * @return コンテントタイプ
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * エンコーディングをセットします。
	 * <p>
	 * セットされたエンコーディングはコンテントタイプのcharsetとして使用されます。
	 * </p>
	 * 
	 * @param encoding
	 *            エンコーディング。　（例："Shift_JIS" ）
	 * @return {@link Json}
	 */
	public Json encoding(String encoding) {
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

	public void xjson() {
		this.xjson = true;
	}

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

		final JsonProvider jsonProvider = JsonProvider.Factory.get();
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

	private boolean isJsonp() {
		return !StringUtils.isEmpty(calllback);
	}

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
