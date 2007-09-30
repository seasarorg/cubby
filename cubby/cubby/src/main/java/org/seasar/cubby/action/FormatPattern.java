package org.seasar.cubby.action;

import java.text.DateFormat;

/**
 * 日付や時刻のフォーマットパターンを保持するクラスです。
 * @author baba
 */
public interface FormatPattern {

	/**
	 * 日付フォーマットパターンを取得します。
	 * @return　日付フォーマットパターン
	 */
	String getDatePattern();

	/**
	 * 時刻フォーマットパターンを取得します。
	 * @return 時刻フォーマットパターン
	 */
	String getTimePattern();

	/**
	 * 日付時刻フォーマットパターンを取得します。
	 * @return 日付時刻パターン
	 */
	String getTimestampPattern();

	/**
	 * 日付フォーマットを取得します。
	 * @return 日付フォーマット
	 */
	DateFormat getDateFormat();

	/**
	 * 時刻フォーマットを取得します。
	 * @return 時刻フォーマット
	 */
	DateFormat getTimeFormat();

	/**
	 * 日付時刻フォーマットを取得します。
	 * @return 日付時刻フォーマット
	 */
	DateFormat getTimestampFormat();

}