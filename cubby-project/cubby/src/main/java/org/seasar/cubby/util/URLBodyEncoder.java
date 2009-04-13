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
package org.seasar.cubby.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;

/**
 * <p>
 * Implements the 'www-form-urlencoded' encoding scheme, also misleadingly known
 * as URL encoding.
 * </p>
 * 
 * <p>
 * For more detailed information please refer to <a
 * href="http://www.w3.org/TR/html4/interact/forms.html#h-17.13.4.1"> Chapter
 * 17.13.4 'Form content types'</a> of the <a
 * href="http://www.w3.org/TR/html4/">HTML 4.01 Specification<a>
 * </p>
 * 
 * <p>
 * This codec is meant to be a replacement for standard Java classes
 * {@link java.net.URLEncoder} and {@link java.net.URLDecoder} on older Java
 * platforms, as these classes in Java versions below 1.4 rely on the platform's
 * default charset encoding.
 * </p>
 * 
 * @author Apache Software Foundation
 * @since 1.2
 * @version $Id: URLCodec.java,v 1.19 2004/03/29 07:59:00 ggregory Exp $
 */
public class URLBodyEncoder {

	protected static byte ESCAPE_CHAR = '%';
	/**
	 * BitSet of www-form-url safe characters.
	 */
	protected static final BitSet WWW_FORM_URL = new BitSet(256);

	// Static initializer for www_form_url
	static {
		// alpha characters
		for (int i = 'a'; i <= 'z'; i++) {
			WWW_FORM_URL.set(i);
		}
		for (int i = 'A'; i <= 'Z'; i++) {
			WWW_FORM_URL.set(i);
		}
		// numeric characters
		for (int i = '0'; i <= '9'; i++) {
			WWW_FORM_URL.set(i);
		}
		// special chars
		WWW_FORM_URL.set('-');
		WWW_FORM_URL.set('_');
		WWW_FORM_URL.set('.');
		WWW_FORM_URL.set('*');
		// blank to be replaced with +
		// WWW_FORM_URL.set(' ');
	}

	/**
	 * Default constructor.
	 */
	private URLBodyEncoder() {
		super();
	}

	/**
	 * Encodes an array of bytes into an array of URL safe 7-bit characters.
	 * Unsafe characters are escaped.
	 * 
	 * @param urlsafe
	 *            bitset of characters deemed URL safe
	 * @param bytes
	 *            array of bytes to convert to URL safe characters
	 * @return array of bytes containing URL safe characters
	 */
	public static final byte[] encodeUrl(BitSet urlsafe, byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		if (urlsafe == null) {
			urlsafe = WWW_FORM_URL;
		}

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		for (int i = 0; i < bytes.length; i++) {
			int b = bytes[i];
			if (b < 0) {
				b = 256 + b;
			}
			if (urlsafe.get(b)) {
				// if (b == ' ') {
				// b = '+';
				// }
				buffer.write(b);
			} else {
				buffer.write('%');
				char hex1 = Character.toUpperCase(Character.forDigit(
						(b >> 4) & 0xF, 16));
				char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF,
						16));
				buffer.write(hex1);
				buffer.write(hex2);
			}
		}
		return buffer.toByteArray();
	}

	/**
	 * Encodes an array of bytes into an array of URL safe 7-bit characters.
	 * Unsafe characters are escaped.
	 * 
	 * @param bytes
	 *            array of bytes to convert to URL safe characters
	 * @return array of bytes containing URL safe characters
	 */
	public static byte[] encode(byte[] bytes) {
		return encodeUrl(WWW_FORM_URL, bytes);
	}

	/**
	 * Encodes a string into its URL safe form using the specified string
	 * charset. Unsafe characters are escaped.
	 * 
	 * @param pString
	 *            string to convert to a URL safe form
	 * @param charset
	 *            the charset for pString
	 * @return URL safe string
	 * @throws UnsupportedEncodingException
	 *             Thrown if charset is not supported
	 */
	public static String encode(String pString, String charset)
			throws UnsupportedEncodingException {
		if (pString == null) {
			return null;
		}
		return new String(encode(pString.getBytes(charset)), "US-ASCII");
	}

}
