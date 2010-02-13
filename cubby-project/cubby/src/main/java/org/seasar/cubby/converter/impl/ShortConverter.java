/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.math.BigInteger;

/**
 * {@link Short}への変換を行うコンバータです。
 * 
 * @author baba
 */
public class ShortConverter extends AbstractIntegerNumberConverter {

	/** 最小値。 */
	private static final BigInteger MIN_VALUE = BigInteger
			.valueOf(Short.MIN_VALUE);

	/** 最大値。 */
	private static final BigInteger MAX_VALUE = BigInteger
			.valueOf(Short.MAX_VALUE);

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getObjectType() {
		return Short.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Number convert(final BigInteger integer) {
		return Short.valueOf(integer.shortValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BigInteger getMinValue() {
		return MIN_VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BigInteger getMaxValue() {
		return MAX_VALUE;
	}

}
