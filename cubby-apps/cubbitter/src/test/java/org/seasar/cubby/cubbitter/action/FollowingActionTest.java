package org.seasar.cubby.cubbitter.action;

import java.sql.Timestamp;


import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.cubbitter.action.FollowingAction;
import org.seasar.cubby.unit.CubbyTestCase;

public class FollowingActionTest extends CubbyTestCase {
	// 対象のアクション
	private FollowingAction action;

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
		assertPathEquals(Redirect.class, "/home/", processAction("/login/"));
	}

	public void testFollowing() throws Exception {
		ActionResult result = processAction("/following/");
		assertPathEquals(Forward.class, "index.jsp", result);

		// メンバー
		Object obj[][] = new Object[][] { { 3, "ccc", "しさん" },
				{ 4, "ddd", "でさん" } };
		assertEquals(java.util.Arrays
				.asList(Conv.convertArrayToMemberMaps(obj)),
				action.followingList);

	}

	public void testFollower() throws Exception {
		ActionResult result = processAction("/following/follower");
		// 結果のチェック
		assertPathEquals(Forward.class, "follower.jsp", result);

		Object obj[][] = new Object[][] { { 3, "ccc", "しさん", false },
				{ 38, "eee", "EEE", null }, };
		assertEquals(java.util.Arrays
				.asList(Conv.convertArrayToMemberMaps(obj)),
				action.followerList);

	}

	public void testPreSearch() throws Exception {
		ActionResult result = processAction("/following/presearch");
		// 結果のチェック
		assertPathEquals(Forward.class, "search.jsp", result);
	}

	public void testSearch() throws Exception {
		getRequest().addParameter("keyword", "b");

		ActionResult result = processAction("/following/search");
		assertPathEquals(Redirect.class, "doSearch?keyword=b&pageNo=1", result);
	}

	public void testDoSearch() throws Exception {
		getRequest().addParameter("keyword", "b");

		ActionResult result = processAction("/following/doSearch?keyword=b&pageNo=1");
		assertPathEquals(Forward.class, "search.jsp", result);

		Object obj[][] = new Object[][] { { 2, "bbb", "びさん", null, true,
				"http://bbb.bb.bb", "bbbbb", null,
				"いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい",
				Timestamp.valueOf("2008-03-06 16:01:58.546") } };

		assertEquals(java.util.Arrays
				.asList(Conv.convertArrayToMemberMaps(obj)),
				action.searchResultList);
	}

	public void testAddDelete() throws Exception {
		// 追加
		ActionResult result = processAction("/following/add/38/follower");
		assertPathEquals(Redirect.class, "follower", result);

		// 再表示
		result = processAction("/following/follower");
		// followingList
		Object obj[][] = new Object[][] { { 3, "ccc", "しさん" },
				{ 4, "ddd", "でさん" }, { 38, "eee", "EEE" }, };
		assertEquals(java.util.Arrays
				.asList(Conv.convertArrayToMemberMaps(obj)),
				action.followingList);
		// follower
		obj = new Object[][] {
				{ 3, "ccc", "しさん", false },
				{ 38, "eee", "EEE", false }, };
		assertEquals(java.util.Arrays
				.asList(Conv.convertArrayToMemberMaps(obj)),
				action.followerList);

		// 削除
		result = processAction("/following/del/38");
		assertPathEquals(Redirect.class, "./", result);
	}
}
