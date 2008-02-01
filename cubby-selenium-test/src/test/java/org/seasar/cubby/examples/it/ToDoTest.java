package org.seasar.cubby.examples.it;

public class ToDoTest extends AbstractSeleniumTestCase {
	
	
	public void test02_toDo_001() throws Exception {
		System.out.println("run todotest..............................");
		selenium.open("/cubby-selenium-test/");
		selenium.click("link=Todoサンプルアプリケーション");
		selenium.waitForPageToLoad("30000");
		// ログイン
		verifyTrue(selenium.isTextPresent("Todoログイン"));
		selenium.click("link=戻る");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Cuuby Exmaples"));
		selenium.click("link=Todoサンプルアプリケーション");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Todoログイン"));
		verifyTrue(selenium.isTextPresent("ログインしていません。"));
		selenium.click("//input[@value='ログイン']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Todoログイン"));
		verifyTrue(selenium.isTextPresent("ユーザ名は必須です。"));
		verifyTrue(selenium.isTextPresent("パスワードは必須です。"));
		selenium.type("userId", "test");
		selenium.click("//input[@value='ログイン']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("パスワードは必須です。"));
		selenium.type("userId", "");
		selenium.type("password", "test");
		selenium.click("//input[@value='ログイン']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("ユーザ名は必須です。"));
		selenium.type("userId", "hoge");
		selenium.type("password", "hoge");
		selenium.click("//input[@value='ログイン']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("ユーザIDかパスワードが違います。"));
		selenium.type("userId", "test");
		selenium.type("password", "test");
		selenium.click("//input[@value='ログイン']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todoリスト", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Todoの一覧"));
		selenium.click("//input[@value='検索']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todoリスト", selenium.getTitle());
		selenium.click("link=新規作成");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todo編集", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Todo編集"));
		selenium.click("link=一覧に戻る");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todoリスト", selenium.getTitle());
		selenium.click("link=新規作成");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todo編集", selenium.getTitle());
		// validationテスト
		selenium.click("//input[@value='次へ']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todo編集", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("タイトルは必須です。"));
		verifyTrue(selenium.isTextPresent("メモは必須です。"));
		verifyTrue(selenium.isTextPresent("種別は必須です。"));
		selenium.type("limitDate", "2008/02/99");
		selenium.click("//input[@value='次へ']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("期限日の日付の形式が正しくありません。"));
		verifyEquals("Todo編集", selenium.getTitle());
		// 登録確認へ遷移
		selenium.type("text", "テストタイトル");
		selenium.select("typeId", "label=type1");
		selenium.type("limitDate", "2008-02-01");
		selenium.type("memo", "テストメモ");
		selenium.click("//input[@value='次へ']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todo編集確認", selenium.getTitle());
		// 戻るリンク（戻らない）
		// 登録
		verifyTrue(selenium.isTextPresent("テストタイトル"));
		verifyTrue(selenium.isTextPresent("type1"));
		verifyTrue(selenium.isTextPresent("2008-02-01"));
		verifyTrue(selenium.isTextPresent("テストメモ"));
		selenium.click("//input[@value='登録']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todoリスト", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("テストタイトルを追加しました。"));
		// 検索
		selenium.click("//input[@value='検索']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todoリスト", selenium.getTitle());
		selenium.type("keyword", "keyword");
		selenium.type("keyword", "");
		selenium.click("//input[@value='検索']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("glob:テストタイトル*type1*2008-02-01 *[削除]"));
		selenium.type("keyword", "テストタイトル");
		selenium.select("typeId", "label=type1");
		selenium.type("limitDate", "2008-02-01");
		selenium.click("//input[@value='検索']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("glob:テストタイトル*type1*2008-02-01 *[削除]"));
		// 詳細
		selenium.click("link=テストタイトル");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todo詳細", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("テストタイトル"));
		verifyTrue(selenium.isTextPresent("type1"));
		verifyTrue(selenium.isTextPresent("2008-02-01"));
		verifyTrue(selenium.isTextPresent("テストメモ"));
		selenium.click("link=一覧に戻る");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todoリスト", selenium.getTitle());
		selenium.click("link=テストタイトル");
		selenium.waitForPageToLoad("30000");
		// 編集
		selenium.click("link=編集");
		selenium.waitForPageToLoad("30000");
		selenium.type("text", "テストタイトル編集");
		selenium.type("limitDate", "2009-02-01");
		selenium.type("memo", "テストメモ編集");
		selenium.select("typeId", "label=type2");
		selenium.click("//input[@value='次へ']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todo編集確認", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Todo編集確認"));
		verifyTrue(selenium.isTextPresent("テストタイトル編集"));
		verifyTrue(selenium.isTextPresent("type2"));
		verifyTrue(selenium.isTextPresent("2009-02-01"));
		verifyTrue(selenium.isTextPresent("テストメモ編集"));
		// 戻る（戻らない）
		selenium.click("//input[@value='登録']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Todoの一覧"));
		verifyTrue(selenium.isTextPresent("テストタイトル編集を更新しました。"));
		selenium.type("keyword", "");
		selenium.select("typeId", "label=");
		selenium.type("limitDate", "");
		selenium.click("//input[@value='検索']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("glob:テストタイトル編集*type2*2009-02-01*[削除]"));
		// 削除
		selenium.click("link=削除");
		verifyEquals("「テストタイトル編集」削除します。よろしいですか？", selenium.getConfirmation());
		verifyEquals("Todoリスト", selenium.getTitle());
		Thread.sleep(3000);
		verifyTrue(selenium.isTextPresent("テストタイトル編集を削除しました。"));
		// ログアウト
		selenium.click("link=ログアウト");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Todoログイン", selenium.getTitle());
	}

}
