package org.seasar.cubby.util;

import java.util.ResourceBundle;

public class SystemMessagedException extends AbstractMessagedException {

	private static final long serialVersionUID = -8220794831343289148L;

	public SystemMessagedException(String code) {
		super(ResourceBundle.getBundle("cubby-messages", LocaleHolder.getLocale()), code);
	}

	public SystemMessagedException(String code, Throwable cause) {
		super(ResourceBundle.getBundle("cubby-messages", LocaleHolder.getLocale()), code, cause);
	}
}