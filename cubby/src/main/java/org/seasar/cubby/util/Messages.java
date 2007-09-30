package org.seasar.cubby.util;

import static org.seasar.cubby.CubbyConstants.RES_MESSAGES;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.seasar.framework.util.ResourceBundleUtil;

/**
 * メッセージリソースを取得するユーティリティクラスです。
 * @author agata
 */
public class Messages {

	/**
	 * 指定されたメッセージリソースから指定されたキーの値を取得し、置換処理を行ったメッセージを取得します。
	 * @param resource メッセージリソース
	 * @param key メッセージキー
	 * @param args 置換文字列
	 * @return 置換処理後のメッセージ
	 */
	public static String getText(ResourceBundle resource, String key,
			Object... args) {
		return MessageFormat.format(resource.getString(key), args);
	}

	/**
	 * メッセージリソース（messages.properties）から指定されたキーの値を取得し、置換処理を行ったメッセージを取得します。
	 * <pre>
	 * msg.sample2=メッセージ中に置換文字列を使用できます（引数1={0}, 引数2={1}）。
	 * 
	 * // 「メッセージ中に置換文字列を使用できます（引数1=foo, 引数2=bar）。」
	 * String message = Messages.getText("msg.sample2", "foo", "bar");
	 * </pre>
	 * @see Messages#getText(ResourceBundle, String, Object...)
	 * @param key メッセージキー
	 * @param args 置換文字列
	 * @return 置換処理後のメッセージ
	 */
	public static String getText(String key, Object... args) {
		ResourceBundle resource = ResourceBundleUtil.getBundle(RES_MESSAGES,
				LocaleHolder.getLocale());
		return getText(resource, key, args);
	}
}
