package org.seasar.cubby.cubbitter.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.cubbitter.action.HomeAction;
import org.seasar.cubby.unit.CubbyTestCase;

public class TestTest extends CubbyTestCase {
	// 対象のアクション
	private HomeAction action;

	// 初期化処理
	protected void setUp() throws Exception {
		// diconファイルの読み込み
		include("app.dicon");
	}

	public void testTest1() {
		Matcher matcher1 = createMatcher("@gotouなんとか～");

		assertTrue(matcher1.find());
		assertEquals("gotou", matcher1.group(1));
	}

	public void testTest2() {
		Matcher matcher1 = createMatcher("   @123_asdb ああ");

		assertTrue(matcher1.find());
		assertEquals("123_asdb", matcher1.group(1));
	}

	public void testTest3() {

		Matcher matcher1 = createMatcher(" @123_asdb");

		assertTrue(matcher1.find());
		assertEquals("123_asdb", matcher1.group(1));
	}

	private Matcher createMatcher(String str) {
		Pattern pattern1 = Pattern.compile("^\\s*@([_0-9a-zA-Z]+)");
		return pattern1.matcher(str);
	}
}
