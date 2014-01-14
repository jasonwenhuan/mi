package com;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class TestCanadaGeoTarget {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://admin:wrpassword@172.20.230.77:7480/sales/sales/betRequests.jsf");
		selenium.start();
	}

	@Test
	public void testCanadaGeoTarget() throws Exception {		
		
		selenium.open("/sales/sales/betRequests.jsf");
		Thread.sleep(3000);
		selenium.click("//tr[@id='yui-rec12']/td[11]/div/a/img");
		Thread.sleep(3000);
		selenium.selectWindow("name=undefined");
		Thread.sleep(2000);
		selenium.click("id=1326");
		Thread.sleep(2000);
		selenium.click("//div[@id='mainPageTabContent']/div[4]/a/div/b");
		Thread.sleep(1000);
		selenium.click("id=seoCheck");
		Thread.sleep(1000);
		selenium.click("id=searchCheck");
		Thread.sleep(1000);
		selenium.click("css=#nextBtn > div");
		//selenium.waitForPageToLoad("30000");
		Thread.sleep(10000);
		//selenium.click("//*[@id='displayTab']");
		selenium.click("css=#displayTab > a > em");
		Thread.sleep(1000);
		selenium.click("//div[@id='geoTab']/ul/li[6]/a/em");
		Thread.sleep(1000);
		selenium.click("css=#nationalUS > b");
		Thread.sleep(1000);
		selenium.click("css=#nationalCA > b");
        Thread.sleep(1000);
        
        assertEquals("Canada",selenium.getText("//*[@id='nationalCA']"));
      //*[@id="nationalCA"]
        
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
