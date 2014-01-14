package com.chinaunicom.filterman.core.bl;

import com.chinaunicom.filterman.core.db.entity.DeviceEntity;

public interface IDeviceBL {
	public DeviceEntity getDevice4Two(String key, String appId);
	public void updateDevice4Two(DeviceEntity entity);
	public DeviceEntity getDevice4One(String key, String appId);
	public void updateDevice4One(DeviceEntity entity);

    public DeviceEntity getDeviceUuid4Two(String key, String uuid);
    public DeviceEntity getDeviceUuid4One(String key, String uuid);
    public void updateDeviceUuid4Two(DeviceEntity entity);
    public void updateDeviceUuid4One(DeviceEntity entity);
}
