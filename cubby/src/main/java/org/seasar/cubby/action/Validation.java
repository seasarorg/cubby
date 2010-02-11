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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * アクションメソッド実行前に入力検証を行うことを表します。
 * <p>
 * 入力検証を行うことを表明します。 入力検証はアクションの実行前に行われます。
 * <ul>
 * <li>検証に成功した場合 -> アクションメソッドが実行されます。</li>
 * <li>検証に失敗した場合 -> errorPage で指定されたURLへフォワードします。</li>
 * </ul>
 * </p>
 * 
 * @author agata
 * @author baba
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Validation {

	/**
	 * 入力検証でエラーがあった場合にフォワードするパスを指定します。
	 */
	String errorPage() default "";

	/**
	 * 入力検証の定義を取得するプロパティ名を指定します。
	 */
	String rules();
}
