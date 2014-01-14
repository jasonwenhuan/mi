package com.chinaunicom.filterman.core.bl;

import com.chinaunicom.filterman.core.db.entity.PhoneEntity;

public interface IPhoneBL {
    public PhoneEntity getPhone4Two(String key, String appId);
    public void updatePhone4Two(PhoneEntity entity);
    public PhoneEntity getPhone4One(String key, String appId);
    public void updatePhone4One(PhoneEntity entity);

    public PhoneEntity getPhoneUuid4Two(String key, String uuid);
    public PhoneEntity getPhoneUuid4One(String key, String uuid);
    public void updatePhoneUuid4Two(PhoneEntity entity);
    public void updatePhoneUuid4One(PhoneEntity entity);
}
