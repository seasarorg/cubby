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

import java.math.BigDecimal;

/**
 * {@link Double}への変換を行うコンバータです。
 * 
 * @author baba
 */
public class DoubleConverter extends AbstractDecimalNumberConverter {

	/** 最小値。 */
	private static final BigDecimal MIN_VALUE = new BigDecimal(Double.MIN_VALUE);

	/** 最大値。 */
	private static final BigDecimal MAX_VALUE = new BigDecimal(Double.MAX_VALUE);

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getObjectType() {
		return Double.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Number convert(final BigDecimal decimal) {
		return new Double(decimal.doubleValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BigDecimal getMinValue() {
		return MIN_VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BigDecimal getMaxValue() {
		return MAX_VALUE;
	}

}
