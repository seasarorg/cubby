package org.seasar.cubby.cubbitter.action;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.cubbitter.action.MemberAction;
import org.seasar.cubby.unit.CubbyTestCase;

public class MemberActionTest extends CubbyTestCase {
	// 対象のアクション
	private MemberAction action;

	// 初期化処理
	protected void setUp() throws Exception {
		// diconファイルの読み込み
		include("app.dicon");
	}

	public void testMemberAfterLoginButton0() throws Exception {
		getRequest().addParameter("memberName", "aaaa");
		getRequest().addParameter("password", "passworda");
		// 後続のテストを実行するためにログインアクションを実行
		processAction("/login/");

		ActionResult result = processAction("/member/ccc");
		// 結果のチェック
		assertPathEquals(Forward.class, "index.jsp", result);

		assertEquals("ccc", action.member.getMemberName());
		assertNotNull(action.commentList);
		assertEquals(0, action.button);
	}

	public void testMemberAfterLoginButton1() throws Exception {
		getRequest().addParameter("memberName", "aaaa");
		getRequest().addParameter("password", "passworda");
		// 後続のテストを実行するためにログインアクションを実行
		processAction("/login/");

		ActionResult result = processAction("/member/bbb");
		// 結果のチェック
		assertPathEquals(Forward.class, "index.jsp", result);

		assertEquals("bbb", action.member.getMemberName());
		assertNotNull(action.commentList);
		assertEquals(1, action.button);
	}

	public void testMemberAfterLoginButton2() throws Exception {
		getRequest().addParameter("memberName", "aaaa");
		getRequest().addParameter("password", "passworda");
		// 後続のテストを実行するためにログインアクションを実行
		processAction("/login/");

		ActionResult result = processAction("/member/eee");
		// 結果のチェック
		assertPathEquals(Forward.class, "index.jsp", result);

		assertEquals("eee", action.member.getMemberName());
		assertNull(action.commentList);
		assertEquals(2, action.button);
	}
	public void testMemberAfterLoginButton3() throws Exception {
		getRequest().addParameter("memberName", "aaaa");
		getRequest().addParameter("password", "passworda");
		// 後続のテストを実行するためにログインアクションを実行
		processAction("/login/");

		//追加リク
		processAction("/following/add/69/member");
		
		ActionResult result = processAction("/member/fff");
		// 結果のチェック
		assertPathEquals(Forward.class, "index.jsp", result);

		assertEquals("fff", action.member.getMemberName());
		assertNull(action.commentList);
		assertEquals(3, action.button);
		
		//追加リク解除
		processAction("/following/del/69/member");
		
	}
//	public void testMemberAfterLoginNoFollowNoOpen() throws Exception {
//		getRequest().addParameter("memberName", "aaaa");
//		getRequest().addParameter("password", "passworda");
//		// 後続のテストを実行するためにログインアクションを実行
//		processAction("/login/");
//
//		ActionResult result = processAction("/member/eee");
//		// 結果のチェック
//		assertPathEquals(Forward.class, "index.jsp", result);
//		assertEquals("eee", action.member.getMemberName());
//
//	}

	public void testMemberNoLogin1() throws Exception {
		// アクションの実行
		ActionResult result = processAction("/member/bbb");
		// 結果のチェック
		assertPathEquals(Forward.class, "index.jsp", result);
		assertEquals("bbb", action.member.getMemberName());
		assertNotNull(action.commentList);
		assertEquals(0, action.button);
	}
	public void testMemberNoLogin2() throws Exception {
		// アクションの実行
		ActionResult result = processAction("/member/ccc");
		// 結果のチェック
		assertPathEquals(Forward.class, "index.jsp", result);
		assertEquals("ccc", action.member.getMemberName());
		assertNull(action.commentList);
		assertEquals(0, action.button);		
	}
	public void testInvalidMember() throws Exception {
		// アクションの実行
		ActionResult result = processAction("/member/abcdefghij");
		// 結果のチェック
		assertPathEquals(Direct.class, "error.jsp", result);
		assertNull(action.member);
	}
}
