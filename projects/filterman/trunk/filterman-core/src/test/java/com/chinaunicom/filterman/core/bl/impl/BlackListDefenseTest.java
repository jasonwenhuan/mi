package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.BaseTest;
import com.chinaunicom.filterman.core.bl.IDefenseBL;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Sally
 */
public class BlackListDefenseTest extends BaseTest {
    @Autowired
    private IDefenseBL blackListDefense = (IDefenseBL)ctx.getBean("blackListDefense");

//    @Test
//    public void testIsRequestInBlackList() throws RequestException, PolicyException {
//        RequestEntity message = makeRequest();
//        boolean result = blackListDefense.execute(message);
//        Assert.assertEquals(result, true);
//    }
//
//    private RequestEntity makeRequest(){
//        String account = "A00000000003";
//        String appId = "3";
//        String phone = "P00000000001";
//        String device = "D00000001";
//
//        RequestEntity request = new RequestEntity(phone, device, appId, account);
//        return request;
//    }
}
