package org.seasar.cubby.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.seasar.cubby.controller.ThreadContext;

/**
 * メッセージリソースを取得するユーティリティクラスです。
 * 
 * @author agata
 */
public class Messages {

	/**
	 * 指定されたメッセージリソースから指定されたキーの値を取得し、置換処理を行ったメッセージを取得します。
	 * 
	 * @param resource
	 *            メッセージリソース
	 * @param key
	 *            メッセージキー
	 * @param args
	 *            置換文字列
	 * @return 置換処理後のメッセージ
	 */
	public static String getText(final ResourceBundle resource,
			final String key, final Object... args) {
		return MessageFormat.format(resource.getString(key), args);
	}

	/**
	 * メッセージリソース（messages.properties）から指定されたキーの値を取得し、置換処理を行ったメッセージを取得します。
	 * 
	 * <pre>
	 * msg.sample2=メッセージ中に置換文字列を使用できます（引数1={0}, 引数2={1}）。
	 * 
	 * // 「メッセージ中に置換文字列を使用できます（引数1=foo, 引数2=bar）。」
	 * String message = Messages.getText(&quot;msg.sample2&quot;, &quot;foo&quot;, &quot;bar&quot;);
	 * </pre>
	 * 
	 * @see Messages#getText(ResourceBundle, String, Object...)
	 * @param key
	 *            メッセージキー
	 * @param args
	 *            置換文字列
	 * @return 置換処理後のメッセージ
	 */
	public static String getText(final String key, final Object... args) {
		final ResourceBundle messagesResourceBundle = ThreadContext
				.getMessagesResourceBundle();
		return getText(messagesResourceBundle, key, args);
	}
}
