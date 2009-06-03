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
package org.seasar.cubby.plugins.spring.spi;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.spi.PathResolverProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * {@link SpringPathResolverProvider} のテストです。
 * 
 * @author someda
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/cubby.xml", "/app-cubby.xml",
		"SpringPathResolverProviderTest.xml" })
public class SpringPathResolverProviderTest {

	@Autowired
	private PathResolverProvider pathResolverProvider;

	@Test
	public void getPathResolver1() throws Exception {
		PathResolver actual = pathResolverProvider.getPathResolver();
		assertEquals(PathResolverImpl.class, actual.getClass());

		Collection<Routing> routings = actual.getRoutings();
		assertEquals(4, routings.size());
	}

}
