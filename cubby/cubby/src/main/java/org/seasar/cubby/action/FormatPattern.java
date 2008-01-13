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

import java.text.DateFormat;

/**
 * 日付や時刻のフォーマットパターンを保持するクラスです。
 * 
 * @author baba
 */
public interface FormatPattern {

	/**
	 * 日付フォーマットパターンを取得します。
	 * 
	 * @return 日付フォーマットパターン
	 */
	String getDatePattern();

	/**
	 * 時刻フォーマットパターンを取得します。
	 * 
	 * @return 時刻フォーマットパターン
	 */
	String getTimePattern();

	/**
	 * 日付時刻フォーマットパターンを取得します。
	 * 
	 * @return 日付時刻パターン
	 */
	String getTimestampPattern();

	/**
	 * 日付フォーマットを取得します。
	 * 
	 * @return 日付フォーマット
	 */
	DateFormat getDateFormat();

	/**
	 * 時刻フォーマットを取得します。
	 * 
	 * @return 時刻フォーマット
	 */
	DateFormat getTimeFormat();

	/**
	 * 日付時刻フォーマットを取得します。
	 * 
	 * @return 日付時刻フォーマット
	 */
	DateFormat getTimestampFormat();

}