package com.chinaunicom.filterman.core.bl;

import com.chinaunicom.filterman.core.db.entity.BlockedEntity;

import java.util.Date;

public interface IBlockphoneBL {
    public boolean createBlockPhone(BlockedEntity entity);
    public BlockedEntity getBlockByPhone(String phone);
    public boolean updateBlockByPhone(String phone, Date date);
    public boolean removeRecordsAndEnsureIndex();
}
