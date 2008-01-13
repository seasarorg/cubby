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