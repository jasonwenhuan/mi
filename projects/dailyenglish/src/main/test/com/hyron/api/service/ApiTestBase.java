package com.hyron.api.service;

import javax.ws.rs.core.Response;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.google.gson.Gson;

/**
 * Created by IntelliJ IDEA.
 * User: gur01
 * Date: Mar 21, 2008
 * Copyrights: YellowBook USA, 2008 - 2013. All rights reserved.
 */
public abstract class ApiTestBase extends AbstractDependencyInjectionSpringContextTests {

    protected String[] getConfigLocations() {
        return new String[]{
                "classpath:applicationContext-properties.xml",
                "classpath:applicationContext-jdbc.xml",
                "classpath:applicationContext-unittest.xml"
        };
    }

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();    //To change body of overridden methods use File | Settings | File Templates.
        initTestCase();
    }

    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();    //To change body of overridden methods use File | Settings | File Templates.
        cleanUpTestCase();
    }

    /**
     * Called every before the testcase start. Use this to innitialize any pre-test data daos...etc
     */
    protected abstract void initTestCase();

    /**
     * Called everytime after the TestCase finished. Use this to do any post testcase clean up
     */
    protected abstract void cleanUpTestCase();

    protected String toString(Object obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
