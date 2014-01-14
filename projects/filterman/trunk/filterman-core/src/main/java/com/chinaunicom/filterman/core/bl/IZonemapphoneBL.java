package com.chinaunicom.filterman.core.bl;

import com.chinaunicom.filterman.core.db.entity.ZoneMapPhoneEntity;

public interface IZonemapphoneBL {
    public boolean createZonemapphone(ZoneMapPhoneEntity entity);
    public boolean removeRecordsAndEnsureIndex();
    public String getZoneCodeWithPhone(String phone);
}
