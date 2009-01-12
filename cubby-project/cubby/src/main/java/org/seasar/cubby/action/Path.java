/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * アクションメソッドのパス。
 * <p>
 * アクションメソッドを起動するためのパスを指定するアノテーションです。
 * </p>
 * <p>
 * 使用例
 * 
 * <pre>
 * &#064;Path(&quot;fuga&quot;)
 * public class HogeAction {
 * 	// -&gt; &quot;/fuga/index&quot;
 * 	public ActionResult index() {
 * 	}
 * 
 * 	// -&gt; &quot;/fuga/m1&quot;
 * 	public ActionResult m1() {
 * 	}
 * 
 * 	&#064;Path(&quot;list&quot;)
 * 	// -&gt; &quot;/fuga/list&quot;
 * 	public ActionResult m2() {
 * 	}
 * 
 * 	&#064;Path(&quot;/xxx/yyy&quot;)
 * 	// -&gt; &quot;/xxx/yyy&quot;
 * 	public ActionResult m3() {
 * 	}
 * 
 * 	&#064;Path(&quot;/{id}/edit&quot;)
 * 	// {id}部分をリクエストパラメータに追加。
 *  // {id}部分の正規表現は省略されているためデフォルトの「[0-9a-zA-Z]+」。
 *  //　priority(優先度)はデフォルト値の「Integer.MAX_VALUE」。
 * 	public ActionResult m4() {
 * 	}
 * 
 * 	&#064;Path(&quot;/{userId,[a-z]+}/edit&quot;, priprity=0)
 * 	// {userId}部分をリクエストパラメータに追加。
 *  // {userId}部分の正規表現は「[a-z]+」のため小文字アルファベットのみ。
 *  //　priority(優先度)は「0」のため、m4メソッドよりも先に適用される。
 * 	public ActionResult m5() {
 * 	}
 * }
 * 
 * &#064;Path(&quot;/&quot;)
 * public class RootAction {
 * 	// -&gt; &quot;/&quot;
 * 	public ActionResult index() {
 * 	}
 * 
 * 	// -&gt; &quot;/m1&quot;
 * 	public ActionResult m1() {
 * 	}
 * 
 * 	&#064;Path(&quot;list&quot;)
 * 	// -&gt; &quot;/list&quot;
 * 	public ActionResult m2() {
 * 	}
 * 
 * 	&#064;Path(&quot;/xxx/yyy&quot;)
 * 	// -&gt; &quot;/xxx/yyy&quot;
 * 	public ActionResult m3() {
 * 	}
 * }
 * </pre>
 * 
 * </p>
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.TYPE })
public @interface Path {

	/**
	 * アクションメソッドのバインディング用パスを指定します。
	 * <p>
	 * URLはアクションクラスのパス＋アクションメソッドのパスで決定されます。
	 * ただし、先頭が『/』の場合コンテキストルートからの絶対パスとして解釈されます。
	 * </p>
	 * <p>
	 * {パラメータ名,正規表現}でプレースホルダーの指定ができます。
	 * </p>
	 * <p>
	 * 正規表現にマッチした場合、マッチした箇所が指定されたパラメータ名に追加され、アクションメソッドが実行されます。
	 * 正規表現は省略可能です。省略した場合「[a-zA-Z0-9]+」と同じ意味になります。
	 * </p>
	 * <p>
	 * アクションメソッドのパスは「パスの正規表現+{@link Accept リクエストメソッド}」で一意に特定できなければいけません。
	 * 実行時に重複が発見されると例外が発生します。
	 * </p>
	 * 
	 * @return アクションメソッドのバインディング用パス
	 * @see Accept
	 */
	String value() default "";

	/**
	 * アクションメソッドのバインディング時の優先度を設定します。
	 * <p>
	 * この値が小さいほど、優先度が高く先に適用されます。デフォルト値は{@link Integer#MAX_VALUE}です。
	 * </p>
	 * <p>
	 * 手動でバインディングの設定が追加された場合、優先度は0から順番に振られます。手動追加よりも自動設定の優先度を上げたい場合は、
	 * 負の値を設定してください。
	 * </p>
	 * 
	 * @return アクションメソッドのバインディング時の優先度
	 */
	int priority() default Integer.MAX_VALUE;

}
