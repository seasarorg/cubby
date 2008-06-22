package org.seasar.cubby.cubbitter.action;


import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.cubbitter.action.LoginAction;
import org.seasar.cubby.unit.CubbyTestCase;

public class LoginActionTest extends CubbyTestCase{
	// 対象のアクション
	private LoginAction action;
	
	// 初期化処理
    protected void setUp() throws Exception {
    	// diconファイルの読み込み
        include("app.dicon");
    }

	public void testLoginOk() throws Exception{
		//パラメータ
		getRequest().addParameter("memberName", "aaaa");
		getRequest().addParameter("password", "passworda");
		//アクションの実行
		ActionResult result = processAction("/login/");
		// 結果のチェック
		assertPathEquals(Redirect.class, "/home/", result);
	}
	public void testBlankId() throws Exception{
		//パラメータ
		getRequest().addParameter("memberName", "");
		getRequest().addParameter("password", "password");
		//アクションの実行
		ActionResult result = processAction("/login/");
		// 結果のチェック
		assertPathEquals(Forward.class, "/top/loginError.jsp", result);
		assertEquals("ユーザIDは必須です。", this.action.getErrors().getAll().get(0));	                        
	}
	public void testBlankPassword() throws Exception{
		//パラメータ
		getRequest().addParameter("memberName", "aaaa");
		getRequest().addParameter("password", "");
		//アクションの実行
		ActionResult result = processAction("/login/");
		// 結果のチェック
		assertPathEquals(Forward.class, "/top/loginError.jsp", result);
		assertEquals("パスワードは必須です。", this.action.getErrors().getAll().get(0));	                        
	}
	public void testLoginNG() throws Exception{
		//パラメータ
		getRequest().addParameter("memberName", "aaaa");
		getRequest().addParameter("password", "aaaa");
		//アクションの実行
		ActionResult result = processAction("/login/");
		// 結果のチェック
		assertPathEquals(Forward.class, "/top/loginError.jsp", result);
		assertEquals("ユーザIDまたはパスワードが違います。", this.action.getErrors().getAll().get(0));	                        
	}
	
}
