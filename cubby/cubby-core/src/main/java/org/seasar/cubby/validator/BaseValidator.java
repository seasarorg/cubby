package org.seasar.cubby.validator;

import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.seasar.cubby.util.Messages;

abstract public class BaseValidator implements Validator {

	protected String getMessage(String key, Object... args) {
		String message = Messages.getString(key);
		return MessageFormat.format(message, args);
	}

	protected String getPropertyMessage(String key) {
		try {
			return Messages.getString(key);
		} catch (MissingResourceException ex) {
			return key;
		}
	}
}