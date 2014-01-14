package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.dao.WhiteListDao;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * User: larry
 */
public class WhiteListDefense implements IDefenseBL {
    private final static String POLICY_NAME = "WhiteList";
    private final static String EXP_ACCOUNT = "Trigger " + POLICY_NAME + ". The account[%s] is in WhiteList.";
    private final static String EXP_PHONE = "Trigger " + POLICY_NAME + ". The phone[%s] is in WhiteList.";
    private final static String EXP_DEVICE = "Trigger " + POLICY_NAME + ". The device[%s] is in WhiteList.";

    @Autowired
    private WhiteListDao whiteListDao;

    @Override
    public boolean execute(RequestEntity request) throws RequestException, PolicyException {
        boolean isWhiteList = false;

        if(!StringUtils.hasText(request.getAppid())) {
            throw new RequestException("The appid is blank.", request);
        }

        if(!request.getPayType().equals("2") && !StringUtils.hasText(request.getAccount())) {
            throw new RequestException("The account is blank.", request);
        }

        if(!StringUtils.hasText(request.getPhone())) {
            throw new RequestException("The phone is blank.", request);
        }

        if(whiteListDao.getWhiteList(request.getPhone()) != null) {
            throw new PolicyException(String.format(EXP_PHONE, request.getPhone()));
        }

//        if(whiteListDao.getWhiteList(request.getAccount()) != null) {
//            throw new PolicyException(String.format(EXP_ACCOUNT, request.getAccount()));
//        }
//
//        if(StringUtils.hasText(request.getMac())
//                && whiteListDao.getWhiteList(request.getMac()) != null) {
//            throw new PolicyException(String.format(EXP_DEVICE, request.getMac()));
//        }

        return isWhiteList;
    }

    @Override
    public String toString() {
        return POLICY_NAME;
    }
}
