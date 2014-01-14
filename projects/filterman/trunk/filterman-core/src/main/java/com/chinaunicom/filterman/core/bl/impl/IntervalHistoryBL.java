package com.chinaunicom.filterman.core.bl.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.bl.IIntervalHistoryBL;
import com.chinaunicom.filterman.core.db.dao.IntervalHistoryDao;
import com.chinaunicom.filterman.core.db.entity.IntervalHistoryEntity;

public class IntervalHistoryBL implements IIntervalHistoryBL {
	@Autowired
    private IntervalHistoryDao intervalHistoryDao;
	
	@Override
	public void createIntervalHistory(IntervalHistoryEntity entity) {
		intervalHistoryDao.saveIntervalEntity(entity);
	}

	@Override
	public int getTotalBill(String phone, Date startTime, Date endTime) {
		return intervalHistoryDao.getBillTotal(phone, startTime, endTime);
	}
	@Override
	public boolean removeRecordsAndEnsureIndex() {
		return intervalHistoryDao.removeRecordsAndEnsureIndex();
	}
}
