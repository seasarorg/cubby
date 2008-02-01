package org.seasar.cubby.examples.it;

public class HelloTest extends AbstractSeleniumTestCase {

	public void testHello() throws Exception {
		selenium.open("/cubby-selenium-test/");
		selenium.click("link=Hello World");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Hello World - 入力画面"));
		selenium.type("name", "MrHelloWorld");
		selenium.click("//input[@value='送信']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Hello World - 出力画面"));
		assertTrue(selenium.isTextPresent("MrHelloWorld"));
	}
	
}
