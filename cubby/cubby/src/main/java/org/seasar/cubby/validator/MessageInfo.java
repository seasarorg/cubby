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
package org.seasar.cubby.validator;

import org.seasar.cubby.util.Messages;

public class MessageInfo {

	private String key;

	private Object[] arguments;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object[] getArguments() {
		if (arguments == null) {
			return null;
		}
		return arguments.clone();
	}

	public void setArguments(Object... arguments) {
		this.arguments = arguments;
	}

	public MessageBuilder builder() {
		MessageBuilder builder = new MessageBuilder(key, arguments);
		return builder;
	}

	public class MessageBuilder {

		private final String messageKey;

		private String fieldNameKey;

		public final Object[] arguments;

		private MessageBuilder(final String messageKey, final Object[] arguments) {
			this.messageKey = messageKey;
			this.arguments = arguments;
		}

		public MessageBuilder fieldNameKey(final String fieldNameKey) {
			this.fieldNameKey = fieldNameKey;
			return this;
		}

		public String toString() {
			final Object[] args;
			if (fieldNameKey != null) {
				args = new Object[this.arguments.length + 1];
				final String paramNameText = Messages.getText(fieldNameKey);
				args[0] = paramNameText;
				System.arraycopy(this.arguments, 0, args, 1, this.arguments.length);
			} else {
				args = this.arguments;
			}
			return Messages.getText(messageKey, args);
		}
	}
}
