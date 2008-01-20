package org.seasar.cubby.action;

import java.text.MessageFormat;
import java.util.Map;

import org.seasar.cubby.util.Messages;

/**
 * アクションの基底クラスです。アクションはビューのコントローラーの役割を果たします。
 * @author agata
 */
public abstract class Action {

	/**
	 * アクションエラーオブジェクト
	 */
	protected ActionErrors errors;

	/**
	 * 揮発性メッセージ
	 */
	protected Map<String, Object> flash;

	/**
	 * Actionの実行前に呼ばれます。パラメータのバインディング前に呼ばれるので、パラメータを使用したい場合はリクエストから直接取得する必要があります。
	 */
	public void initialize() {
	}

	/**
	 * フォーワードの直前で呼ばれます。対象のActionクラスのフォワード先で必ず使用する共通のデータなどを取得する目的で使用します。
	 */
	public void prerender() {
	}

	/**
	 * フォワードの直後で呼ばれます。通常はあまり使用することはないでしょう。
	 */
	public void postrender() {
	}

	/**
	 * アクションエラーオブジェクトを取得します。
	 * @return アクションエラーオブジェクト
	 */
	public ActionErrors getErrors() {
		return errors;
	}

	/**
	 * アクションエラーオブジェクトをセットします。
	 * @param errors アクションエラーオブジェクト
	 */
	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	/**
	 * 揮発性メッセージを取得します。
	 * @return 揮発性メッセージ
	 */
	public Map<String, Object> getFlash() {
		return flash;
	}

	/**
	 * 揮発性メッセージをセットします。
	 * @param flash 揮発性メッセージ
	 */
	public void setFlash(Map<String, Object> flash) {
		this.flash = flash;
	}

	/**
	 * メッセージリソースから指定されたキーのメッセージを取得します。
	 * @see Messages#getText(String, Object...)
	 * @param key メッセージキー
	 * @return メッセージ
	 */
	public String getText(String key) {
		return Messages.getText(key);
	}

	/**
	 * メッセージリソースから指定されたキーの値を取得し、置換処理を行ったメッセージを取得します。
	 * <pre>
	 * msg.sample2=メッセージ中に置換文字列を使用できます（引数1={0}, 引数2={1}）。
	 * 
	 * // 「メッセージ中に置換文字列を使用できます（引数1=foo, 引数2=bar）。」
	 * String message = getText("msg.sample2", "foo", "bar");
	 * </pre>
	 * @see Messages#getText(String, Object...)
	 * @param key メッセージキー
	 * @param args 置換文字列
	 * @return 置換処理後のメッセージ
	 */
	public String getText(String key, Object... args) {
		String text = getText(key);
		MessageFormat format = new MessageFormat(text);
		return format.format(args);
	}

}