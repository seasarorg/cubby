package org.seasar.cubby.plugins.spring.spi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.converter.impl.BooleanConverter;
import org.seasar.cubby.spi.ConverterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/cubby.xml", "/cubby-converters.xml" })
public class SpringConverterProviderTest {

	@Autowired
	private ConverterProvider converterProvider;

	@Test
	public void getConverter1() throws Exception {
		Converter actual = converterProvider
				.getConverter(BooleanConverter.class);
		assertNotNull(actual);
		assertEquals(BooleanConverter.class, actual.getClass());
	}

}
