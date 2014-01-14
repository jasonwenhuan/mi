package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.bl.IPhoneBL;
import com.chinaunicom.filterman.core.db.dao.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.db.entity.PhoneEntity;

public class PhoneBL implements IPhoneBL {

	@Autowired
    private PhoneDao phoneDao;

    @Override
    public PhoneEntity getPhoneUuid4Two(String key, String appId) {
        return phoneDao.getPhoneUuid4Two(key, appId);
    }

    @Override
    public void updatePhoneUuid4Two(PhoneEntity entity) {
        phoneDao.updatePhoneUuid4Two(entity);
    }

    @Override
    public PhoneEntity getPhoneUuid4One(String key, String appId) {
        return phoneDao.getPhoneUuid4One(key, appId);
    }

    @Override
    public void updatePhoneUuid4One(PhoneEntity entity) {
        phoneDao.updatePhoneUuid4One(entity);
    }

	@Override
	public PhoneEntity getPhone4Two(String key, String appId) {
		return phoneDao.getPhone4Two(key, appId);
	}

	@Override
	public void updatePhone4Two(PhoneEntity entity) {
		phoneDao.updatePhone4Two(entity);
	}
	
	@Override
	public PhoneEntity getPhone4One(String key, String appId) {
		return phoneDao.getPhone4One(key, appId);
	}

	@Override
	public void updatePhone4One(PhoneEntity entity) {
		phoneDao.updatePhone4One(entity);
	}
}
