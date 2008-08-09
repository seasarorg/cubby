package org.seasar.cubby.cubbitter.action;

import java.sql.Timestamp;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.cubbitter.action.FavoriteAction;
import org.seasar.cubby.unit.CubbyTestCase;

public class FavoriteActionTest extends CubbyTestCase {
	// 対象のアクション
	private FavoriteAction action;

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

	public void testIndex() throws Exception {
		ActionResult result = processAction("/favorite/");

		assertPathEquals(Forward.class, "index.jsp", result);

		Object obj[][] = new Object[][] {
				{ 3, "ccc", "しさん", 5, "えええええええええええええええええええ",
						Timestamp.valueOf("2008-03-06 16:02:40.453"), false,
						true },
				{ 1, "aaaa", "あさん", 4,
						"うううううううううううううううううううううううううううううううううううううううう",
						Timestamp.valueOf("2008-03-06 16:02:25.203"), true,
						true },
				{ 2, "bbb", "びさん", 3,
						"いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい",
						Timestamp.valueOf("2008-03-06 16:01:58.546"), true,
						true }, };
		assertEquals(java.util.Arrays.asList(Conv
				.convertArrayToCommentMaps(obj)), action.commentList);
	}

	public void testAdd() throws Exception {
		// 追加
		ActionResult result = processAction("/operation/fav/add/1/home/1");
		assertPathEquals(Redirect.class, "/home/1", result);

		// 数の確認
		result = processAction("/favorite/");
		assertEquals(4, action.commentList.size());

		// 削除
		result = processAction("/operation/fav/del/1/home/1");
		assertPathEquals(Redirect.class, "/home/1", result);

		// 数の確認
		result = processAction("/favorite/");
		assertEquals(3, action.commentList.size());
	}
}
