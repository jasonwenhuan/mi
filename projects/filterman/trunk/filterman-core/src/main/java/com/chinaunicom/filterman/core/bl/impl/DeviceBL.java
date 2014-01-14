package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.bl.IDeviceBL;
import com.chinaunicom.filterman.core.db.dao.DeviceDao;
import com.chinaunicom.filterman.core.db.entity.DeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class DeviceBL implements IDeviceBL {

	@Autowired
    private DeviceDao deviceDao;

    @Override
    public DeviceEntity getDeviceUuid4Two(String key, String appId) {
        return deviceDao.getDeviceUuid4Two(key, appId);
    }

    @Override
    public void updateDeviceUuid4Two(DeviceEntity entity) {
        deviceDao.updateDeviceUuid4Two(entity);
    }

    @Override
    public DeviceEntity getDeviceUuid4One(String key, String appId) {
        return deviceDao.getDeviceUuid4One(key, appId);
    }

    @Override
    public void updateDeviceUuid4One(DeviceEntity entity) {
        deviceDao.updateDeviceUuid4One(entity);
    }

	@Override
	public DeviceEntity getDevice4Two(String key, String appId) {
		return deviceDao.getDevice4Two(key, appId);
	}

	@Override
	public void updateDevice4Two(DeviceEntity entity) {
		deviceDao.updateDevice4Two(entity);
	}
	
	@Override
	public DeviceEntity getDevice4One(String key, String appId) {
		return deviceDao.getDevice4One(key, appId);
	}

	@Override
	public void updateDevice4One(DeviceEntity entity) {
		deviceDao.updateDevice4One(entity);
	}
}
