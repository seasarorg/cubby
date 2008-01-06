package org.seasar.cubby.unit;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.framework.unit.S2TigerTestCase;

public class CubbyTestCase extends S2TigerTestCase {
	public static<T> void assertPathEquals(Class<T> resultClass, String expectedPath,
			ActionResult actualResult) {
		assertEquals("ActionResultの型をチェック", resultClass, actualResult.getClass());
		if (actualResult instanceof Forward) {
			assertEquals("パスのチェック", expectedPath, ((Forward)actualResult).getPath());
		} else if (actualResult instanceof Redirect) {
			assertEquals("パスのチェック", expectedPath, ((Redirect)actualResult).getPath());
		}
	}
}
