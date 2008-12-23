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
package org.seasar.cubby.spi;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Test;

public class ProviderFactoryTest {

	@After
	public void teardown() {
		ProviderFactory.clear();
	}

	@Test
	public void getSingletonInstanceInMultiThread() throws Exception {
		final int size = 500;
		ExecutorService executor = Executors.newFixedThreadPool(size);
		List<Future<FooProvider>> futures = new ArrayList<Future<FooProvider>>(
				size);
		for (int i = 0; i < size; i++) {
			futures.add(executor.submit(new Callable<FooProvider>() {

				public FooProvider call() throws Exception {
					return ProviderFactory.get(FooProvider.class);
				}

			}));
		}
		Iterator<Future<FooProvider>> iterator = futures.iterator();
		FooProvider base = iterator.next().get();
		while (iterator.hasNext()) {
			FooProvider another = iterator.next().get();
			assertSame(base, another);
		}
	}

}
