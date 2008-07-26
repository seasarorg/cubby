package org.seasar.cubby.converter.impl;

import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.converter.Converter;

public abstract class AbstractFormatPatternConverter implements Converter {

	private FormatPattern formatPattern;

	public void setCubbyConfiguration(
			final CubbyConfiguration cubbyConfiguration) {
		this.formatPattern = cubbyConfiguration.getFormatPattern();
	}

	public FormatPattern getFormatPattern() {
		return formatPattern;
	}
}
