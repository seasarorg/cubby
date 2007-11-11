package org.seasar.cubby.controller;

import org.seasar.cubby.action.FormatPattern;

public interface CubbyConfiguration {

	RequestParser getRequestParser();

	FormatPattern getFormatPattern();

}