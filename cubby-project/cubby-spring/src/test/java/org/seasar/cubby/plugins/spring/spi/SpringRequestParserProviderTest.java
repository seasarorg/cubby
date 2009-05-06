package org.seasar.cubby.plugins.spring.spi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.impl.DefaultRequestParser;
import org.seasar.cubby.controller.impl.MultipartRequestParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * {@link SpringRequestParserProvider} のテストです。
 * 
 * @author someda
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/cubby.xml",
		"SpringRequestParserProviderTest.xml" })
public class SpringRequestParserProviderTest {

	@Autowired
	private SpringRequestParserProvider requestParserProvider;

	@Test
	public void getRequestParsers1() throws Exception {
		Collection<RequestParser> actual = requestParserProvider
				.getRequestParsers();
		assertNotNull(actual);
		assertEquals(2, actual.size());
		Object[] array = actual.toArray();
		assertEquals(MultipartRequestParser.class, array[0].getClass());
		assertEquals(DefaultRequestParser.class, array[1].getClass());
	}

}
