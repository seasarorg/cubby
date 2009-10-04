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
package org.seasar.cubby.unit;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;

/**
 * アクションのテストを行うための検証メソッドの集合です。
 * 
 * @author someda
 * @author baba
 */
public class CubbyAssert {

	/**
	 * デフォルトの文字エンコーディング。
	 */
	private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

	/**
	 * 指定された {@link ActionResult} の型とパスを検証します。
	 * 
	 * @param expectedType
	 *            期待する <code>ActionResult</code> の型
	 * @param expectedPath
	 *            期待する <code>ActionResult</code> のパス
	 * @param actual
	 *            実際の <code>ActionResult</code>
	 */
	public static void assertPathEquals(
			final Class<? extends ActionResult> expectedType,
			final String expectedPath, final ActionResult actual) {
		assertPathEquals(expectedType, expectedPath, actual,
				DEFAULT_CHARACTER_ENCODING);
	}

	/**
	 * 指定された {@link ActionResult} の型とパスを検証します。
	 * 
	 * @param message
	 *            メッセージ
	 * @param expectedType
	 *            期待する <code>ActionResult</code> の型
	 * @param expectedPath
	 *            期待する <code>ActionResult</code> のパス
	 * @param actual
	 *            実際の <code>ActionResult</code>
	 * @since 2.0.2
	 */
	public static void assertPathEquals(final String message,
			final Class<? extends ActionResult> expectedType,
			final String expectedPath, final ActionResult actual) {
		assertPathEquals(message, expectedType, expectedPath, actual,
				DEFAULT_CHARACTER_ENCODING);
	}

	/**
	 * 指定された {@link ActionResult} の型とパスを検証します。
	 * 
	 * @param expectedType
	 *            期待する <code>ActionResult</code> の型
	 * @param expectedPath
	 *            期待する <code>ActionResult</code> のパス
	 * @param actual
	 *            実際の <code>ActionResult</code>
	 * @param characterEncoding
	 *            URI のエンコーディング
	 */
	public static void assertPathEquals(
			final Class<? extends ActionResult> expectedType,
			final String expectedPath, final ActionResult actual,
			final String characterEncoding) {

		assertPathEquals(null, expectedType, expectedPath, actual,
				characterEncoding);
	}

	/**
	 * 指定された {@link ActionResult} の型とパスを検証します。
	 * 
	 * @param message
	 *            メッセージ
	 * @param expectedType
	 *            期待する <code>ActionResult</code> の型
	 * @param expectedPath
	 *            期待する <code>ActionResult</code> のパス
	 * @param actual
	 *            実際の <code>ActionResult</code>
	 * @param characterEncoding
	 *            URI のエンコーディング
	 * @since 2.0.2
	 */
	public static void assertPathEquals(final String message,
			final Class<? extends ActionResult> expectedType,
			final String expectedPath, final ActionResult actual,
			final String characterEncoding) {

		final List<ActionResultAssert<?, String>> asserters = new ArrayList<ActionResultAssert<?, String>>();
		asserters.add(new ForwardAssert());
		asserters.add(new RedirectAssert());
		assertActionResult(message, expectedType, actual, asserters,
				expectedPath, characterEncoding);
	}

	protected static <E> void assertActionResult(final String message,
			final Class<? extends ActionResult> expectedType,
			final ActionResult actualResult,
			final List<ActionResultAssert<?, E>> asserters, final E expected,
			final Object... args) {
		Assert.assertNotNull(message, actualResult);
		Assert.assertEquals(message, expectedType, actualResult.getClass());
		for (final ActionResultAssert<?, E> asserter : asserters) {
			asserter.assertType(message, actualResult, expected, args);
		}
	}

	protected static abstract class ActionResultAssert<T extends ActionResult, E> {

		private final Class<T> clazz;

		ActionResultAssert(Class<T> clazz) {
			this.clazz = clazz;
		}

		public void assertType(final String message,
				final ActionResult actualResult, final E expected,
				final Object... args) {
			if (clazz.isInstance(actualResult)) {
				doAssert(message, clazz.cast(actualResult), expected, args);
			}
		}

		abstract void doAssert(String message, T actualResult, E expected,
				Object... args);

	}

	protected static abstract class PathAssert<T extends ActionResult> extends
			ActionResultAssert<T, String> {

		PathAssert(final Class<T> clazz) {
			super(clazz);
		}

		protected void doPathAssert(final String message,
				final String expectedPath, final String actualPath) {
			Assert.assertEquals(message, expectedPath, actualPath);
		}
	}

	private static class ForwardAssert extends PathAssert<Forward> {

		protected ForwardAssert() {
			super(Forward.class);
		}

		@Override
		void doAssert(final String message, final Forward actualResult,
				final String expected, final Object... args) {
			doPathAssert(message, expected, actualResult.getPath(args[0]
					.toString()));
		}

	}

	private static class RedirectAssert extends PathAssert<Redirect> {

		protected RedirectAssert() {
			super(Redirect.class);
		}

		@Override
		void doAssert(final String message, final Redirect actualResult,
				final String expected, final Object... args) {
			doPathAssert(message, expected, actualResult.getPath(args[0]
					.toString()));
		}

	}

}
