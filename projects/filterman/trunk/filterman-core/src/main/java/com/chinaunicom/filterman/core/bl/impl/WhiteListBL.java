package com.chinaunicom.filterman.core.bl.impl;

import java.util.List;

import com.chinaunicom.filterman.core.bl.IWhiteListBL;
import com.chinaunicom.filterman.core.db.dao.WhiteListDao;
import com.chinaunicom.filterman.core.db.entity.WBListEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class WhiteListBL implements IWhiteListBL {
    @Autowired
    private WhiteListDao whiteListDao;
	
	@Override
	public void saveWhiteList(WBListEntity entity) {
		whiteListDao.saveWhiteList(entity);
	}

	@Override
	public void removeWhiteList(String whiteListId) {
        whiteListDao.removeWhiteList(whiteListId);
	}

	@Override
	public List<WBListEntity> getWhiteListByKeyId(String keyId, int pageOffset, int pageSize) {
		return whiteListDao.getWhiteListByKeyId(keyId, pageOffset, pageSize);
	}

	@Override
	public WBListEntity getWhiteList(String keyId) {
		return whiteListDao.getWhiteList(keyId);
	}

	@Override
	public Long getWhiteListCountByKeyId(String keyId) {
		return whiteListDao.getWhiteListCountByKeyId(keyId);
	}
}
