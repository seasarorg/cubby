package org.seasar.cubby.action.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.seasar.cubby.action.FormatPattern;

/**
 * 日付や時刻のフォーマットパターンを保持するクラスの実装です。
 * @author baba
 */
public class FormatPatternImpl implements FormatPattern {

	/**
	 * 日付フォーマットパターン
	 */
	private String datePattern = "yyyy-MM-dd";

	/**
	 * 時刻フォーマットパターン
	 */
	private String timePattern = "HH:mm:ss";

	/**
	 * 日付時刻フォーマットパターン
	 */
	private String timestampPattern = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日付フォーマットパターンを取得します。
	 */
	public String getDatePattern() {
		return datePattern;
	}

	/**
	 * 日付フォーマットパターンをセットします。
	 * @param datePattern　日付フォーマットパターン
	 */
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	/**
	 * 時刻フォーマットパターンを取得します。
	 */
	public String getTimePattern() {
		return timePattern;
	}

	/**
	 * 時刻フォーマットパターンをセットします。
	 * @param timePattern　時刻フォーマットパターン
	 */
	public void setTimePattern(String timePattern) {
		this.timePattern = timePattern;
	}

	/**
	 * 日付時刻フォーマットパターンを取得します。
	 */
	public String getTimestampPattern() {
		return timestampPattern;
	}

	/**
	 * 日付時刻フォーマットパターンをセットします。
	 * @param timestampPattern　日付時刻フォーマットパターン
	 */
	public void setTimestampPattern(String timestampPattern) {
		this.timestampPattern = timestampPattern;
	}

	/**
	 * 日付フォーマットを取得します。
	 */
	public DateFormat getDateFormat() {
		return new SimpleDateFormat(this.datePattern);
	}

	/**
	 * 時刻フォーマットを取得します。
	 */
	public DateFormat getTimeFormat() {
		return new SimpleDateFormat(this.timePattern);
	}

	/**
	 * 日付時刻フォーマットを取得します。
	 */
	public DateFormat getTimestampFormat() {
		return new SimpleDateFormat(this.timestampPattern);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("[datePattern=");
		builder.append(datePattern);
		builder.append(",timePattern=");
		builder.append(timePattern);
		builder.append(",timestampPattern=");
		builder.append(timestampPattern);
		builder.append("]");
		return builder.toString();
	}

}
