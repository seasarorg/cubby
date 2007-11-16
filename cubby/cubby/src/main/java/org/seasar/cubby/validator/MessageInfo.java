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
