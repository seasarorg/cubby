package org.seasar.cubby.examples.it;


import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

abstract public class AbstractSeleniumTestCase extends SeleneseTestCase  {

    public static final String BROWSER_IEXPLORE = "*iexplore";
    public static final String BROWSER_FIREFOX = "*firefox";

    protected SeleniumServer seleniumServer;

    protected Selenium selenium;

//    private String browser = BROWSER_IEXPLORE;
    private String browser = BROWSER_FIREFOX;
    
    
    @Override
    public void setUp() throws Exception {
		startSeleniumServer();
		startSelenium();
    }

    @Override
    public void tearDown() throws Exception {
        stopSelenium();
        stopSeleniumServer();
    }

    private void startSeleniumServer() throws Exception {
        seleniumServer = new SeleniumServer();
        seleniumServer.start();
    }

    private void stopSeleniumServer() {
        seleniumServer.stop();
    }

    private void startSelenium() throws Exception{
        String url = "http://localhost:8080/";

        selenium = new DefaultSelenium("localhost",
                SeleniumServer.DEFAULT_PORT, browser, url);

        selenium.start();
    }

    private void stopSelenium() {
        selenium.stop();
    }


    public void setBrowser(final String browser) {
        this.browser = browser;
    }

}

