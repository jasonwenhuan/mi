package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.dao.BlackListDao;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * User: Sally
 */
public class BlackListDefense implements IDefenseBL {

    private final static String POLICY_NAME = "BlackList";
    private final static String EXP_ACCOUNT = "Trigger " + POLICY_NAME + ". The account[%s] is in BlackList.";
    private final static String EXP_PHONE = "Trigger " + POLICY_NAME + ". The phone[%s] is in BlackList.";
    private final static String EXP_DEVICE = "Trigger " + POLICY_NAME + ". The device[%s] is in BlackList.";

    @Autowired
    private BlackListDao blackListDao;

    @Override
    public boolean execute(RequestEntity request) throws RequestException, PolicyException {

        if(!StringUtils.hasText(request.getAppid())) {
            throw new RequestException("The appid is blank.", request);
        }

        if(!request.getPayType().equals("2") && !StringUtils.hasText(request.getAccount())) {
            throw new RequestException("The account is blank.", request);
        }

        if(!StringUtils.hasText(request.getPhone())) {
            throw new RequestException("The phone is blank.", request);
        }

//        if(blackListDao.getBlackList(request.getAccount()) != null) {
//            throw new PolicyException(String.format(EXP_ACCOUNT, request.getAccount()));
//        }
//
//        if(StringUtils.hasText(request.getMac())
//                && blackListDao.getBlackList(request.getMac()) != null) {
//            throw new PolicyException(String.format(EXP_DEVICE, request.getMac()));
//        }
//
        if(blackListDao.getBlackList(request.getPhone()) != null) {
            throw new PolicyException(String.format(EXP_PHONE, request.getPhone()));
        }

        return true;
    }

    @Override
    public String toString() {
        return POLICY_NAME;
    }
}
