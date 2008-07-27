package org.seasar.cubby.converter.impl;

import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.controller.CubbyConfiguration;

public abstract class AbstractFormatPatternConverter extends AbstractConverter {

	private FormatPattern formatPattern;

	public void setCubbyConfiguration(
			final CubbyConfiguration cubbyConfiguration) {
		this.formatPattern = cubbyConfiguration.getFormatPattern();
	}

	public FormatPattern getFormatPattern() {
		return formatPattern;
	}
}
