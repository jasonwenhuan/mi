package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.bl.IBlockphoneBL;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.db.dao.BlockphoneDao;
import com.chinaunicom.filterman.core.db.entity.BlockedEntity;

import java.util.Date;

public class BlockphoneBL implements IBlockphoneBL {
    @Autowired
    private BlockphoneDao blockPhoneDao;

    @Override
    public boolean createBlockPhone(BlockedEntity entity) {
        return blockPhoneDao.saveBlockPhoneEntity(entity);
    }

    @Override
    public BlockedEntity getBlockByPhone(String phone) {
        return blockPhoneDao.getBlockByPhone(phone);
    }

    @Override
    public boolean updateBlockByPhone(String phone, Date date) {
        return blockPhoneDao.updateBlockByPhone(phone, date);
    }

    @Override
    public boolean removeRecordsAndEnsureIndex() {
        return blockPhoneDao.removeRecordsAndEnsureIndex();
    }
}
