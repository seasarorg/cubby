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
package org.seasar.cubby.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.converter.impl.BigDecimalConverter;
import org.seasar.cubby.converter.impl.BigIntegerConverter;
import org.seasar.cubby.converter.impl.BooleanConverter;
import org.seasar.cubby.converter.impl.ByteArrayFileItemConverter;
import org.seasar.cubby.converter.impl.ByteConverter;
import org.seasar.cubby.converter.impl.CharacterConverter;
import org.seasar.cubby.converter.impl.DateConverter;
import org.seasar.cubby.converter.impl.DoubleConverter;
import org.seasar.cubby.converter.impl.EnumConverter;
import org.seasar.cubby.converter.impl.FloatConverter;
import org.seasar.cubby.converter.impl.InputStreamFileItemConverter;
import org.seasar.cubby.converter.impl.IntegerConverter;
import org.seasar.cubby.converter.impl.LongConverter;
import org.seasar.cubby.converter.impl.ShortConverter;
import org.seasar.cubby.converter.impl.SqlDateConverter;
import org.seasar.cubby.converter.impl.SqlTimeConverter;
import org.seasar.cubby.converter.impl.SqlTimestampConverter;
import org.seasar.cubby.spi.impl.AbstractCachedConverterProvider;

public class MockConverterProvider extends AbstractCachedConverterProvider {

	private final List<Converter> converters;

	private final List<Converter> defaults = Arrays.asList(new Converter[] {
			new BigDecimalConverter(),
			new BigIntegerConverter(),
			new BooleanConverter(),
			new ByteArrayFileItemConverter(),
			new ByteConverter(),
			new CharacterConverter(),
			new DateConverter(),
			new DoubleConverter(),
			new EnumConverter(),
			new FloatConverter(),
			new InputStreamFileItemConverter(),
			new IntegerConverter(),
			new LongConverter(),
			new ShortConverter(),
			new SqlDateConverter(),
			new SqlTimeConverter(),
			new SqlTimestampConverter() });

	public MockConverterProvider(Converter... additionalConverters) {
		final List<Converter> converters = new ArrayList<Converter>();
		converters.addAll(defaults);
		for (final Converter additionalConverter : additionalConverters) {
			converters.add(additionalConverter);
		}
		this.converters = converters;
	}

	@Override
	protected Collection<Converter> getConverters() {
		return converters;
	}

}
