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

/**
 * HTMLフォームにおけるフィールドの情報です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class FieldInfo {

	/** フィールド名。 */
	private final String name;

	/** インデックス。 */
	private final Integer index;

	/**
	 * 指定されたフィールド名のフィールドの情報をインスタンス化します。
	 * 
	 * @param name
	 *            フィールド名
	 */
	public FieldInfo(final String name) {
		this(name, null);
	}

	/**
	 * 指定されたフィールド名とインデックスのフィールドの情報をインスタンス化します。
	 * 
	 * @param name
	 *            フィールド名
	 * @param index
	 *            インデックス
	 */
	public FieldInfo(final String name, final Integer index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * フィールド名を取得します。
	 * 
	 * @return フィールド名
	 */
	public String getName() {
		return name;
	}

	/**
	 * インデックスを取得します。
	 * <p>
	 * コンストラクタでインデックスを指定しなかった場合は <code>null</code> を返します。
	 * </p>
	 * 
	 * @return インデックス
	 */
	public Integer getIndex() {
		return index;
	}

}
