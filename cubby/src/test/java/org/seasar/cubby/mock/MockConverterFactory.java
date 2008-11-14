package org.seasar.cubby.mock;

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
import org.seasar.cubby.factory.impl.AbstractConverterFactory;

public class MockConverterFactory extends AbstractConverterFactory {

	private List<Converter> converters = Arrays.asList(new Converter[] {
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

	@Override
	protected Collection<Converter> getConverters() {
		return converters;
	}

}
