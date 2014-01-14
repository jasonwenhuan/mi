package com.chinaunicom.filterman.core.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.bl.IGeneralBillBL;
import com.chinaunicom.filterman.core.db.dao.GeneralBillDao;
import com.chinaunicom.filterman.core.db.entity.GeneralBillEntity;

public class GeneralBillBL implements IGeneralBillBL{
	@Autowired
    private GeneralBillDao generalBillDao;

	@Override
	public boolean createGeneralBill(GeneralBillEntity entity) {
		return generalBillDao.createGeneralBill(entity);
	}

	@Override
	public boolean removeRecordsAndEnsureIndex() {
		return generalBillDao.removeRecordsAndEnsureIndex();
	}

	@Override
	public List<GeneralBillEntity> getGeneralBillByOffsetAndCount(int offset, int count) {
		return generalBillDao.getGeneralBillByOffsetAndCount(offset, count);
	}

}
