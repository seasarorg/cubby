package org.seasar.cubby.action;

import java.util.Map;

/**
 * アクションの基底クラスです。
 * <p>
 * アクションはビューのコントローラーの役割を果たします。
 * </p>
 * 
 * @author agata
 * @author baba
 */
public abstract class Action {

	/**
	 * アクションエラーオブジェクト。
	 */
	protected ActionErrors errors;

	/**
	 * 揮発性メッセージ。
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
	 * 
	 * @return アクションエラーオブジェクト
	 */
	public ActionErrors getErrors() {
		return errors;
	}

	/**
	 * アクションエラーオブジェクトをセットします。
	 * 
	 * @param errors
	 *            アクションエラーオブジェクト
	 */
	public void setErrors(final ActionErrors errors) {
		this.errors = errors;
	}

	/**
	 * 揮発性メッセージを取得します。
	 * 
	 * @return 揮発性メッセージ
	 */
	public Map<String, Object> getFlash() {
		return flash;
	}

	/**
	 * 揮発性メッセージをセットします。
	 * 
	 * @param flash
	 *            揮発性メッセージ
	 */
	public void setFlash(final Map<String, Object> flash) {
		this.flash = flash;
	}

}