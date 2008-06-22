package org.seasar.cubby.cubbitter.action;

import java.sql.Timestamp;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.cubbitter.action.TopAction;
import org.seasar.cubby.unit.CubbyTestCase;

public class TopActionTest extends CubbyTestCase{
	// 対象のアクション
	private TopAction action;
	
	// 初期化処理
    protected void setUp() throws Exception {
    	// diconファイルの読み込み
        include("app.dicon");
    }
  
	public void testIndex() throws Exception {
		// アクションの実行
		ActionResult result = processAction("/top/");
		// 結果のチェック
		assertPathEquals(Forward.class, "index.jsp", result);
		
		 
		Object obj[][] = new Object[][]{
			{1, "aaaa", "あさん", 4, "うううううううううううううううううううううううううううううううううううううううう", Timestamp.valueOf("2008-03-06 16:02:25.203"), true},
			{2, "bbb", "びさん", 3, "いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい", Timestamp.valueOf("2008-03-06 16:01:58.546"), true},
			{1, "aaaa", "あさん", 2, "ああああああああああああああ", Timestamp.valueOf("2008-03-06 16:01:27.593"), true},
			{1, "aaaa", "あさん", 1, "コメント", Timestamp.valueOf("2008-03-06 15:46:26.171"), true}	
		};

		assertEquals(java.util.Arrays.asList(Conv.convertArrayToCommentMaps(obj)), action.commentList);
	}
	
	public void testLoginIndex() throws Exception {
		
		getRequest().setParameter("memberName", "aaaa");
		getRequest().setParameter("password", "passworda");
		// 後続のテストを実行するためにログインアクションを実行
		assertPathEquals(Redirect.class, "/home/", processAction("/login/"));
		
	
		// アクションの実行
		ActionResult result = processAction("/top/");
		// 結果のチェック
		assertPathEquals(Forward.class, "index.jsp", result);
		
		 
		Object obj[][] = new Object[][]{
			{1, "aaaa", "あさん", 4, "うううううううううううううううううううううううううううううううううううううううう", Timestamp.valueOf("2008-03-06 16:02:25.203"), true, true},
			{2, "bbb", "びさん", 3, "いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい", Timestamp.valueOf("2008-03-06 16:01:58.546"), true, true},
			{1, "aaaa", "あさん", 2, "ああああああああああああああ", Timestamp.valueOf("2008-03-06 16:01:27.593"), true, false},
			{1, "aaaa", "あさん", 1, "コメント", Timestamp.valueOf("2008-03-06 15:46:26.171"), true, false}	
		};

		assertEquals(java.util.Arrays.asList(Conv.convertArrayToCommentMaps(obj)), action.commentList);
	}
//	public void testMember() throws Exception{
//		// アクションの実行
//		ActionResult result = processAction("/top/member/2");
//		// 結果のチェック
//		assertPathEquals(Forward.class, "member.jsp", result);
//		
//		 
//		Object obj[][] = new Object[][]{
//			{2, "bbb", 3, "いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい", Timestamp.valueOf("2008-03-06 16:01:58.546")},
//		};
//
//		assertEquals(java.util.Arrays.asList(Conv.convertArrayToCommentMaps(obj)), action.commentList);
//	}
	
}
