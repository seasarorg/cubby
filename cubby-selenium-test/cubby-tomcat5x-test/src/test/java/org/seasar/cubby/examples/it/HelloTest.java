package org.seasar.cubby.examples.it;

import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

public class HelloTest extends SeleneseTestCase {
	
    public static final String BROWSER_IEXPLORE = "*iexplore";
    public static final String BROWSER_FIREFOX = "*firefox";
    
    private String browser = BROWSER_FIREFOX;
    
    protected SeleniumServer seleniumServer;
    protected Selenium selenium;
	
	@Override
	public void setUp() throws Exception {
		startSeleniumServer();
		startSelenium();
		super.setUp();
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		selenium.stop();
		seleniumServer.stop();
	}
	
	public void testHello() throws Exception {
		selenium.open("/cubby-examples/");
		selenium.click("link=Hello World");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Hello World - 入力画面"));
		selenium.type("name", "MrHelloWorld");
		selenium.click("//input[@value='送信']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Hello World - 出力画面"));
		assertTrue(selenium.isTextPresent("MrHelloWorld"));
	}
	
    private void startSeleniumServer() throws Exception {
        seleniumServer = new SeleniumServer();
        seleniumServer.start();
    }
	
    private void startSelenium() {
        String url = "http://localhost:8080/";

        selenium = new DefaultSelenium("localhost",
                SeleniumServer.DEFAULT_PORT, browser, url);
        selenium.start();
    }
    
    @Override
    protected void runTest() throws Throwable {
        selenium.setSpeed("500");
        super.runTest();
    }
}
