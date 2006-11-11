package org.seasar.cubby.util;

import static org.seasar.cubby.CubbyConstants.RES_MESSAGES;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Messages {

	public static String getString(ResourceBundle resource, String key, Object... args) {
		return MessageFormat.format(resource.getString(key), args);
	}

	public static String getString(String key, Object... args) {
		ResourceBundle resource = ResourceBundle.getBundle(RES_MESSAGES, LocaleHolder.getLocale());
		return getString(resource, key, args);
	}
}
