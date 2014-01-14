package com.chinaunicom.filterman.core.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.bl.IIntervalBL;
import com.chinaunicom.filterman.core.db.dao.IntervalMstDao;
import com.chinaunicom.filterman.core.db.entity.IntervalMstEntity;

public class IntervalBL implements IIntervalBL {
	@Autowired
    private IntervalMstDao intervalDao;
	
	@Override
	public boolean createInterval(IntervalMstEntity entity) {
		return intervalDao.saveIntervalEntity(entity);
	}

	@Override
	public boolean updateInterval(IntervalMstEntity entity) {
		return intervalDao.updateIntervalEntity(entity);
	}

	@Override
	public List<IntervalMstEntity> getAllIntervals() {
		return intervalDao.getAllIntervals();
	}

	@Override
	public List<IntervalMstEntity> getRulesByInterval(String interval) {
		return intervalDao.getRulesByInterval(interval);
	}

	@Override
	public boolean removeInterval(String id) {
		return intervalDao.removeInterval(id);
	}

}
