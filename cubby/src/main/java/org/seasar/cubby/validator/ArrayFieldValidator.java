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
package org.seasar.cubby.validator;

/**
 * 複数の値をもつ入力フォームのフィールドに対する入力検証です。
 * 
 * @author baba
 */
public interface ArrayFieldValidator extends Validator {

	/**
	 * 指定された入力値を検証します。
	 * 
	 * @param context
	 *            入力検証のコンテキスト
	 * @param values
	 *            入力値の配列
	 */
	void validate(ValidationContext context, Object[] values);

}
