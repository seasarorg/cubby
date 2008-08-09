package org.seasar.cubby.cubbitter.action;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.cubbitter.action.HomeAction;
import org.seasar.cubby.unit.CubbyTestCase;

public class HomeActionTest extends CubbyTestCase {
	// 対象のアクション
	private HomeAction action;

	// 初期化処理
	protected void setUp() throws Exception {
		// diconファイルの読み込み
		include("app.dicon");
	}

	@Override
	protected void setUpAfterBindFields() throws Throwable {
		super.setUpAfterBindFields();
		getRequest().addParameter("memberName", "aaaa");
		getRequest().addParameter("password", "passworda");
		// 後続のテストを実行するためにログインアクションを実行
		processAction("/login/");

	}

	public void testLoginOk() throws Exception {
		ActionResult result = processAction("/home/");

		// メンバー
		Object obj[][] = new Object[][] { { 3, "ccc", "しさん" },
				{ 4, "ddd", "でさん" } };
		assertEquals(java.util.Arrays
				.asList(Conv.convertArrayToMemberMaps(obj)),
				action.followingList);

		obj = new Object[][] {
				{ 3, "ccc", "しさん", 5, "えええええええええええええええええええ",
						Timestamp.valueOf("2008-03-06 16:02:40.453"),false , true },
				{ 1, "aaaa", "あさん", 4,
						"うううううううううううううううううううううううううううううううううううううううう",
						Timestamp.valueOf("2008-03-06 16:02:25.203"), true, true },
				{ 1, "aaaa", "あさん", 2, "ああああああああああああああ",
						Timestamp.valueOf("2008-03-06 16:01:27.593"), true, false },
				{ 1, "aaaa", "あさん", 1, "コメント",
						Timestamp.valueOf("2008-03-06 15:46:26.171"), true, false } };
		assertEquals(java.util.Arrays.asList(Conv
				.convertArrayToCommentMaps(obj)), action.commentList);

		assertPathEquals(Forward.class, "index.jsp", result);
	}

	public void testCommentNG() throws Exception {
		// パラメータ
		getRequest()
				.addParameter(
						"comment",
						"123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901");
		// アクションの実行
		ActionResult result = processAction("/home/comment");
		// 結果のチェック
		assertPathEquals(Forward.class, "index.jsp", result);
		assertEquals("ひとりごとは140文字以下で入力してください。", this.action.getErrors()
				.getAll().get(0));
	}
}
