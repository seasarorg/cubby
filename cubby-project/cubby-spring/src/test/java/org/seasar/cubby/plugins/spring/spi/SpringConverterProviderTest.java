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

/**
 * {@link SpringContainerProvider} のテストです。
 * 
 * @author someda
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/cubby.xml", "/cubby-converters.xml",
		"/app-cubby.xml" })
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
