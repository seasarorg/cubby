package org.seasar.cubby.cubbitter.util;

public class JPQLUtils {

	public static String escapeLike(String literal) {
		return escapeLike(literal, '\\');
	}

	public static String escapeLike(String literal, char escapeCharacter) {
		StringBuilder builder = new StringBuilder();
		for (char c : literal.toCharArray()) {
			if (c == '%') {
				builder.append(escapeCharacter);
			}
			builder.append(c);
		}
		return builder.toString();
	}

}
