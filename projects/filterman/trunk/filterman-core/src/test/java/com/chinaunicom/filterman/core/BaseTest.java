package com.chinaunicom.filterman.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: larry
 */
public class BaseTest {
    protected ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
            "applicationContext-core.xml");
}
