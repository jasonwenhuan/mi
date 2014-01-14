package com.chinaunicom.filterman.core.bl;

import java.util.List;

import com.chinaunicom.filterman.core.db.entity.WBListEntity;

public interface IWhiteListBL {
	public WBListEntity getWhiteList(String keyId);
    public void saveWhiteList(WBListEntity entity);
    public void removeWhiteList(String whiteListId);
    public List<WBListEntity> getWhiteListByKeyId(String keyId, int pageOffset, int pageSize);
    public Long getWhiteListCountByKeyId(String keyId);
}
