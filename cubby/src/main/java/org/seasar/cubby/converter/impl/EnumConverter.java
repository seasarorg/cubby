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
package org.seasar.cubby.converter.impl;

import org.seasar.cubby.converter.ConversionHelper;

/**
 * 列挙定数へ変換するコンバータです。
 * 
 * @author baba
 * @since 1.1.0
 */
public class EnumConverter extends AbstractConverter {

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getObjectType() {
		return Enum.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Object convertToObject(final Object value,
			final Class<?> objectType, final ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		final String name = value.toString();
		return Enum.valueOf((Class<? extends Enum>) objectType, name);
	}

	/**
	 * {@inheritDoc}
	 */
	public String convertToString(final Object value,
			final ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		return Enum.class.cast(value).name();
	}

}
