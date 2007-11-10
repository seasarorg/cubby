package org.seasar.cubby.validator.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.validator.BaseScalarValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * Eメールアドレスに対する検証を行います。
 * <p>
 * デフォルトエラーメッセージキー:valid.email
 * </p>
 * @author agata
 * @author baba
 */
public class EmailValidator extends BaseScalarValidator {

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
		this.setMessageKey(messageKey);
	}

	@Override
	protected void validate(final Object value, final ValidationContext context) {
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
				Pattern pattern = Pattern.compile(LEGAL_ASCII_PATTERN);
				Matcher matchAsciiPat = pattern.matcher(email);
				match = matchAsciiPat.matches();
			}

			if (match) {
				Pattern pattern = Pattern.compile(EMAIL_PATTERN);
				Matcher matcher = pattern.matcher(email);
				match = matcher.find();
				if (match) {
					if (isValidUser(matcher.group(1))
							&& isValidDomain(matcher.group(2))) {
						return;
					}
				}
			}
		}
		context.addMessage(getMessage(getPropertyMessage(context.getName())));
	}

	private boolean isValidDomain(String domain) {
		Pattern pattern = Pattern.compile(IP_DOMAIN_PATTERN);
		Matcher ipAddressMatcher = pattern.matcher(domain);

		if (ipAddressMatcher.find()) {
			if (isValidIpAddress(ipAddressMatcher)) {
				return true;
			}
		} else {
			pattern = Pattern.compile(DOMAIN_PATTERN);
			Matcher domainMatcher = pattern.matcher(domain);
			if (domainMatcher.matches()) {
				if (isValidSymbolicDomain(domain)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isValidUser(String user) {
		Pattern pattern = Pattern.compile(USER_PATTERN);
		Matcher userMatcher = pattern.matcher(user);
		return userMatcher.matches();
	}

	private boolean isValidIpAddress(Matcher ipAddressMatcher) {
		for (int i = 1; i <= 4; i++) {
			String ipSegment = ipAddressMatcher.group(i);
			if (ipSegment == null || ipSegment.length() <= 0) {
				return false;
			}

			int iIpSegment = 0;

			try {
				iIpSegment = Integer.parseInt(ipSegment);
			} catch (NumberFormatException e) {
				return false;
			}

			if (iIpSegment > 255) {
				return false;
			}

		}
		return true;
	}

	private boolean isValidSymbolicDomain(String domain) {
		List<String> domainSegments = new ArrayList<String>();
		boolean match = true;
		int i = 0;

		Pattern pattern = Pattern.compile(ATOM_PATTERN);
		Matcher atomMatcher;
		String ds;
		while (match) {
			atomMatcher = pattern.matcher(domain);
			match = atomMatcher.find();
			if (match) {
				ds = atomMatcher.group(1);
				domainSegments.add(ds);
				int l = ds.length() + 1;
				domain = (l >= domain.length()) ? "" : domain.substring(l);

				i++;
			}
		}

		int size = domainSegments.size();
		if (size > 0) {
			String end = domainSegments.get(size - 1);
			if (end.length() < 2 || end.length() > 4) {
				return false;
			}
		}

		if (domainSegments.size() < 2) {
			return false;
		}

		return true;
	}
}
