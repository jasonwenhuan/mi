package com.chinaunicom.filterman.core.bl;

import java.util.Date;

import com.chinaunicom.filterman.core.db.entity.IntervalHistoryEntity;

public interface IIntervalHistoryBL {
    public void createIntervalHistory(IntervalHistoryEntity intervalHistory);
    public int getTotalBill(String phone, Date startDate, Date endDate);
    public boolean removeRecordsAndEnsureIndex();
}
