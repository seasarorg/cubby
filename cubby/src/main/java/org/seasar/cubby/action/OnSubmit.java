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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 要求をこの注釈で修飾されたアクションメソッドへ振り分けるための要求パラメータ名を指定します。
 * <p>
 * ひとつのフォームに複数のボタンを配置し、それぞれ異なるアクションメソッドを実行させたい場合に使用します。
 * </p>
 * 
 * <pre>
 * &lt;t:form action=&quot;${contextPath}/todo/save&quot; method=&quot;post&quot; value=&quot;${action}&quot;&gt;
 * (1) &lt;input type=&quot;submit&quot; value=&quot;登録&quot;/&gt;
 * (2) &lt;input type=&quot;submit&quot; name=&quot;confirm_back&quot; value=&quot;戻る&quot;/&gt;
 * &lt;/t:form&gt;
 * </pre>
 * 
 * <pre>
 * public TodoAction extends Action {
 * 
 *   // (1)に対応
 *   public ActionResult save() {
 *   }
 * 
 *   // (2)に対応
 *   &#064;OnSubmit(&quot;confirm_back&quot;)
 *   &#064;Path(&quot;save&quot;)
 * 	 public ActionResult back() {
 *   }
 * 
 * }
 * </pre>
 * 
 * @author baba
 * @since 1.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD })
public @interface OnSubmit {

	/**
	 * 要求パラメータ名を返します。
	 * 
	 * @return 要求パラメータ名
	 */
	String value();

}
