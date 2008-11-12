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
 * アクションメソッド呼び出し時にリクエストパラメータがバインドされるオブジェクトや方法を指定します。
 * 
 * <p>
 * この注釈によって、どのようにリクエストパラメータがバインドされるかが変わります。
 * <pre>
 * import static org.seasar.cubby.action.RequestParameterBindingType.*;
 * 
 * public class FooAction {
 * 
 * 	// コンテナの機能によって自動的にインジェクションされる想定です。
 * 	public BarDto barDto;
 * 
 * 	// -&gt; アクション(FooAction)の @RequestParameter で修飾されたプロパティにバインドします。
 *  // よく使用するパターンです。
 * 	public ActionResult m01() {
 * 	}
 * 
 * 	// -&gt; アクション(FooAction)の @RequestParameter で修飾されたプロパティにバインドします。
 * 	&#064;Form(bindingType = ONLY_SPECIFIED_PROPERTIES)
 * 	public ActionResult m02() {
 * 	}
 * 
 * 	// -&gt; アクション(FooAction)の全プロパティにバインドします。
 * 	&#064;Form(bindingType = ALL_PROPERTIES)
 * 	public ActionResult m03() {
 * 	}
 * 
 * 	// リクエストパラメータを barDto の全プロパティにバインドします。
 *  // よく使用するパターンです。
 * 	&#064;Form(&quot;barDto&quot;)
 * 	public ActionResult m11() {
 * 	}
 * 
 * 	// リクエストパラメータを barDto の @RequestParameter で修飾されたプロパティにバインドします。
 * 	&#064;Form(&quot;barDto&quot;
 *             bindingType = ONLY_SPECIFIED_PROPERTIES)
 * 	public ActionResult m12() {
 * 	}
 * 
 * 	// リクエストパラメータを barDto の全プロパティにバインドします。
 * 	&#064;Form(value = &quot;barDto&quot;,
 *             bindingType = ALL_PROPERTIES)
 * 	public ActionResult m13() {
 * 	}
 * 
 * 	// リクエストパラメータをバインドしません。
 * 	&#064;Form(bindingType = NONE)
 * 	public ActionResult m21() {
 * 	}
 * }
 * </pre>
 * </p>
 * 
 * <p>
 * クラスとメソッドの両方に注釈をつけた場合は、メソッドの注釈が優先されます。
 * <pre>
 * &#064;Form(&quot;barDto&quot;)
 * // 全アクションメソッドに対して一括でバインディングの指定を行います。
 * public class Foo2Action {
 * 
 * 	public BarDto barDto;
 * 
 * 	public BazDto bazDto;
 * 
 * 	// リクエストパラメータを barDto のプロパティにバインドします (クラスでの指定が有効なため)。
 * 	public ActionResult m01() {
 * 	}
 * 
 * 	&#064;Form(&quot;bazDto&quot;)
 * 	// リクエストパラメータを bazDto のプロパティにバインドします（アクションメソッドでの指定が優先されるため）。
 * 	public ActionResult m02() {
 * 	}
 * }
 * </pre>
 * </p>
 * 
 * <p>
 * このアノテーションをつけない場合の挙動が 1.0.x と 1.1.x で異なるのでご注意ください。
 * <table>
 * <thead>
 * <tr>
 * <th>バージョン</th>
 * <th>バインド対象のオブジェクト</th>
 * <th>バインド対象のプロパティ</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 * <td>1.0.x</td>
 * <td>アクション</td>
 * <td>すべてのプロパティ</td>
 * </tr>
 * <tr>
 * <td>1.1.x</td>
 * <td>アクション</td>
 * <td>&#64;RequestParameter で修飾されたプロパティ</td>
 * </tr>
 * </tbody>
 * </table>
 * </p>
 *
 * @author agata
 * @since 1.0.0
 * @see RequestParameter
 * @see RequestParameterBindingType
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
	 * リクエストパラメータからフォームオブジェクトへのバインディング方法を指定します。
	 * 
	 * @since 1.1.0
	 */
	RequestParameterBindingType bindingType() default ALL_PROPERTIES;

}
