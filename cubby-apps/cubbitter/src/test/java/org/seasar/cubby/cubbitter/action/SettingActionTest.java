package org.seasar.cubby.cubbitter.action;


import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.cubbitter.action.SettingAction;
import org.seasar.cubby.cubbitter.entity.Member;
import org.seasar.cubby.unit.CubbyTestCase;

public class SettingActionTest extends CubbyTestCase {
	// 対象のアクション
	private SettingAction action;

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

	public void testSetting() throws Exception {
		// アクションの実行
		ActionResult result = processAction("/setting/");
		// 結果のチェック
		assertPathEquals(Forward.class, "index.jsp", result);
		
		getRequest().setParameter("memberName", "aaaa_");
		//getRequest().setParameter("password", "passworda_");
		getRequest().addParameter("fullName", "あさん_");
		getRequest().addParameter("email", "a@aa.aa.aa");
		getRequest().addParameter("web", "http://aaa.aa.aa/a/");
		getRequest().addParameter("biography", "aaaaa_");
		getRequest().addParameter("location", "_");
		getRequest().addParameter("open", "false");

		// アクションの実行
		result = processAction("/setting/update");
		// 結果のチェック
		assertPathEquals(Redirect.class, "./", result);

		Member expected = new Member();
		expected.setMemberId(1);
		expected.setMemberName("aaaa_");
		//expected.setPassword("passworda_");
		expected.setFullName("あさん_");
		expected.setEmail("a@aa.aa.aa");
		expected.setWeb("http://aaa.aa.aa/a/");
		expected.setBiography("aaaaa_");
		expected.setLocation("_");
		expected.setLocale("ja");
		expected.setOpen(false);

		assertEquals(expected.toString(), action.sessionScope.get("user").toString());
		
		//元に戻す・・・
		getRequest().setParameter("memberName", "aaaa");
		//getRequest().setParameter("password", "passworda");
		getRequest().setParameter("fullName", "あさん");
		getRequest().setParameter("email", "a@aa.aa");
		getRequest().setParameter("web", "http://aaa.aa.aa/");
		getRequest().setParameter("biography", "aaaaa");
		getRequest().setParameter("location", "");
		getRequest().setParameter("open", "true");
		
		// アクションの実行
		result = processAction("/setting/update");
		// 結果のチェック
		assertPathEquals(Redirect.class, "./", result);
	}

}
