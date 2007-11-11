package org.seasar.cubby.controller;

import static org.seasar.cubby.CubbyConstants.RES_MESSAGES;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.util.ResourceBundleUtil;

public class ThreadContext {

	private static final ThreadLocal<ThreadContext> CONTEXT = new ThreadLocal<ThreadContext>() {

		@Override
		protected ThreadContext initialValue() {
			return new ThreadContext();
		}

	};

	public static void remove() {
		CONTEXT.remove();
	}

	public static HttpServletRequest getRequest() {
		return CONTEXT.get().request;
	}

	public static void setRequest(final HttpServletRequest request) {
		CONTEXT.get().request = request;
	}

	public static ResourceBundle getMessagesResourceBundle() {
		final ThreadContext context = CONTEXT.get();
		if (context.resourceBundle == null) {
			final Locale locale;
			if (context.request == null) {
				locale = Locale.getDefault();
			} else {
				locale = context.request.getLocale();
			}
			context.resourceBundle = ResourceBundleUtil.getBundle(RES_MESSAGES,
					locale);
		}
		return context.resourceBundle;
	}

	public static Map<?, ?> getMessagesMap() {
		final ThreadContext context = CONTEXT.get();
		if (context.messages == null) {
			final ResourceBundle resourceBundle = getMessagesResourceBundle();
			context.messages = ResourceBundleUtil.convertMap(resourceBundle);
		}
		return context.messages;
	}

	private ThreadContext() {
	}

	private HttpServletRequest request;

	private ResourceBundle resourceBundle = null;

	private Map<?, ?> messages = null;

}
