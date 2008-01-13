/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.controller;

import static org.seasar.cubby.CubbyConstants.RES_MESSAGES;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.container.SingletonS2Container;
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

	public static CubbyConfiguration getConfiguration() {
		return SingletonS2Container.getComponent(CubbyConfiguration.class);
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
