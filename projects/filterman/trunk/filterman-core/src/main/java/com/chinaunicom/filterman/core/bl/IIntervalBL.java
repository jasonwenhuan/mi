package com.chinaunicom.filterman.core.bl;

import java.util.List;

import com.chinaunicom.filterman.core.db.entity.IntervalMstEntity;

public interface IIntervalBL {
    public boolean createInterval(IntervalMstEntity entity);
    public boolean updateInterval(IntervalMstEntity entity);
    public List<IntervalMstEntity> getAllIntervals();
	public List<IntervalMstEntity> getRulesByInterval(String interval);
	public boolean removeInterval(String id);
}
