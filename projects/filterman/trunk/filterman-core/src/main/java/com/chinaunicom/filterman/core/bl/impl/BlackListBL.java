package com.chinaunicom.filterman.core.bl.impl;

import java.util.List;

import com.chinaunicom.filterman.core.bl.IBlackListBL;
import com.chinaunicom.filterman.core.db.dao.BlackListDao;
import com.chinaunicom.filterman.core.db.entity.WBListEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Sally
 */
public class BlackListBL implements IBlackListBL {

    @Autowired
    private BlackListDao blackListDao;

    @Override
    public WBListEntity getBlackList(String keyId){
         return blackListDao.getBlackList(keyId);
    }

    @Override
    public void saveBlackList(WBListEntity entity){
          blackListDao.saveBlackList(entity);
    }

    @Override
    public void removeBlackList(String id){
        blackListDao.removeBlackList(id);
    }

	@Override
	public List<WBListEntity> getBlackListByKeyId(String keyId, int pageOffset, int pageSize) {
		return blackListDao.getBlackListByKeyId(keyId, pageOffset, pageSize);
	}

	@Override
	public Long getBlackListCountByKeyId(String keyId) {
		return blackListDao.getBlackListCountByKeyId(keyId);
	}
}