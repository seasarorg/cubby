/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
 * アクションメソッド呼び出し時に要求パラメータがバインドされるオブジェクトや方法を指定します。
 * 
 * <p>
 * この注釈によって、どのように要求パラメータがバインドされるかが変わります。
 * 
 * <pre>
 * import static org.seasar.cubby.action.RequestParameterBindingType.*;
 * 
 * public class FooAction {
 * 
 * 	// コンテナの機能によって自動的にインジェクションされる想定です。
 * 	public BarDto barDto;
 * 
 * 	// -&gt; アクション(FooAction)の @RequestParameter で修飾されたプロパティとフィールドにバインドします。
 *  // よく使用するパターンです。
 * 	public ActionResult m01() {
 * 	}
 * 
 * 	// -&gt; アクション(FooAction)の @RequestParameter で修飾されたプロパティとフィールドにバインドします。
 * 	&#064;Form(bindingType = ONLY_SPECIFIED_PROPERTIES)
 * 	public ActionResult m02() {
 * 	}
 * 
 * 	// -&gt; アクション(FooAction)のすべてのプロパティにバインドします。フィールドにはバインドしません。
 * 	&#064;Form(bindingType = ALL_PROPERTIES)
 * 	public ActionResult m03() {
 * 	}
 * 
 * 	// -&gt; 要求パラメータを barDto の全プロパティにバインドします。フィールドにはバインドしません。
 *  // よく使用するパターンです。
 * 	&#064;Form(&quot;barDto&quot;)
 * 	public ActionResult m11() {
 * 	}
 * 
 * 	// 要求パラメータを barDto の @RequestParameter で修飾されたプロパティとフィールドにバインドします。
 * 	&#064;Form(&quot;barDto&quot;
 *             bindingType = ONLY_SPECIFIED_PROPERTIES)
 * 	public ActionResult m12() {
 * 	}
 * 
 * 	// 要求パラメータを barDto の全プロパティにバインドします。フィールドにはバインドしません。
 * 	&#064;Form(value = &quot;barDto&quot;,
 *             bindingType = ALL_PROPERTIES)
 * 	public ActionResult m13() {
 * 	}
 * 
 * 	// 要求パラメータをバインドしません。
 * 	&#064;Form(bindingType = NONE)
 * 	public ActionResult m21() {
 * 	}
 * }
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * クラスとメソッドの両方に注釈をつけた場合は、メソッドの注釈が優先されます。
 * 
 * <pre>
 * &#064;Form(&quot;barDto&quot;)
 * // 全アクションメソッドに対して一括でバインディングの指定を行います。
 * public class Foo2Action {
 * 
 * 	public BarDto barDto;
 * 
 * 	public BazDto bazDto;
 * 
 * 	// 要求パラメータを barDto のプロパティにバインドします (クラスでの指定が有効なため)。
 * 	public ActionResult m01() {
 * 	}
 * 
 * 	&#064;Form(&quot;bazDto&quot;)
 * 	// 要求パラメータを bazDto のプロパティにバインドします（アクションメソッドでの指定が優先されるため）。
 * 	public ActionResult m02() {
 * 	}
 * }
 * </pre>
 * 
 * </p>
 * 
 * @author agata
 * @see RequestParameter
 * @see RequestParameterBindingType
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.TYPE })
public @interface Form {
	/** アクションメソッド自身に要求パラメータがバインディングされることを表します */
	public static final String THIS = "this";

	/**
	 * バインディングするオブジェクトのプロパティ名。
	 * <p>
	 * "this" が指定された場合は、アクションクラス自身に要求パラメータがバインディングされることを表します。
	 * </p>
	 */
	String value() default THIS;

	/**
	 * 要求パラメータからフォームオブジェクトへのバインディング方法を指定します。
	 */
	RequestParameterBindingType bindingType() default ALL_PROPERTIES;

}
