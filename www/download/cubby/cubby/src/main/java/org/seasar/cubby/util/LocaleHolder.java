package org.seasar.cubby.util;

import java.util.Locale;

public class LocaleHolder {
	private static final ThreadLocal<Locale> LOCALE = new ThreadLocal<Locale>() {
		@Override
		protected Locale initialValue() {
			return Locale.getDefault();
		}
	};

	public static Locale getLocale() {
		return LOCALE.get();
	}

	public static void setLocale(Locale locale) {
		LOCALE.set(locale);
	}
}
