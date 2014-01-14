package com.chinaunicom.filterman.core.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.bl.IBadBillBL;
import com.chinaunicom.filterman.core.db.dao.BadBillDao;
import com.chinaunicom.filterman.core.db.entity.BadBillEntity;

public class BadBillBL implements IBadBillBL{
	@Autowired
    private BadBillDao badBillDao;
	
	@Override
	public boolean createBadBill(BadBillEntity entity) {
		return badBillDao.createBadBill(entity);
	}

	@Override
	public List<BadBillEntity> getAllBadBills() {
		return badBillDao.getAllBadBills();
	}

	@Override
	public boolean removeRecordsAndEnsureIndex() {
		return badBillDao.removeRecordsAndEnsureIndex();
	}

	@Override
	public BadBillEntity getBadBillByUserCode(String userCode) {
		return badBillDao.getBadBillByUserCode(userCode);
	}

	@Override
	public boolean updateBadBillById(BadBillEntity entity) {
		return badBillDao.updateBadBillById(entity);
	}

}
