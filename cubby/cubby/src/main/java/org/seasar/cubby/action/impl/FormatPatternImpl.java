package org.seasar.cubby.action.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.seasar.cubby.action.FormatPattern;

public class FormatPatternImpl implements FormatPattern {

	private String datePattern = "yyyy-MM-dd";

	private String timePattern = "HH:mm:ss.SSS";

	private String timestampPattern = "yyyy-MM-dd HH:mm:ss.SSS";

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public String getTimePattern() {
		return timePattern;
	}

	public void setTimePattern(String timePattern) {
		this.timePattern = timePattern;
	}

	public String getTimestampPattern() {
		return timestampPattern;
	}

	public void setTimestampPattern(String timestampPattern) {
		this.timestampPattern = timestampPattern;
	}

	public DateFormat getDateFormat() {
		return new SimpleDateFormat(this.datePattern);
	}

	public DateFormat getTimeFormat() {
		return new SimpleDateFormat(this.timePattern);
	}

	public DateFormat getTimestampFormat() {
		return new SimpleDateFormat(this.timestampPattern);
	}

}
