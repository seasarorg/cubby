package org.seasar.cubby.action;

import java.text.DateFormat;

public interface FormatPattern {

	String getDatePattern();

	String getTimePattern();

	String getTimestampPattern();

	DateFormat getDateFormat();

	DateFormat getTimeFormat();

	DateFormat getTimestampFormat();

}