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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

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
	 * <p>
	 * 指定されたアクションメソッドに {@link InitializeMethod} でメソッド名が指定されている場合はそのメソッドを呼び出します。
	 * そうでない場合は {{@link #initialize()} を呼び出します。
	 * </p>
	 * <p>
	 * パラメータのバインディング前に呼ばれるので、パラメータを使用したい場合はリクエストから直接取得する必要があります。
	 * </p>
	 * 
	 * @param actionMethod
	 *            アクションメソッド
	 * @since 1.1.0
	 */
	public void invokeInitializeMethod(final Method actionMethod) {
		if (actionMethod.isAnnotationPresent(InitializeMethod.class)) {
			final InitializeMethod initializeMethod = actionMethod
					.getAnnotation(InitializeMethod.class);
			final String methodName = initializeMethod.value();
			this.invoke(methodName);
		} else {
			this.initialize();
		}
	}

	/**
	 * アクションメソッドが {@link InitializeMethod} で装飾されていない場合に
	 * {@link #invokeInitializeMethod(Method)} から呼ばれるメソッドです。
	 */
	protected void initialize() {
	}

	/**
	 * フォーワードの直前に呼ばれます。
	 * <p>
	 * 指定されたアクションメソッドが {@link PreRenderMethod} でメソッド名が指定されている場合はそのメソッドを呼び出します。
	 * そうでない場合は {@link #prerender()} を呼び出します。
	 * </p>
	 * <p>
	 * 対象のActionクラスのフォワード先で必ず使用する共通のデータなどを取得する目的で使用します。
	 * </p>
	 * 
	 * @param actionMethod
	 *            アクションメソッド
	 * @since 1.1.0
	 */
	public void invokePreRenderMethod(final Method actionMethod) {
		if (actionMethod.isAnnotationPresent(PreRenderMethod.class)) {
			final PreRenderMethod preRenderMethod = actionMethod
					.getAnnotation(PreRenderMethod.class);
			final String methodName = preRenderMethod.value();
			this.invoke(methodName);
		} else {
			this.prerender();
		}
	}

	/**
	 * アクションメソッドが {@link PreRenderMethod} で装飾されていない場合に
	 * {@link #invokePreRenderMethod(Method)} から呼ばれるメソッドです。
	 */
	protected void prerender() {
	}

	/**
	 * フォワードの直後に呼ばれます。
	 * <p>
	 * 指定されたアクションメソッドが {@link PostRenderMethod} でメソッド名が指定されている場合はそのメソッドを呼び出します。
	 * そうでない場合は {@link #postrender()} を呼び出します。
	 * </p>
	 * <p>
	 * 通常はあまり使用することはないでしょう。
	 * </p>
	 * 
	 * @param actionMethod
	 *            アクションメソッド
	 * @since 1.1.0
	 */
	public void invokePostRenderMethod(final Method actionMethod) {
		if (actionMethod.isAnnotationPresent(PostRenderMethod.class)) {
			final PostRenderMethod postRenderMethod = actionMethod
					.getAnnotation(PostRenderMethod.class);
			final String methodName = postRenderMethod.value();
			this.invoke(methodName);
		} else {
			this.postrender();
		}
	}

	/**
	 * アクションメソッドが {@link PostRenderMethod} で装飾されていない場合に
	 * {@link #invokePostRenderMethod(Method)} から呼ばれるメソッドです。
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
		try {
			final Method method = this.getClass().getMethod(methodName);
			method.invoke(this);
		} catch (NoSuchMethodException e) {
			throw new ActionException(e);
		} catch (IllegalAccessException e) {
			throw new ActionException(e);
		} catch (InvocationTargetException e) {
			throw new ActionException(e);
		}
	}

}
