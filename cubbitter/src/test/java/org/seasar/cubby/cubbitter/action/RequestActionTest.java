package org.seasar.cubby.cubbitter.action;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.cubbitter.action.RequestAction;
import org.seasar.cubby.unit.CubbyTestCase;

public class RequestActionTest extends CubbyTestCase {
	// 対象のアクション
	private RequestAction action;

	// 初期化処理
	protected void setUp() throws Exception {
		// diconファイルの読み込み
		include("app.dicon");
	}

	@Override
	protected void setUpAfterBindFields() throws Throwable {
		super.setUpAfterBindFields();

		getRequest().setParameter("memberName", "aaaa");
		getRequest().setParameter("password", "passworda");
		// 後続のテストを実行するためにログインアクションを実行
		assertPathEquals(Redirect.class, "/home/", processAction("/login/"));

		// 追加
		ActionResult result = processAction("/following/add/69/member");
		assertPathEquals(Redirect.class, "/member/fff", result);

		getRequest().setParameter("memberName", "fff");
		getRequest().setParameter("password", "ffffff");
		// 後続のテストを実行するためにログインアクションを実行
		assertPathEquals(Redirect.class, "/home/", processAction("/login/"));
	}

	public void testAccept() throws Exception {

		assertPathEquals(Forward.class, "index.jsp", processAction("/request/"));
		assertEquals(1, action.followRequestedCount);
		assertEquals(1, action.requestedMemberList.size());

		// Accept!
		assertPathEquals(Redirect.class, "../home/",
				processAction("/request/accept/1"));

		// ログイン
		getRequest().setParameter("memberName", "aaaa");
		getRequest().setParameter("password", "passworda");
		// 後続のテストを実行するためにログインアクションを実行
		assertPathEquals(Redirect.class, "/home/", processAction("/login/"));

		// 削除
		ActionResult result = processAction("/following/del/69/member");
		assertPathEquals(Redirect.class, "/member/fff", result);
	}

	public void testDeny() throws Exception {
		assertPathEquals(Forward.class, "index.jsp", processAction("/request/"));
		assertEquals(1, action.followRequestedCount);
		assertEquals(1, action.requestedMemberList.size());

		// Deny!
		assertPathEquals(Redirect.class, "../home/",
				processAction("/request/deny/1"));
	}
}
