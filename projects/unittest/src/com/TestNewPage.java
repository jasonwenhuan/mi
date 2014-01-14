package com;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNewPage {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://zhidao.baidu.com/");
		selenium.start();
	}

	@Test
	public void testAaa() throws Exception {
		selenium.open("/");
		selenium.click("link=网页");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=知 道");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
