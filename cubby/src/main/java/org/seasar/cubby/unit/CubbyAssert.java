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
 * @since 2.0.0
 */
public class CubbyAssert {

	/**
	 * デフォルトの文字エンコーディング。
	 */
	private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

	/**
	 * ActionResultの型とパスをチェックします。
	 * 
	 * @param resultClass
	 *            ActionResultの型
	 * @param expectedPath
	 *            期待されるパス
	 * @param actualResult
	 *            チェックするActionResult
	 */
	public static void assertPathEquals(
			final Class<? extends ActionResult> resultClass,
			final String expectedPath, final ActionResult actualResult) {
		assertPathEquals(resultClass, expectedPath, actualResult,
				DEFAULT_CHARACTER_ENCODING);
	}

	/**
	 * ActionResultの型とパスをチェックします。
	 * 
	 * @param resultClass
	 *            ActionResultの型
	 * @param expectedPath
	 *            期待されるパス
	 * @param actualResult
	 *            チェックするActionResult
	 * @param characterEncoding
	 *            URI のエンコーディング
	 */
	public static void assertPathEquals(
			final Class<? extends ActionResult> resultClass,
			final String expectedPath, final ActionResult actualResult,
			final String characterEncoding) {

		List<ActionResultAssert<?, String>> asserters = new ArrayList<ActionResultAssert<?, String>>();
		asserters.add(new ForwardAssert());
		asserters.add(new RedirectAssert());
		assertActionResult(resultClass, actualResult, asserters, expectedPath,
				characterEncoding);
	}

	protected static <E> void assertActionResult(
			final Class<? extends ActionResult> resultClass,
			final ActionResult actualResult,
			List<ActionResultAssert<?, E>> asserters, E expected,
			Object... args) {
		Assert.assertNotNull("ActionResult が null でないこと", actualResult);
		Assert.assertEquals("ActionResultの型をチェック", resultClass, actualResult
				.getClass());
		for (ActionResultAssert<?, E> asserter : asserters) {
			asserter.assertType(actualResult, expected, args);
		}
	}

	protected static abstract class ActionResultAssert<T extends ActionResult, E> {

		protected Class<T> clazz;

		public void assertType(ActionResult actualResult, E expected,
				Object... args) {
			if (clazz.isInstance(actualResult)) {
				doAssert(clazz.cast(actualResult), expected, args);
			}
		}

		abstract void doAssert(T actualResult, E expected, Object... args);

	}

	protected static abstract class PathAssert<T extends ActionResult> extends
			ActionResultAssert<T, String> {

		protected void doPathAssert(String expectedPath, String actualPath) {
			Assert.assertEquals("パスのチェック", expectedPath, actualPath);
		}
	}

	private static class ForwardAssert extends PathAssert<Forward> {

		protected ForwardAssert() {
			super.clazz = Forward.class;
		}

		@Override
		void doAssert(Forward actualResult, String expected, Object... args) {
			doPathAssert(expected, actualResult.getPath(args[0].toString()));
		}
	}

	private static class RedirectAssert extends PathAssert<Redirect> {

		protected RedirectAssert() {
			super.clazz = Redirect.class;
		}

		@Override
		void doAssert(Redirect actualResult, String expected, Object... args) {
			doPathAssert(expected, actualResult.getPath(args[0].toString()));
		}
	}

}
