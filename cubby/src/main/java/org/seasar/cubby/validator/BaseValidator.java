package org.seasar.cubby.validator;

import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.seasar.cubby.util.Messages;

abstract public class BaseValidator implements Validator {

	private String messageKey;

	protected void setMessageKey(final String messageKey) {
		this.messageKey = messageKey;
	}

	protected String getMessage(final Object... args) {
		String message = Messages.getText(messageKey);
		return MessageFormat.format(message, args);
	}

	protected String getPropertyMessage(final String key) {
		try {
			return Messages.getText(key);
		} catch (MissingResourceException ex) {
			return key;
		}
	}

}