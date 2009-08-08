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
package org.seasar.cubby.showcase.other.web.converter;

import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.converter.impl.AbstractConverter;

/**
 * ISDN を表す文字列と Book を相互変換するコンバータです。
 */
public class BookConverter extends AbstractConverter {

	public BookDao bookDao;

	/**
	 * Book.class を返すことで、このコンバータが任意の値を Book へ変換できることを示します。
	 */
	public Class<?> getObjectType() {
		return Book.class;
	}

	/**
	 * リクエストパラメータ(ここではISBN)を Book へ変換します。
	 */
	public Object convertToObject(Object value, Class<?> objectType,
			ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		String isbn13 = value.toString();
		Book book = bookDao.findByIsbn13(isbn13);
		return book;
	}

	/**
	 * Book から表示用の文字列(ISBN)へ変換します。
	 */
	public String convertToString(Object value, ConversionHelper helper) {
		Book book = Book.class.cast(value);
		return book.getIsbn13();
	}

}
