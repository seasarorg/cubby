/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.cubby.internal.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;

/**
 * URL ボディ部のエンコーダです。
 * 
 * @author baba
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
	public static final byte[] encodeUrl(BitSet urlsafe, final byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		if (urlsafe == null) {
			urlsafe = WWW_FORM_URL;
		}

		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		for (final byte b : bytes) {
			final int b1 = b;
			final int b2;
			if (b1 < 0) {
				b2 = b1 + 256;
			} else {
				b2 = b1;
			}

			if (urlsafe.get(b2)) {
				buffer.write(b2);
			} else {
				buffer.write('%');
				final char hex1 = Character.toUpperCase(Character.forDigit(
						(b2 >> 4) & 0xF, 16));
				final char hex2 = Character.toUpperCase(Character.forDigit(
						b2 & 0xF, 16));
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
	public static byte[] encode(final byte[] bytes) {
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
	public static String encode(final String pString, final String charset)
			throws UnsupportedEncodingException {
		if (pString == null) {
			return null;
		}
		return new String(encode(pString.getBytes(charset)), "US-ASCII");
	}

}
