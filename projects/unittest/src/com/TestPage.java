package com;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPage {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*googlechrome C:/Program Files (x86)/Chrome/Chrome/chrome.exe", "http://zhidao.baidu.com/");
		selenium.start();
	}

	@Test
	public void testUnittest() throws Exception {
		selenium.open("/");
		selenium.click("link=网页");
		//selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.click("link=知 道");
		//selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
