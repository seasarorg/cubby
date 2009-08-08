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
package org.seasar.cubby.controller.impl;

import static org.junit.Assert.assertEquals;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Test;
import org.seasar.cubby.controller.MessagesBehaviour;

public class DefaultMessagesBehaviourTest {

	@Test
	public void defaultBaseName() {
		DefaultMessagesBehaviour behaviour = new DefaultMessagesBehaviour();
		assertEquals(DefaultMessagesBehaviour.DEFAULT_RESOURCE_NAME, behaviour
				.getBaseName());
	}

	@Test
	public void baseName() {
		DefaultMessagesBehaviour behaviour = new DefaultMessagesBehaviour();
		String customBaseName = "base";
		behaviour.setBaseName(customBaseName);
		assertEquals(customBaseName, behaviour.getBaseName());
	}

	@Test
	public void getBundleWithDefaultLocale() {
		Locale defaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.JAPANESE);
		MessagesBehaviour behaviour = new DefaultMessagesBehaviour();
		ResourceBundle bundle = behaviour.getBundle(null);
		assertEquals("{0}は{1}以下選択してください。", bundle
				.getString("valid.arrayMaxSize"));
		Locale.setDefault(defaultLocale);
		assertEquals(Locale.JAPANESE, bundle.getLocale());
	}

	@Test
	public void getBundleWithJapaneseLocale() {
		MessagesBehaviour behaviour = new DefaultMessagesBehaviour();
		ResourceBundle bundle = behaviour.getBundle(Locale.JAPANESE);
		assertEquals("{0}は{1}以下選択してください。", bundle
				.getString("valid.arrayMaxSize"));
		assertEquals(Locale.JAPANESE, bundle.getLocale());
	}

	@Test
	public void getBundleWithEnglishLocale() {
		MessagesBehaviour behaviour = new DefaultMessagesBehaviour();
		ResourceBundle bundle = behaviour.getBundle(Locale.ENGLISH);
		assertEquals("{0} : selects <= {1}.", bundle
				.getString("valid.arrayMaxSize"));
		assertEquals(Locale.ENGLISH, bundle.getLocale());
	}

	@Test
	public void testToMap() {
		MessagesBehaviour behaviour = new DefaultMessagesBehaviour();
		ResourceBundle bundle = behaviour.getBundle(Locale.ENGLISH);
		Map<String, Object> map = behaviour.toMap(bundle);
		int bundleSize = 0;
		for (Enumeration<String> keys = bundle.getKeys(); keys
				.hasMoreElements();) {
			String key = keys.nextElement();
			assertEquals(bundle.getObject(key), map.get(key));
			bundleSize++;
		}
		assertEquals(bundleSize, map.size());

	}

}
