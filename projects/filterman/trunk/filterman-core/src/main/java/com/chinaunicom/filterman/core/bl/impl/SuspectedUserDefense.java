package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.db.dao.BlockphoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.entity.BlockedEntity;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;

public class SuspectedUserDefense implements IDefenseBL{
	public final static String POLICY_NAME = "疑似用户策略";
	
	public final static String EXP_BLOCK_SUSPECTEDUSER = "触发了 [" + POLICY_NAME + 
	    "]. 手机号[%s] 在疑似用户表中.";

    @Autowired
    private BlockphoneDao blockPhoneDao;
	
	@Override
	public boolean execute(RequestEntity requestEntity) throws PolicyException,
			RequestException {

		if(!StringUtils.hasText(requestEntity.getPhone())){
            throw new RequestException("The phone is blank.", requestEntity);
		}
		
		BlockedEntity blockEntity =  blockPhoneDao.getBlockByPhone(requestEntity.getPhone());
		if(blockEntity != null){
			blockPhoneDao.updateBlockByPhone(requestEntity.getPhone(), requestEntity.getTimestamp());
			throw new PolicyException(String.format(EXP_BLOCK_SUSPECTEDUSER, requestEntity.getPhone()));
		}
		
		return true;
	}

    @Override
    public String toString() {
        return POLICY_NAME;
    }

}
