package org.seasar.cubby.validator.validators;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.FieldInfo;
import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.action.impl.FormatPatternImpl;
import org.seasar.cubby.validator.MessageInfo;
import org.seasar.cubby.validator.ValidationContext;

public class MockValidationContext implements ValidationContext {

	private final Action action;

	private final FormatPattern formatPattern = new FormatPatternImpl();

	public MockValidationContext(final Action action) {
		this.action = action;
	}

	public FormatPattern getFormatPattern() {
		return formatPattern;
	}

	public void addMessageInfo(final MessageInfo messageInfo) {
		this.action.getErrors().add(messageInfo.toString(),
				new FieldInfo("mock"));
	}

}
