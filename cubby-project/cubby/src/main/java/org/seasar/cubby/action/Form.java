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

import static org.seasar.cubby.action.RequestParameterBindingType.ALL_PROPERTIES;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * リクエストパラメータがバインディングされるオブジェクトを指定します。
 * 
 * <pre>
 * public class FooAction {
 * 
 * 	// コンテナの機能によって自動的にインジェクションされる想定です。
 * 	public BarDto BarDto;
 * 
 * 	// -&gt; アクション自身(FooAction)のプロパティにバインディングします。
 * 	public ActionResult m1() {
 * 	}
 * 
 * 	// -&gt; アクション自身(FooAction)のプロパティにバインディングします。
 * 	&#064;Form
 * 	public ActionResult m2() {
 * 	}
 * 
 * 	// barDto のプロパティにバインディングします。
 * 	&#064;Form(&quot;barDto&quot;)
 * 	public ActionResult m3() {
 * 	}
 * 
 * 	// バインディングしません。
 * 	&#064;Form(binding = false)
 * 	public ActionResult m4() {
 * 	}
 * }
 * 
 * &#064;Form(&quot;barDto&quot;)
 * // 全アクションメソッドに対して一括でバインディングの指定を行います。
 * public class Foo2Action {
 * 
 * 	public BarDto barDto;
 * 
 * 	public BazDto bazDto;
 * 
 * 	// barDto のプロパティにバインディングします (クラスでの指定が有効なため)。
 * 	public ActionResult m1() {
 * 	}
 * 
 * 	&#064;Form(&quot;bazDto&quot;)
 * 	// bazDto のプロパティにバインディングします（アクションメソッドでの指定が優先されるため）。
 * 	public ActionResult m2() {
 * 	}
 * }
 * </pre>
 * 
 * @author agata
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.TYPE })
public @interface Form {
	/** アクションメソッド自身にリクエストパラメータがバインディングされることを表します */
	public static final String THIS = "this";

	/**
	 * バインディングするオブジェクトのプロパティ名。
	 * <p>
	 * "this" が指定された場合は、アクションクラス自身にリクエストパラメータがバインディングされることを表します。
	 * </p>
	 */
	String value() default THIS;

	/**
	 * リクエストパラメータからフォームオブジェクトへのバインディングするかを示します。
	 * <p>
	 * <code>false</code> が指定された場合はフォームオブジェクトへのバインディングを行いません。
	 * </p>
	 */
	boolean binding() default true;

	/**
	 * リクエストパラメータからフォームオブジェクトへのバインディング方法を指定します。
	 */
	RequestParameterBindingType type() default ALL_PROPERTIES;

}
