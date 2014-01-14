package com;

import static org.junit.Assert.assertEquals;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCpRem{
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://172.20.230.77:9888/analytics/include/login.html");
		selenium.start();
	}

	@Test
	public void testCpRem() throws Exception {
		selenium.open("/analytics/include/login.xhtml");
		selenium.type("id=loginForm:username", "sysadmin");
		selenium.type("id=loginForm:password", "wrpassword");
		selenium.click("id=loginForm:signin");
		//selenium.waitForPageToLoad("30000");
		Thread.sleep(3000);
		
		//waitForElement("ybCustomerName", 2000);
		selenium.type("id=ybCustomerName", "rem");
		selenium.click("id=ybCampaignFilterSubmit");
		Thread.sleep(2000);
		//waitForElement("176342", 2000);
		selenium.click("link=176342");
		selenium.waitForPageToLoad("30000");
		
		//selenium
		//assertEquals();
		//*[@id="accountSelect"]
		assertEquals("REM11736" , selenium.getText("//*[@id='accountSelect']"));  
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
	
	
	protected void waitForElement(String locator, int timeout)  
        throws InterruptedException {  
        for (int second = 0;second < timeout; second = second + 50) {  
            System.out.println("--------waitting--------");
            try {  
                if (selenium.isElementPresent(locator)) {  
                    break;  
                }
            }catch (Exception e) {  
            	System.out.println("there is exception");
                //Thread.sleep(1000);  
            }  
            
         }  
    }
}
