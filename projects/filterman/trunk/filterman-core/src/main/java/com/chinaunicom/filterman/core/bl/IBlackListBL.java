package com.chinaunicom.filterman.core.bl;

import java.util.List;

import com.chinaunicom.filterman.core.db.entity.WBListEntity;

/**
 * User: Sally
 */
public interface IBlackListBL {
    public WBListEntity getBlackList(String keyId);
    public List<WBListEntity> getBlackListByKeyId(String keyId, int pageOffset, int pageSize);
    public Long getBlackListCountByKeyId(String keyId);
    public void saveBlackList(WBListEntity entity);
    public void removeBlackList(String id);
}
