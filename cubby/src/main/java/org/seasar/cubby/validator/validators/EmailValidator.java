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
package org.seasar.cubby.validator.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * Eメールアドレスに対する検証を行います。
 * <p>
 * デフォルトエラーメッセージキー:valid.email
 * </p>
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class EmailValidator implements ScalarFieldValidator {

	private static final String SPECIAL_CHARS = "\\(\\)<>@,;:\\\\\\\"\\.\\[\\]";
	private static final String VALID_CHARS = "[^\\s" + SPECIAL_CHARS + "]";
	private static final String QUOTED_USER = "(\"[^\"]*\")";
	private static final String ATOM = VALID_CHARS + '+';
	private static final String WORD = "(" + ATOM + "|" + QUOTED_USER + ")";

	private static final String LEGAL_ASCII_PATTERN = "^[\\x00-\\x7F]+$";
	private static final String EMAIL_PATTERN = "^(.+)@(.+)$";
	private static final String IP_DOMAIN_PATTERN = "^(\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})$";

	private static final String USER_PATTERN = "^" + WORD + "(\\." + WORD
			+ ")*$";
	private static final String DOMAIN_PATTERN = "^" + ATOM + "(\\." + ATOM
			+ ")*$";
	private static final String ATOM_PATTERN = "(" + ATOM + ")";

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * コンストラクタ
	 */
	public EmailValidator() {
		this("valid.email");
	}

	/**
	 * メッセージキーを指定するコンストラクタ
	 * 
	 * @param messageKey
	 */
	public EmailValidator(final String messageKey) {
		this.messageHelper = new MessageHelper(messageKey);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object value) {
		if (value == null) {
			return;
		}
		if (value instanceof String) {
			final String email = (String) value;
			if (StringUtil.isEmpty(email)) {
				return;
			}

			boolean match = !email.endsWith(".");
			if (match) {
				final Pattern pattern = Pattern.compile(LEGAL_ASCII_PATTERN);
				final Matcher matchAsciiPat = pattern.matcher(email);
				match = matchAsciiPat.matches();
			}

			if (match) {
				final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
				final Matcher matcher = pattern.matcher(email);
				match = matcher.find();
				if (match) {
					if (isValidUser(matcher.group(1))
							&& isValidDomain(matcher.group(2))) {
						return;
					}
				}
			}
		}

		context.addMessageInfo(this.messageHelper.createMessageInfo());
	}

	private boolean isValidDomain(final String domain) {
		Pattern pattern = Pattern.compile(IP_DOMAIN_PATTERN);
		final Matcher ipAddressMatcher = pattern.matcher(domain);

		if (ipAddressMatcher.find()) {
			if (isValidIpAddress(ipAddressMatcher)) {
				return true;
			}
		} else {
			pattern = Pattern.compile(DOMAIN_PATTERN);
			final Matcher domainMatcher = pattern.matcher(domain);
			if (domainMatcher.matches()) {
				if (isValidSymbolicDomain(domain)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isValidUser(final String user) {
		final Pattern pattern = Pattern.compile(USER_PATTERN);
		final Matcher userMatcher = pattern.matcher(user);
		return userMatcher.matches();
	}

	private boolean isValidIpAddress(final Matcher ipAddressMatcher) {
		for (int i = 1; i <= 4; i++) {
			final String ipSegment = ipAddressMatcher.group(i);
			if (ipSegment == null || ipSegment.length() <= 0) {
				return false;
			}

			int iIpSegment = 0;

			try {
				iIpSegment = Integer.parseInt(ipSegment);
			} catch (final NumberFormatException e) {
				return false;
			}

			if (iIpSegment > 255) {
				return false;
			}

		}
		return true;
	}

	private boolean isValidSymbolicDomain(final String domain) {
		final List<String> domainSegments = new ArrayList<String>();
		boolean match = true;

		final Pattern pattern = Pattern.compile(ATOM_PATTERN);
		String domainSegment;
		String currentDomain = domain;
		while (match) {
			final Matcher atomMatcher = pattern.matcher(currentDomain);
			match = atomMatcher.find();
			if (match) {
				domainSegment = atomMatcher.group(1);
				domainSegments.add(domainSegment);
				final int domainSegmentLength = domainSegment.length() + 1;
				currentDomain = domainSegmentLength >= currentDomain.length() ? ""
						: currentDomain.substring(domainSegmentLength);
			}
		}

		final int size = domainSegments.size();
		if (size > 0) {
			final String end = domainSegments.get(size - 1);
			if (end.length() < 2 || end.length() > 4) {
				return false;
			}
		}

		if (size < 2) {
			return false;
		}

		return true;
	}

}
