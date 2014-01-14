package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.bl.IZonemapphoneBL;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.db.dao.ZoneMapPhoneDao;
import com.chinaunicom.filterman.core.db.entity.ZoneMapPhoneEntity;

public class ZonemapphoneBL implements IZonemapphoneBL {
    @Autowired
    private ZoneMapPhoneDao zoneMapPhoneDao;

    @Override
    public boolean createZonemapphone(ZoneMapPhoneEntity entity) {
        return zoneMapPhoneDao.createZoneMapPhone(entity);
    }

    @Override
    public boolean removeRecordsAndEnsureIndex() {
        return zoneMapPhoneDao.removeRecordsAndEnsureIndex();
    }

    @Override
    public String getZoneCodeWithPhone(String phone) {
        return zoneMapPhoneDao.getZoneCodeWithPhone(phone);
    }
}
