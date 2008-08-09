package org.seasar.cubby.cubbitter.action;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.cubbitter.action.LoginAction;
import org.seasar.cubby.unit.CubbyTestCase;

public class RegisterActionTest extends CubbyTestCase{
	// 対象のアクション
	private LoginAction action;
	
	// 初期化処理
    protected void setUp() throws Exception {
    	// diconファイルの読み込み
        include("app.dicon");
    }

	public void testDisplay() throws Exception{
		//アクションの実行
		ActionResult result = processAction("/register/");
		// 結果のチェック
		assertPathEquals(Forward.class, "index.jsp", result);
	}
	public void testResiter() throws Exception{
		
		//ユーザ登録する
		
		//パラメータ
		getRequest().addParameter("regMemberName", "userid");
		getRequest().addParameter("regPassword", "password");
		getRequest().addParameter("regEmail", "email@xxxxx.xx.xx");
		//アクションの実行
		ActionResult result = processAction("/register/process");
		// 結果のチェック
		assertPathEquals(Forward.class, "result.jsp", result);
		
		//ログインする
		
		//パラメータ
		getRequest().addParameter("memberName", "userid");
		getRequest().addParameter("password", "password");
		//アクションの実行
		result = processAction("/login/");
		// 結果のチェック
		assertPathEquals(Redirect.class, "/home/", result);
	
		//登録削除する
		//アクションの実行
		result = processAction("/setting/delete");
		//結果のチェック
		assertPathEquals(Forward.class, "delete.jsp", result);
		
		//もっかいログイン
		getRequest().addParameter("memberName", "userid");
		getRequest().addParameter("password", "password");
		//アクションの実行
		result = processAction("/login/");
		// 結果のチェック
		assertPathEquals(Forward.class, "/top/loginError.jsp", result);
		
	}

}
