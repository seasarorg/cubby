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

import java.lang.reflect.Method;
import java.util.Map;

import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.MethodUtil;

/**
 * アクションの基底クラスです。
 * <p>
 * アクションはビューのコントローラーの役割を果たします。
 * </p>
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public abstract class Action {

	/** アクションエラーオブジェクト。 */
	protected ActionErrors errors;

	/** 揮発性メッセージ。 */
	protected Map<String, Object> flash;

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

	/**
	 * アクションメソッドの実行前に呼ばれます。
	 * <p> {{@link #initialize()} の実行後、指定されたアクションメソッドに {@link InitializeMethod}
	 * でメソッド名が指定されている場合、そのメソッドを呼び出します。
	 * </p>
	 * <p>
	 * パラメータのバインディング前に呼ばれるので、パラメータを使用したい場合はリクエストから直接取得する必要があります。
	 * </p>
	 * 
	 * @param actionMethod
	 *            アクションメソッド
	 * @since 1.1.0
	 */
	public void initialize(final Method actionMethod) {
		this.initialize();
		if (actionMethod.isAnnotationPresent(InitializeMethod.class)) {
			final InitializeMethod initialize = actionMethod
					.getAnnotation(InitializeMethod.class);
			invoke(initialize.value());
		}
	}

	/**
	 * アクションメソッドの実行前に {@ilnk #initialize(Method)} から呼ばれます。
	 */
	protected void initialize() {
	}

	/**
	 * フォーワードの直前に呼ばれます。
	 * <p>
	 * {@link #prerender()} の実行後、指定されたアクションメソッドが {@link PreRenderMethod}
	 * で装飾されている場合はそのメソッドを呼び出します。
	 * </p>
	 * <p>
	 * 対象のActionクラスのフォワード先で必ず使用する共通のデータなどを取得する目的で使用します。
	 * </p>
	 * 
	 * @param actionMethod
	 *            アクションメソッド
	 * @since 1.1.0
	 */
	public void prerender(final Method actionMethod) {
		this.prerender();
		if (actionMethod.isAnnotationPresent(PreRenderMethod.class)) {
			final PreRenderMethod prerender = actionMethod
					.getAnnotation(PreRenderMethod.class);
			invoke(prerender.value());
		}
	}

	/**
	 * フォーワードの直前に {@link #prerender(Method)} から呼ばれます。
	 */
	protected void prerender() {
	}

	/**
	 * フォワードの直後に呼ばれます。
	 * <p>
	 * 指定されたアクションメソッドが {@link PostRenderMethod} で装飾されている場合はそのメソッドを呼び出します。
	 * {@link #postrender()} を実行します。
	 * </p>
	 * <p>
	 * 通常はあまり使用することはないでしょう。
	 * </p>
	 * 
	 * @param actionMethod
	 *            アクションメソッド
	 * @since 1.1.0
	 */
	public void postrender(final Method actionMethod) {
		if (actionMethod.isAnnotationPresent(PostRenderMethod.class)) {
			final PostRenderMethod postrender = actionMethod
					.getAnnotation(PostRenderMethod.class);
			invoke(postrender.value());
		}
		this.postrender();
	}

	/**
	 * フォワードの直後に {@link #postrender(Method)} 呼ばれます。
	 */
	protected void postrender() {
	}

	/**
	 * このアクションに定義された指定されたメソッド名のメソッドを実行します。
	 * 
	 * @param methodName
	 *            メソッド名
	 * @since 1.1.0
	 */
	protected void invoke(final String methodName) {
		final Method prerenderMethod = ClassUtil.getMethod(this.getClass(),
				methodName, null);
		MethodUtil.invoke(prerenderMethod, this, null);
	}

}
