package org.seasar.cubby.action;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.framework.util.JSONSerializer;
import org.seasar.framework.util.StringUtil;

/**
 * JSON 形式のレスポンスを返す {@link ActionResult} です。
 * <p>
 * アクションメソッドの戻り値としてこのインスタンスを指定することで、指定された JavaBean を JSON/JSONP 形式に変換してレスポンスを返します。
 * ブラウザの JavaScript から発行されたリクエストを処理する場合等に使用してください。
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
 * 
 * @see <a href="http://www.json.org/">JSON(JavaScript Object Notation)</a>
 * @see <a href="http://ajaxian.com/archives/jsonp-json-with-padding">JSONP(JSON with Padding)</a>
 * @see JSONSerializer#serialize(Object)
 * @author baba
 */
public class Json extends AbstractActionResult {

	private Object bean;

	private String calllback;

	/**
	 * JSON 形式でレスポンスを返すインスタンスを生成します。
	 * 
	 * @param bean
	 *            JSON 形式に変換する JavaBean
	 */
	public Json(final Object bean) {
		this(bean, null);
	}

	/**
	 * JSONP 形式でレスポンスを返すインスタンスを生成します。
	 * 
	 * @param bean
	 *            JSON 形式に変換する JavaBean
	 * @param callback
	 *            コールバック関数名
	 */
	public Json(final Object bean, final String callback) {
		this.bean = bean;
		this.calllback = callback;
	}

	public void execute(final ActionContext context,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {

		response.setContentType("text/javascript; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		final String script;
		if (isJsonp()) {
			script = appendCallbackFunction(JSONSerializer.serialize(bean),
					calllback);
		} else {
			script = JSONSerializer.serialize(bean);
		}

		final Writer writer = response.getWriter();
		writer.write(script);
		writer.flush();
	}

	private boolean isJsonp() {
		return !StringUtil.isEmpty(calllback);
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
